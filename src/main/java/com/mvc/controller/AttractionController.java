package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.service.AttractionService;
import com.mvc.service.JwtServiceImpl;
import com.mvc.vo.Attraction;
import com.mvc.vo.AttractionLike;
import com.mvc.vo.AttractionReview;
import com.mvc.vo.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/attraction")
@Api("여행지 컨트롤러  API")
public class AttractionController {
	
	@Autowired
    AttractionService service;
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@GetMapping(value = "/{sido_code}")
    @ApiOperation(notes="지역별 관광지 정보 찾기", value="sido_code 별 관광지 정보 찾기")
    public List<Attraction> selectBySidoCode(@PathVariable String sido_code) throws Exception {
        return service.selectBySidoCode(sido_code);
    }
	
	@GetMapping(value="/id/{content_id}")
	@ApiOperation(notes="content_id 여행지 정보 조회",value="content_id 여행지 정보 조회")
	public Attraction selectByContentId(@PathVariable String content_id) throws Exception{
		Attraction attr=service.selectByContentId(content_id);
		return attr;
	}
	
	@GetMapping(value = "/search")
    @ApiOperation(notes="sido_code & content_type_id 별 관광지 정보 찾기", value="sido_code & content_type_id 별 관광지 정보 찾기")
    public List<Attraction> selectBySidoCodeAndContentTypeId(@RequestParam("sido_name") String sido_name, @RequestParam("content_type_name") String content_type_name, HttpServletRequest request) throws Exception {
		String userid = jwtService.getUserId(request.getHeader("access-token"));
		
		if(userid == null) {
			System.out.println("null");
			return service.selectBySidoCodeAndContentTypeId(sido_name, content_type_name);
		}
		else {
			System.out.println(userid);
			return service.selectBySidoCodeAndContentTypeIdWithLogin(sido_name, content_type_name, userid);
		}
	
    }

	@PostMapping(value = "/registration")
    @ApiOperation(notes="여행지 등록", value="Attraction 등록")
    public Map<String, String> registration(@RequestBody Attraction attr, HttpSession session) throws Exception {
		User sessionUser=(User)session.getAttribute("user");
		
		Map<String, String> map = new HashMap<>();
		if(sessionUser.getRole().equals("admin")) { // admin만 attraction 추가 가능
			attr.setUser_id(sessionUser.getId());
			int x = service.registration(attr);

	        if(x >= 1) map.put("result", "registration success!");
	        else map.put("result", "registration fail!");
		}
		else {
			map.put("result", "User not matched");
		}
		
        return map;
    }
	
	@DeleteMapping(value = "/{content_id}")
    @ApiOperation(notes="여행지 정보 삭제", value="attraction_info 삭제")
    public Map<String, String> delete(@PathVariable String content_id) throws Exception {
		int x = service.delete(content_id);

        Map<String, String> map = new HashMap<>();
        if(x >= 1) map.put("result", "delete success!");
        else map.put("result", "delete fail!");
        return map;
    }
	
	@PutMapping(value = "/modify")
    @ApiOperation(notes="여행지 정보 수정", value="여행지 정보 수정")
    public Map<String, String> modify(@RequestBody Attraction attr) throws Exception {
        int x = service.modify(attr);

        Map<String, String> map = new HashMap<>();
        if(x >= 1) map.put("result", "modify success!");
        else map.put("result", "modify fail!");
        return map;
    }
	
	@GetMapping(value = "/hotplace")
    @ApiOperation(notes="좋아요 많이 받은 상위 10개의 여행지 정보", value="좋아요 많이 받은 상위 10개의 여행지 정보")
    public List<Attraction> hotplace() throws Exception {
        return service.hotplace();
    }
	
	@PostMapping(value = "/like/{content_id}")
    @ApiOperation(notes="여행지 좋아요", value="여행지 좋아요")
    public Map<String, String> like(@PathVariable String content_id, HttpServletRequest request) throws Exception {
		String user_id = jwtService.getUserId(request.getHeader("access-token"));

		Map<String, String> map = new HashMap<>();
		if(user_id == null) {
			map.put("result", "login");
		}
		else {
			AttractionLike attrLike = new AttractionLike(content_id, user_id);
			int x = service.like(attrLike);

	        if(x >= 1) map.put("result", "like success!");
	        else map.put("result", "like fail!");
		}

        return map;
    }
	
	@GetMapping(value="/islike/{content_id}")
	@ApiOperation(notes="좋아요 여부", value="해당 user_id가 해당 content_id를 좋아요했는지에 대한 여부 체크")
	public Map<String,String> islike(@PathVariable String content_id, HttpSession session) throws Exception{
		Map<String,String> map=new HashMap<>();
		User sessionUser=(User)session.getAttribute("user");
		AttractionLike attrlike=new AttractionLike(content_id,sessionUser.getId()); //임의로 넣음 (삭제해야함)
		
		int x=service.isLike(attrlike);
		map.put("result", Integer.toString(x));
		map.put("user_id", sessionUser.getId());
		return map;
	}
	
	
	@DeleteMapping(value = "/like/{content_id}")
    @ApiOperation(notes="여행지 좋아요 취소", value="여행지 좋아요 취소")
    public Map<String, String> delete_like(@PathVariable String content_id, HttpServletRequest request) throws Exception {
		String user_id = jwtService.getUserId(request.getHeader("access-token"));
		
		Map<String, String> map = new HashMap<>();
		if(user_id == null) {
			map.put("result", "login");
		}
		else {
			AttractionLike attrLike = new AttractionLike(content_id, user_id);
			int x = service.delete_like(attrLike);

	        if(x >= 1) map.put("result", "delete like success!");
	        else map.put("result", "delete like fail!");
		}

        return map;
    }
	
	@PostMapping(value = "/review/{content_id}")
    @ApiOperation(notes="여행지 리뷰 추가", value="여행지 리뷰 추가")
    public Map<String, String> add_review(@RequestBody AttractionReview attrReview, @PathVariable String content_id, HttpServletRequest request) throws Exception {
		String user_id = jwtService.getUserId(request.getHeader("access-token"));
		
		Map<String, String> map = new HashMap<>();
		if(user_id == null) {
			map.put("result", "login");
		}
		else {
			attrReview.setUser_id(user_id);
			attrReview.setContent_id(content_id);
			
			int x = service.add_review(attrReview);

	        if(x >= 1) map.put("result", "add review success!");
	        else map.put("result", "add review fail!");
		}
        return map;
    }
	
	@DeleteMapping(value = "/review/delete/{review_id}")
    @ApiOperation(notes="여행지 리뷰 삭제", value="여행지 리뷰 삭제")
    public Map<String, String> delete_review(@PathVariable String review_id, HttpServletRequest request) throws Exception {
		String user_id = jwtService.getUserId(request.getHeader("access-token"));
		String owner = service.get_review_info(review_id);
		
		Map<String, String> map = new HashMap<>();
		if(user_id == null) {
			map.put("result", "login");
		}
		else if(!user_id.equals(owner)) {
			map.put("result", "User not matched");
		}
		else {
			int x = service.delete_review(review_id);

	        if(x >= 1) map.put("result", "delete review success!");
	        else map.put("result", "delete review fail!");
		}
		
		return map;
    }
	
	@PutMapping(value = "/review/modify/{review_id}")
    @ApiOperation(notes="여행지 리뷰 수정", value="여행지 리뷰 수정")
    public Map<String, String> modify_review(@PathVariable String review_id, HttpServletRequest request) throws Exception {
		String user_id = jwtService.getUserId(request.getHeader("access-token"));
		String owner = service.get_review_info(review_id);
		
		Map<String, String> map = new HashMap<>();
		if(user_id == null) {
			map.put("result", "login");
		}
		else if(!user_id.equals(owner)) {
			map.put("result", "User not matched");
		}
		else { // 본인 글일 때만 수정
			int x = service.modify_review(review_id);

	        if(x >= 1) map.put("result", "modify review success!");
	        else map.put("result", "modify review fail!");
		}
		
		return map;
    }
	
	@GetMapping(value = "/review/list/{content_id}")
    @ApiOperation(notes="content_id 여행지에 해당하는 리뷰 리스트", value="content_id 여행지에 해당하는 리뷰 리스트")
    public List<AttractionReview> review_list(@PathVariable String content_id) throws Exception {
        return service.review_list(content_id);
    }
	
	@GetMapping(value = "/ad")
    @ApiOperation(notes="광고 여행지 랜덤으로 3개 가져오기", value="광고 여행지 랜덤으로 3개 가져오기")
    public List<Attraction> select_ad_attraction() throws Exception {
        return service.select_ad_attraction();
    }
}
