package com.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.service.JwtServiceImpl;
import com.mvc.service.UserService;
import com.mvc.vo.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/user")
@Api("사용자 컨트롤러  API")
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
    UserService service;
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping(value = "/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) User user) throws Exception{
    	Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		try {
			// DB에 로그인 정보 확인
			User loginUser = service.login(user);
			if (loginUser != null) {
				// access, refresh token 발급
				String accessToken = jwtService.createAccessToken("userid", loginUser.getId());// "userid", "ssafy"
				String refreshToken = jwtService.createRefreshToken("userid", loginUser.getId());// key, data
				// refresh token을 DB에 저장
				service.saveRefreshToken(user.getId(), refreshToken);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				// 응답 설정
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				
				status = HttpStatus.ACCEPTED;
			}
			else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
    
    @ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{id}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("id") @ApiParam(value = "인증할 회원의 아이디.", required = true) String id, HttpServletRequest request) {
		logger.debug("id : {} ", id);
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				User user = service.userInfo(id);
				resultMap.put("userInfo", user);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

    @ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
    @GetMapping(value = "/logout/{id}")
    public ResponseEntity<?> removeToken(@PathVariable("id") String id) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			service.deleRefreshToken(id);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
    
    @ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody User user, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token"); // 헤더에 있는 refresh-token 뽑아옴
		logger.debug("token : {}, memberDto : {}", token, user);
		if (jwtService.checkToken(token)) { // 토큰이 정상적인지 확인(유효기간이 남았는지 확인)
			if (token.equals(service.getRefreshToken(user.getId()))) { // 클라이언트가 보내준 토큰과 db에 저장된 refresh-token과 일치하는지 확인
				String accessToken = jwtService.createAccessToken("userid", user.getId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken); // access-token을 보내줌
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(notes="회원가입", value="User 객체 등록")
    @PostMapping(value = "/join")
    public Map<String, String> join(@RequestBody User user) throws Exception {
        int x = service.join(user);

        Map<String, String> map = new HashMap<>();
        if(x >= 1) map.put("result", "join success!");
        else map.put("result", "join fail!");
        return map;
    }

    @PutMapping(value = "/modify")
    @ApiOperation(notes="회원 정보 수정", value="User 객체 수정")
    public Map<String, String> modify(HttpSession session,@RequestBody User user) throws Exception {
    	User sessionUser=(User)session.getAttribute("user");
    	sessionUser.setName(user.getName());
    	
        int x = service.modify((User)session.getAttribute("user"));

        Map<String, String> map = new HashMap<>();
        if(x >= 1) {
        	map.put("result", "modify success!");
        	session.setAttribute("user", sessionUser);
        }
        else map.put("result", "modify fail!");
        return map;
    }

    @GetMapping(value = "")
    @ApiOperation(notes="회원 정보 조회", value="User 객체 조회")
    public User search(HttpSession session) throws Exception {
    	User sessionUser = (User)session.getAttribute("user");
    	if(sessionUser != null) return service.search(sessionUser.getId());
    	else throw new Exception();
    }

    @DeleteMapping(value = "")
    @ApiOperation(notes="회원 탈퇴", value="User 객체 삭제")
    public Map<String, String> withdraw(HttpSession session) throws Exception {
    	User sessionUser = (User)session.getAttribute("user");
    	if(sessionUser == null) throw new Exception();
    	
        int x = service.withdraw(sessionUser.getId());

        Map<String, String> map = new HashMap<>();
        if(x >= 1) map.put("result", "withdraw success!");
        else map.put("result", "withdraw fail!");
        return map;
    }

    @PutMapping(value = "/findPw")
    @ApiOperation(notes="비밀번호 찾기", value="비밀번호 찾기")
    public Map<String, String> findPw (@RequestBody Map<String, String> rb, HttpSession session) throws Exception{
    	User sessionUser = (User)session.getAttribute("user");
    	
        User user = service.search(sessionUser.getId());
        user.setPw(rb.get("pw"));
        int x = service.newPw(user);

        Map<String, String> map = new HashMap<>();
        if(x >= 1) {
        	map.put("result", "findPw success!");
        	session.setAttribute("user", user);
        }
        else map.put("result", "findPw fail!");
        return map;
    }
    
    @PostMapping(value = "/id/check")
    @ApiOperation(notes="아이디 중복 검사", value="아이디 중복 검사")
    public Map<String, String> id_check(@RequestBody Map<String, String> rb) throws Exception {
    	String id = rb.get("id");
        String already_exist = service.id_check(id);

        Map<String, String> map = new HashMap<>();
        if(already_exist == null) map.put("result", "available");
        else map.put("result", "already exist");
        return map;
    }
    
    @PostMapping(value = "/name/check")
    @ApiOperation(notes="이름 중복 검사", value="이름 중복 검사")
    public Map<String, String> name_check(@RequestBody Map<String, String> rb) throws Exception {
    	String name = rb.get("name");
        String already_exist = service.name_check(name);

        Map<String, String> map = new HashMap<>();
        if(already_exist == null) map.put("result", "available");
        else map.put("result", "already exist");
        return map;
    }
}