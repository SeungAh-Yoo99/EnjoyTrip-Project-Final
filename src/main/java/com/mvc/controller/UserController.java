package com.mvc.controller;

import com.mvc.service.UserService;
import com.mvc.vo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class UserController {
	
	@Autowired
    UserService service;

    @PostMapping(value = "/api/user/join")
    @ApiOperation(notes="회원가입", value="User 객체 등록")
    public Map<String, String> join(@RequestBody User user) throws Exception {
        int x = service.join(user);

        Map<String, String> map = new HashMap<>();
        if(x >= 1) map.put("result", "join success!");
        else map.put("result", "join fail!");
        return map;
    }

    @PutMapping(value = "/api/user/modify")
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

    @GetMapping(value = "/api/user")
    @ApiOperation(notes="회원 정보 조회", value="User 객체 조회")
    public User search(HttpSession session) throws Exception {
    	User sessionUser = (User)session.getAttribute("user");
    	if(sessionUser != null) return service.search(sessionUser.getId());
    	else throw new Exception();
    }

    @DeleteMapping(value = "/api/user")
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

	@PostMapping(value = "/api/user/login")
	@ApiOperation(notes="로그인", value="로그인")
	public Map<String, String> login(@RequestBody User user, HttpSession session) throws Exception{
        User u = service.login(user);
        
        Map<String, String> map = new HashMap<>();
        if(u == null) {
            map.put("result", "login fail!");
            return map;
        }

        session.setAttribute("user", u);
        map.put("id", u.getId());
        map.put("name", u.getName());
        map.put("role", u.getRole());
        return map;
	}

    @GetMapping(value = "/api/user/logout")
    @ApiOperation(notes="로그아웃", value="로그아웃")
    public Map<String, String> logout (HttpSession session) throws Exception{
        session.invalidate();

        Map<String, String> map = new HashMap<>();
        map.put("result", "logout success");
        return map;
    }

    @PutMapping(value = "/api/user/findPw")
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
    
    @PostMapping(value = "/api/user/id/check")
    @ApiOperation(notes="아이디 중복 검사", value="아이디 중복 검사")
    public Map<String, String> id_check(@RequestBody Map<String, String> rb) throws Exception {
    	String id = rb.get("id");
        String already_exist = service.id_check(id);

        Map<String, String> map = new HashMap<>();
        if(already_exist == null) map.put("result", "available");
        else map.put("result", "already exist");
        return map;
    }
    
    @PostMapping(value = "/api/user/name/check")
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