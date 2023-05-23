package com.mvc.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private String board_id;
	private String creation_date;
	private String board_content;
	private String user_id;
	private String board_title;
}
