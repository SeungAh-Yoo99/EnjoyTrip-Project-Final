package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.service.AttractionService;
import com.mvc.vo.Attraction;
import com.mvc.vo.AttractionLike;
import com.mvc.vo.User;

import io.swagger.annotations.ApiOperation;

@RestController
public class AttractionController {
	
	@Autowired
    AttractionService service;
	
	@GetMapping(value = "/api/attraction/{sido_code}")
    @ApiOperation(notes="지역별 관광지 정보 찾기", value="sido_code 별 관광지 정보 찾기")
    public List<Attraction> selectBySidoCode(@PathVariable String sido_code) throws Exception {
        return service.selectBySidoCode(sido_code);
    }
	
	@GetMapping(value = "/api/attraction/search")
    @ApiOperation(notes="지역별 원하는 컨텐츠 별 관광지 정보 찾기", value="sido_code & content_type_id 별 관광지 정보 찾기")
    public List<Attraction> selectBySidoCodeAndContentTypeId(@RequestBody Map<String, String> rb) throws Exception {
		String sido_code = rb.get("sido_code");
		String content_type_id = rb.get("content_type_id");
        return service.selectBySidoCodeAndContentTypeId(sido_code, content_type_id);
    }

	@PostMapping(value = "/api/attraction/registration")
    @ApiOperation(notes="여행지 등록", value="Attraction 등록")
    public Map<String, String> registration(@RequestBody Attraction attr) throws Exception {
        int x = service.registration(attr);

        Map<String, String> map = new HashMap<>();
        if(x >= 1) map.put("result", "registration success!");
        else map.put("result", "registration fail!");
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
}
