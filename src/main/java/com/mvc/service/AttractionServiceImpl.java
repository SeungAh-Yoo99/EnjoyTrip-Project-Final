package com.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.mapper.AttractionMapper;
import com.mvc.vo.Attraction;
import com.mvc.vo.AttractionLike;
import com.mvc.vo.AttractionReview;

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

	@Override
	public int delete_like(AttractionLike attrLike) throws Exception {
		return mapper.delete_like(attrLike);
	}

	@Override
	public int add_review(AttractionReview attrReview) throws Exception {
		return mapper.add_review(attrReview);
	}
	
	@Override
	public String get_review_info(String review_id) throws Exception {
		return mapper.get_review_info(review_id);
	}

	@Override
	public int delete_review(String review_id) throws Exception {
		return mapper.delete_review(review_id);
	}

	@Override
	public int modify_review(String review_id) throws Exception {
		return mapper.modify_review(review_id);
	}

	@Override
	public List<AttractionReview> review_list(String content_id) throws Exception {
		return mapper.review_list(content_id);
	}

	@Override
	public List<Attraction> select_ad_attraction() throws Exception {
		return mapper.select_ad_attraction();
	}

	@Override
	public Attraction selectByContentId(String content_id) throws Exception {
		return mapper.selectByContentId(content_id);
	}

}
