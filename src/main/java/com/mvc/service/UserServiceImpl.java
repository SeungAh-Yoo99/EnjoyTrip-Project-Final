package com.mvc.service;

import com.mvc.mapper.UserMapper;
import com.mvc.vo.User;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserMapper mapper;
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public User login(User user) throws Exception {
		if (user.getId() == null || user.getPw() == null)
			return null;
		return sqlSession.getMapper(UserMapper.class).login(user);
	}
	
	@Override
	public void saveRefreshToken(String id, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", refreshToken);
		sqlSession.getMapper(UserMapper.class).saveRefreshToken(map);
	}

	@Override
	public User userInfo(String id) throws Exception {
		return sqlSession.getMapper(UserMapper.class).userInfo(id);
	}

	@Override
	public void deleRefreshToken(String id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", null);
		sqlSession.getMapper(UserMapper.class).deleteRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String id) throws Exception {
		return sqlSession.getMapper(UserMapper.class).getRefreshToken(id);
	}
	
	@Override
	public int join(User user) throws Exception {
		return mapper.join(user);
	}

	@Override
	public int modify(User user) throws Exception {
		return mapper.modify(user);
	}

	@Override
	public User search(String id) throws Exception {
		return mapper.search(id);
	}

	@Override
	public int withdraw(String id) throws Exception {
		return mapper.withdraw(id);
	}

	@Override
	public int newPw(User user) throws Exception {
		return mapper.newPw(user);
	}

	@Override
	public String id_check(String id) throws Exception {
		return mapper.id_check(id);
	}

	@Override
	public String name_check(String name) throws Exception {
		return mapper.name_check(name);
	}
}
