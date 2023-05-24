package com.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.mapper.BoardMapper;
import com.mvc.vo.Board;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardMapper mapper;
	
	@Override
	public List<Board> selectAll() throws Exception {
		
		return mapper.selectAll();
	}

	@Override
	public boolean insertBoard(Board board) throws Exception {
		return mapper.insertBoard(board);
	}

	@Override
	public boolean deleteBoard(Board board) throws Exception {
		return mapper.deleteBoard(board);
	}

	@Override
	public boolean modifyBoard(Board board) throws Exception {
		return mapper.modifyBoard(board);
	}

	@Override
	public Board detailBoard(String board_id) throws Exception {
		return mapper.detailBoard(board_id);
	}

}
