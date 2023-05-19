package com.mvc.service;

import java.util.List;

import com.mvc.vo.Notice;


public interface NoticeService {
	Notice selectByNoticeId(String notice_id) throws Exception;
	List<Notice> selectAll() throws Exception;
	boolean insertNotice(Notice notice) throws Exception;
	boolean deleteNotice(String notice_id) throws Exception;
	boolean modifyNotice(Notice notice) throws Exception;
}
