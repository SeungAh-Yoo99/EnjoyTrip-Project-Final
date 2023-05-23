package com.mvc.service;

import java.util.List;

import com.mvc.vo.Attraction;
import com.mvc.vo.AttractionLike;
import com.mvc.vo.AttractionReview;

public interface AttractionService {

	List<Attraction> selectBySidoCode(String sido_code) throws Exception;
	
	List<Attraction> selectBySidoCodeAndContentTypeId(String sido_name, String content_type_name) throws Exception;

	List<Attraction> selectBySidoCodeAndContentTypeIdWithLogin(String sido_code, String content_type_id, String user_id) throws Exception;

	Attraction selectByContentId(String content_id) throws Exception;
	
	int registration(Attraction attr) throws Exception;

	int delete(String content_id) throws Exception;

	int modify(Attraction attr) throws Exception;

	List<Attraction> hotplace() throws Exception;

	int like(AttractionLike attrLike) throws Exception;

	int delete_like(AttractionLike attrLike) throws Exception;

	int add_review(AttractionReview attrReview) throws Exception;

	int delete_review(String review_id) throws Exception;

	String get_review_info(String review_id) throws Exception;

	int modify_review(String review_id) throws Exception;

	List<AttractionReview> review_list(String content_id) throws Exception;

	List<Attraction> select_ad_attraction() throws Exception;
	
	int isLike(AttractionLike attrlike) throws Exception;
}
