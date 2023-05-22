package com.mvc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mvc.vo.Attraction;
import com.mvc.vo.AttractionLike;
import com.mvc.vo.AttractionReview;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface AttractionMapper {

	List<Attraction> selectBySidoCode(String sido_code) throws SQLException;

	List<Attraction> selectBySidoCodeAndContentTypeId(String sido_name, String content_type_name) throws SQLException;
	
	Attraction selectByContentId(String content_id) throws SQLException;
	
	
	Integer getMaxContentId() throws SQLException;

	int registration(Attraction attr) throws SQLException;

	int delete(String content_id) throws SQLException;

	int modify(Attraction attr) throws SQLException;

	List<Attraction> hotplace() throws SQLException;

	int like(AttractionLike attrLike) throws SQLException;

	int delete_like(AttractionLike attrLike) throws SQLException;

	int add_review(AttractionReview attrReview) throws SQLException;

	int delete_review(String review_id) throws SQLException;

	String get_review_info(String review_id) throws SQLException;

	int modify_review(String review_id) throws SQLException;

	List<AttractionReview> review_list(String content_id) throws SQLException;

	List<Attraction> select_ad_attraction() throws SQLException;
}
