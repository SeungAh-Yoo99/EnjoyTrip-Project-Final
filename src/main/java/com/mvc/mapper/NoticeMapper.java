package com.mvc.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mvc.vo.Notice;

@Mapper
public interface NoticeMapper {
	List<Notice> selectAll() throws SQLException;
	Notice selectByNoticeId(String notice_id) throws SQLException;
	boolean insertNotice(Notice notice) throws SQLException;
	boolean deleteNotice(String notice_id) throws SQLException;
	boolean modifyNotice(Notice notice) throws SQLException;
}
