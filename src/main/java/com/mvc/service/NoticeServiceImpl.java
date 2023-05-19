package com.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.mapper.NoticeMapper;
import com.mvc.vo.Notice;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	NoticeMapper mapper;

	@Override
	public Notice selectByNoticeId(String notice_id) throws Exception {
		return mapper.selectByNoticeId(notice_id);
	}

	@Override
	public List<Notice> selectAll() throws Exception {
		
		return mapper.selectAll();
	}

	@Override
	public boolean insertNotice(Notice notice) throws Exception {
		
		return mapper.insertNotice(notice);
	}

	@Override
	public boolean deleteNotice(String notice_id) throws Exception {
		
		return mapper.deleteNotice(notice_id);
	}

	@Override
	public boolean modifyNotice(Notice notice) throws Exception {
		
		return mapper.modifyNotice(notice);
	}

}
