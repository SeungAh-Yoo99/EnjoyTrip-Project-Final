package com.mvc.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
	private String notice_id;
	private String creation_date;
	private String content;
	private String user_id;
	private String notice_title;

}
