package com.mvc.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionReview {
	private String review_id;
	private String user_id;
	private String content_id;
	private String review_content;
	private String creation_date;
}
