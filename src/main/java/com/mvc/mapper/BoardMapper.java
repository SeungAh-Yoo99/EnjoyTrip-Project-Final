package com.mvc.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mvc.vo.Board;

@Mapper
public interface BoardMapper {
	List<Board> selectAll() throws SQLException;
	boolean insertBoard(Board board) throws SQLException;
	boolean deleteBoard(Board board) throws SQLException;
	boolean modifyBoard(Board board) throws SQLException;
	Board detailBoard(String board_id) throws SQLException;
}
