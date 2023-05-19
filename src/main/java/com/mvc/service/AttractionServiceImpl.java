package com.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.mapper.AttractionMapper;
import com.mvc.vo.Attraction;
import com.mvc.vo.AttractionLike;

@Service
public class AttractionServiceImpl implements AttractionService {
	
	@Autowired
	AttractionMapper mapper;

	@Override
	public List<Attraction> selectBySidoCode(String sido_code) throws Exception {
		return mapper.selectBySidoCode(sido_code);
	}

	@Override
	public List<Attraction> selectBySidoCodeAndContentTypeId(String sido_code, String content_type_id)
			throws Exception {
		return mapper.selectBySidoCodeAndContentTypeId(sido_code, content_type_id);
	}

	@Override
	public int registration(Attraction attr) throws Exception{
		int content_id = mapper.getMaxContentId();
		System.out.println(content_id);
		attr.setContent_id(String.valueOf(content_id + 1));
		return mapper.registration(attr);
	}

	@Override
	public int delete(String content_id) throws Exception{
		return mapper.delete(content_id);
	}

	@Override
	public int modify(Attraction attr) throws Exception {
		return mapper.modify(attr);
	}

	@Override
	public List<Attraction> hotplace() throws Exception {
		return mapper.hotplace();
	}

	@Override
	public int like(AttractionLike attrLike) throws Exception {
		return mapper.like(attrLike);
	}

}
