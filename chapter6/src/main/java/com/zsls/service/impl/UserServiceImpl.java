package com.zsls.service.impl;

import com.zsls.entity.User;
import com.zsls.mapper.UserMapper;
import com.zsls.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;


	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Cacheable(value = "user", key = "#id")
	@Override
	public User get(Integer id) {
		log.info("进入 get 方法");
		return userMapper.queryUserById(id);
	}

	@CachePut(value = "user", key = "#user.id", condition = "#user.id < 5")
	@Override
	public User saveOrUpdate(User user) {
		log.info("进入 saveOrUpdate 方法");
		userMapper.insert(user);
		return user;
	}

	@CacheEvict(value = "user", key = "#id", allEntries = true, beforeInvocation = true)
	@Override
	public void delete(Integer id) {
		User user = new User();
		user.setId(id);
		userMapper.delete(user);
		log.info("进入 delete 方法");
	}
}
