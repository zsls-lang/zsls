package com.zsls.mapper;

import com.zsls.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 *@InterfaceName UserMapper
 *@Description
 * * <p>第一种是基于mybatis3.x版本后提供的注解方式<p/>
 *  * <p>第二种是早期写法，将SQL写在 XML 中<p/>
 *@Author zsls
 *@Date 2019/1/24 11:40
 *@Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

	/**
	 * @Author zsls
	 * @Description //用户名统计
	 * @Date 16:20 2019/1/24
	 * @Param [username]
	 * @return int
	 */
	int countByUsername(String studentNo);

}
