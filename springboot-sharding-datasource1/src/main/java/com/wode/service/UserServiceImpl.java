package com.wode.service;

import com.wode.entity.User;
import com.wode.mapper.UserMapper1;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper1 userMapper;
	
	@Override
	public List<User> list() {
		return userMapper.list();
	}

	@Override
	public Long add(User user) {
		return userMapper.addUser(user);
	}

	@Override
	public User findById(Long id) {
		return userMapper.findById(id);
	}

	@Override
	public User findByName(String name) {
		return userMapper.findByName(name);
	}

}
