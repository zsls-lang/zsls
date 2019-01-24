package com.zsls;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsls.entity.User;
import com.zsls.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter5ApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(Chapter5ApplicationTests.class);

	@Autowired
	private UserMapper userMapper;

	@Test
	public void test() {
//		int i = userMapper.insertUser(new User("33", "4"));
//		LOGGER.info("结果：{}",i);
//		int j = userMapper.insertUser(new User("345", "43"));
//		LOGGER.info("结果：{}",j);
//		int k = userMapper.insertUser(new User("34534", "3"));
//		LOGGER.info("结果：{}",k);
		int count = userMapper.countByUsername("345");
		LOGGER.info("结果：{}",count);

		PageInfo<Object> pageinfo = PageHelper.startPage(1, 2).setOrderBy("id desc")
				.doSelectPageInfo(() -> userMapper.selectAll());
		LOGGER.info("分页 {} ",pageinfo.toString());

		PageHelper.startPage(1, 2).setOrderBy("id desc");
		PageInfo<User> userPageInfo = new PageInfo<>(userMapper.selectAll());
		LOGGER.info("分页 {} ",userPageInfo);

	}

//	// TODO 分页 + 排序 this.userMapper.selectAll() 这一句就是我们需要写的查询，有了这两款插件无缝切换各种数据库
//	final PageInfo<Object> pageInfo = PageHelper.startPage(1, 10).setOrderBy("id desc").doSelectPageInfo(() -> this.userMapper.selectAll());
//		log.info("[lambda写法] - [分页信息] - [{}]", pageInfo.toString());
//
//		PageHelper.startPage(1, 10).setOrderBy("id desc");
//	final PageInfo<User> userPageInfo = new PageInfo<>(this.userMapper.selectAll());
//		log.info("[普通写法] - [{}]", userPageInfo);

}

