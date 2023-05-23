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
import org.springframework.web.bind.annotation.RestController;

import com.mvc.service.BoardService;
import com.mvc.vo.Board;
import com.mvc.vo.User;

import io.swagger.annotations.ApiOperation;

@RestController
public class BoardController {
	@Autowired
	BoardService service;
	
	@GetMapping(value="/api/board")
	@ApiOperation(notes="게시판 조회", value="게시판 조회")
	public List<Board> selectAll() throws Exception{
		List<Board> b=service.selectAll();
		return b;
	}
	
	@PostMapping(value="/api/board")
	@ApiOperation(notes="게시판 글 등록",value="게시판 글 등록")
	public Map<String,String> insertBoard(@RequestBody Board board,HttpSession session) throws Exception{
		Map<String,String> map=new HashMap<>();
		User SessionUser=(User)session.getAttribute("user");
		
		
		if(SessionUser==null) {
			map.put("result", "로그인 하세요");
		}
		else {
			board.setUser_id(SessionUser.getId());
			boolean ch=service.insertBoard(board);
			if(ch) {
				map.put("result", "board 등록 완료");
			}
			else {
				map.put("result", "board 등록 실패");
			}
		}
		
		
		return map;
	}
	
	@DeleteMapping(value="/api/board/delete")
	@ApiOperation(notes="게시글 삭제",value="게시글 삭제")
	public Map<String,String> deleteBoard(@RequestBody Board board,HttpSession session) throws Exception{
		Map<String,String> map=new HashMap<>();
		User SessionUser=(User)session.getAttribute("user");
		if(SessionUser==null) {
			map.put("result", "로그인 하세요");
		}else {
			board.setUser_id(SessionUser.getId());
			boolean ch=service.deleteBoard(board);
			if(ch) {
				map.put("result", "board 삭제 성공");
			}else {
				map.put("result", "board 삭제 실패");
			}
		}
		
		
		return map;
	}
	
	@PutMapping(value="/api/board/modify")
	@ApiOperation(notes="게시글 수정", value="게시글 수정")
	public Map<String,String> modifyBoard(HttpSession session, @RequestBody Board board) throws Exception{
		Map<String,String> map=new HashMap<>();
		User SessionUser=(User)session.getAttribute("user");

		if(SessionUser==null) {
			map.put("result", "로그인 하세요");
		}else {
			board.setUser_id(SessionUser.getId());
			boolean ch=service.modifyBoard(board);
			if(ch) {
				map.put("result", "board 수정 완료");
			}else {
				map.put("result", "board 수정 실패");
			}
		}
		return map;
	}
	
	@GetMapping(value="/api/board/{board_id}")
	@ApiOperation(notes="board_id인 게시글 조회",value="board_id인 게시글 조회")
	public Board detailBoard(@PathVariable String board_id) throws Exception{
		Board b=service.detailBoard(board_id);
		return b;
	}
}
