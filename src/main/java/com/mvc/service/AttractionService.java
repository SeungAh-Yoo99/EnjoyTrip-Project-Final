package com.mvc.service;

import java.util.List;

import com.mvc.vo.Attraction;
import com.mvc.vo.AttractionLike;

public interface AttractionService {

	List<Attraction> selectBySidoCode(String sido_code) throws Exception;

	List<Attraction> selectBySidoCodeAndContentTypeId(String sido_code, String content_type_id) throws Exception;

	int registration(Attraction attr) throws Exception;

	int delete(String content_id) throws Exception;

	int modify(Attraction attr) throws Exception;

	List<Attraction> hotplace() throws Exception;

	int like(AttractionLike attrLike) throws Exception;

}
