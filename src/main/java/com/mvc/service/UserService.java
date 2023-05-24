package com.mvc.service;

import com.mvc.vo.User;

public interface UserService {
	
	User login(User user) throws Exception;
	void saveRefreshToken(String id, String refreshToken) throws Exception;
	User userInfo(String id) throws Exception;
	void deleRefreshToken(String id) throws Exception;
	Object getRefreshToken(String id) throws Exception;
	
	int join(User user) throws Exception;
	int modify(User user) throws Exception;
	User search(String id) throws Exception;
	int withdraw(String id) throws Exception;
	int newPw(User user) throws Exception;
	String id_check(String id) throws Exception;
	String name_check(String name) throws Exception;
}
