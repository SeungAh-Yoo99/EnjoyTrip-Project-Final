package com.mvc.service;

import java.util.List;

import com.mvc.vo.Board;

public interface BoardService {
	List<Board> selectAll() throws Exception;
	boolean insertBoard(Board board) throws Exception;
	boolean deleteBoard(Board board) throws Exception;
	boolean modifyBoard(Board board) throws Exception;
	Board detailBoard(String board_id) throws Exception;
}
