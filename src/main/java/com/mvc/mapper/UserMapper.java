package com.mvc.mapper;

import com.mvc.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface UserMapper {
	User login(User user) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	User userInfo(String id) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String id) throws SQLException;
	
	int join(User user) throws SQLException;
	int modify(User user) throws SQLException;
	User search(String id) throws SQLException;
	int withdraw(String id) throws SQLException;
	int newPw(User user) throws SQLException;
	String id_check(String id) throws SQLException;
	String name_check(String name) throws SQLException;
}
