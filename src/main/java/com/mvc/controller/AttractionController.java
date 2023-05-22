package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.service.AttractionService;
import com.mvc.vo.Attraction;
import com.mvc.vo.AttractionLike;
import com.mvc.vo.AttractionReview;
import com.mvc.vo.User;

import io.swagger.annotations.ApiOperation;
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
public class AttractionController {
	
	@Autowired
    AttractionService service;
	
	@GetMapping(value = "/api/attraction/{sido_code}")
    @ApiOperation(notes="지역별 관광지 정보 찾기", value="sido_code 별 관광지 정보 찾기")
    public List<Attraction> selectBySidoCode(@PathVariable String sido_code) throws Exception {
        return service.selectBySidoCode(sido_code);
    }
	
	@GetMapping(value="/api/attraction/id/{content_id}")
	@ApiOperation(notes="content_id 여행지 정보 조회",value="content_id 여행지 정보 조회")
	public Attraction selectByContentId(@PathVariable String content_id) throws Exception{
		Attraction attr=service.selectByContentId(content_id);
		return attr;
	}
	
	@GetMapping(value = "/api/attraction/search")
    @ApiOperation(notes="지역별 원하는 컨텐츠 별 관광지 정보 찾기", value="sido_code & content_type_id 별 관광지 정보 찾기")
    public List<Attraction> selectBySidoCodeAndContentTypeId(@RequestParam("sido_name") String sidoName, @RequestParam("content_type_name") String contentTypeName) throws Exception {
		String sido_name = sidoName;
		String content_type_name = contentTypeName;
        return service.selectBySidoCodeAndContentTypeId(sido_name, content_type_name);
    }

	@PostMapping(value = "/api/attraction/registration")
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
	
	@DeleteMapping(value = "/api/attraction/{content_id}")
    @ApiOperation(notes="여행지 정보 삭제", value="attraction_info 삭제")
    public Map<String, String> delete(@PathVariable String content_id) throws Exception {
		int x = service.delete(content_id);

        Map<String, String> map = new HashMap<>();
        if(x >= 1) map.put("result", "delete success!");
        else map.put("result", "delete fail!");
        return map;
    }
	
	@PutMapping(value = "/api/attraction/modify")
    @ApiOperation(notes="여행지 정보 수정", value="여행지 정보 수정")
    public Map<String, String> modify(@RequestBody Attraction attr) throws Exception {
        int x = service.modify(attr);

        Map<String, String> map = new HashMap<>();
        if(x >= 1) map.put("result", "modify success!");
        else map.put("result", "modify fail!");
        return map;
    }
	
	@GetMapping(value = "/api/attraction/hotplace")
    @ApiOperation(notes="좋아요 많이 받은 상위 10개의 여행지 정보", value="좋아요 많이 받은 상위 10개의 여행지 정보")
    public List<Attraction> hotplace() throws Exception {
        return service.hotplace();
    }
	
	@PostMapping(value = "/api/attraction/like")
    @ApiOperation(notes="여행지 좋아요", value="여행지 좋아요")
    public Map<String, String> like(@RequestBody AttractionLike attrLike, HttpSession session) throws Exception {
		User sessionUser=(User)session.getAttribute("user");
		attrLike.setUser_id(sessionUser.getId());
		
        int x = service.like(attrLike);

        Map<String, String> map = new HashMap<>();
        if(x >= 1) map.put("result", "like success!");
        else map.put("result", "like fail!");
        return map;
    }
	
	@DeleteMapping(value = "/api/attraction/like")
    @ApiOperation(notes="여행지 좋아요 취소", value="여행지 좋아요 취소")
    public Map<String, String> delete_like(@RequestBody AttractionLike attrLike, HttpSession session) throws Exception {
		User sessionUser=(User)session.getAttribute("user");
		attrLike.setUser_id(sessionUser.getId());
		
        int x = service.delete_like(attrLike);

        Map<String, String> map = new HashMap<>();
        if(x >= 1) map.put("result", "delete like success!");
        else map.put("result", "delete like fail!");
        return map;
    }
	
	@PostMapping(value = "/api/attraction/review/{content_id}")
    @ApiOperation(notes="여행지 리뷰 추가", value="여행지 리뷰 추가")
    public Map<String, String> add_review(@RequestBody AttractionReview attrReview, @PathVariable String content_id, HttpSession session) throws Exception {
		User sessionUser=(User)session.getAttribute("user");
		attrReview.setUser_id(sessionUser.getId());
		attrReview.setContent_id(content_id);
		
        int x = service.add_review(attrReview);

        Map<String, String> map = new HashMap<>();
        if(x >= 1) map.put("result", "add review success!");
        else map.put("result", "add review fail!");
        return map;
    }
	
	@DeleteMapping(value = "/api/attraction/review/delete/{review_id}")
    @ApiOperation(notes="여행지 리뷰 삭제", value="여행지 리뷰 삭제")
    public Map<String, String> delete_review(@PathVariable String review_id, HttpSession session) throws Exception {
		User sessionUser=(User)session.getAttribute("user");
		String owner = service.get_review_info(review_id);
		
		Map<String, String> map = new HashMap<>();
		if(sessionUser.getId().equals(owner)) { // 본인 글일 때만 삭제
			int x = service.delete_review(review_id);

	        if(x >= 1) map.put("result", "delete review success!");
	        else map.put("result", "delete review fail!");
		}
		else {
			map.put("result", "User not matched");
		}
		
		return map;
    }
	
	@PutMapping(value = "/api/attraction/review/modify/{review_id}")
    @ApiOperation(notes="여행지 리뷰 수정", value="여행지 리뷰 수정")
    public Map<String, String> modify_review(@PathVariable String review_id, HttpSession session) throws Exception {
		User sessionUser=(User)session.getAttribute("user");
		String owner = service.get_review_info(review_id);
		
		Map<String, String> map = new HashMap<>();
		if(sessionUser.getId().equals(owner)) { // 본인 글일 때만 수정
			int x = service.modify_review(review_id);

	        if(x >= 1) map.put("result", "modify review success!");
	        else map.put("result", "modify review fail!");
		}
		else {
			map.put("result", "User not matched");
		}
		
		return map;
    }
	
	@GetMapping(value = "/api/attraction/review/list/{content_id}")
    @ApiOperation(notes="content_id 여행지에 해당하는 리뷰 리스트", value="content_id 여행지에 해당하는 리뷰 리스트")
    public List<AttractionReview> review_list(@PathVariable String content_id) throws Exception {
        return service.review_list(content_id);
    }
	
	@GetMapping(value = "/api/attraction/ad")
    @ApiOperation(notes="광고 여행지 랜덤으로 3개 가져오기", value="광고 여행지 랜덤으로 3개 가져오기")
    public List<Attraction> select_ad_attraction() throws Exception {
        return service.select_ad_attraction();
    }
}
