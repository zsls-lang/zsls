package com.zsls.controller;

import com.zsls.entity.KxlhXh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@ClassName SpringJdbcController
 *@Description TODO
 *@Author zk102
 *@Date 2019/1/22 18:22
 *@Version 1.0
 */
@RestController
@RequestMapping("/test")
public class SpringJdbcController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * @Author zsls
	 * @Description //查询所有
	 * @Date 18:31 2019/1/22
	 * @Param []
	 * @return java.util.List<com.zsls.entity.KxlhXh>
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<KxlhXh> queryKxlhXh(){
		String sql = "select * from t_kxlh_xh";
		return jdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper<>(KxlhXh.class));
	}

	/**
	 * @Author zsls
	 * @Description ////根据id查找
	 * @Date 16:12 2019/1/23
	 * @Param [kxlh]
	 * @return java.util.List<com.zsls.entity.KxlhXh>
	 */
	@RequestMapping(value = "/{kxlh}",method = RequestMethod.GET)
	public List<KxlhXh> queryKxlhXhByKxlh(@PathVariable String kxlh ){
		String sql = "select * from t_kxlh_xh where kxlh = ? ";
		return jdbcTemplate.query(sql,new Object[]{kxlh},new BeanPropertyRowMapper<>(KxlhXh.class));
	}

	@RequestMapping(value = "add/{kxlh}/{student_no}/{college_code}",method = RequestMethod.GET)
	public int addKxlhXh(@PathVariable String kxlh,@PathVariable String student_no,@PathVariable String college_code) {
		String sql = "insert into t_kxlh_xh(kxlh,student_no,college_code) values(?,?,?)";
		return jdbcTemplate.update(sql, kxlh, student_no,college_code);
	}


}
