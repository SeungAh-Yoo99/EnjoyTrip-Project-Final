package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.service.NoticeService;
import com.mvc.service.UserService;
import com.mvc.vo.Notice;
import com.mvc.vo.User;

import io.swagger.annotations.ApiOperation;

@RestController
public class NoticeController {
	
	@Autowired
	NoticeService service;
	UserService userservice;
	
	@GetMapping(value="/api/notice")
	@ApiOperation(notes="전체 공지사항 조회",value="전체 공지사항 조회")
	public List<Notice> selectAll() throws Exception{
		List<Notice> list=service.selectAll();
		return list;
	}
	
	@GetMapping(value="/api/notice/{notice_id}")
	@ApiOperation(notes="공지사항 내용 조회",value="notice_id인 공지사항 조회")
	public Notice selectByNoticeId(@PathVariable String notice_id) throws Exception{
		Notice n=service.selectByNoticeId(notice_id);
		return n;
	}
	
	@PostMapping(value="/api/notice")
	@ApiOperation(notes="공지사항 등록",value="공지사항 등록")
	public Map<String,String> insertNotice(@RequestBody Notice notice,HttpSession session) throws Exception {
		User sessionUser=(User) session.getAttribute("user");
		Map<String,String> map=new HashMap<>();
		if(sessionUser!=null && sessionUser.getRole().equals("admin")) {
			notice.setUser_id(sessionUser.getId());
			boolean ch=service.insertNotice(notice);
			map.put("result", "success");
		}else if(sessionUser!=null && !sessionUser.getRole().equals("admin")){
			map.put("result", "권한을 확인하세요");
		}else {
			map.put("result", "fail");
		}
		return map;
	}
	
	@DeleteMapping(value="/api/notice/{notice_id}")
	@ApiOperation(notes="공지사항 삭제", value="공지사항 삭제")
	public Map<String,String> deleteNotice(@PathVariable String notice_id) throws Exception{
		Map<String,String> map=new HashMap<>();
		boolean ch=service.deleteNotice(notice_id);
		if(ch) {
			map.put("result", "success");
		}else {
			map.put("result", "fail");
		}
		return map;
	}
	
	@PutMapping(value="/api/notice")
	@ApiOperation(notes="공지사항 수정",value="공지사항 수정")
	public Map<String,String> modifyNotice(@RequestBody Notice notice,HttpSession session) throws Exception{
		Map<String,String> map=new HashMap<>();
		User sessionUser=(User)session.getAttribute("user");
		if(sessionUser!=null && sessionUser.getRole().equals("admin")) {
			boolean ch=service.modifyNotice(notice);
			map.put("result", "success");
		}else if(sessionUser!=null && !sessionUser.getRole().equals("admin")) {
			map.put("result", "권한을 확인해주세요");
		}else {
			map.put("result", "fail");
		}
		return map;
	}
	

}
