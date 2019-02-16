package com.zsls.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public abstract class BaseServiceImpl<T> implements IBaseService<T> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BaseMapper<T> mapper;

	/**
	 * 根据id查询数据
	 *
	 * @param id
	 * @return
	 */
	@Override
	public T queryById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	/**
	 * 查询所有数据
	 *
	 * @return
	 */
	@Override
	public List<T> queryList() {
		return mapper.select(null);
	}

	/**
	 * 根据条件查询一条数据，如果有多条数据会抛出异常
	 *
	 * @param record
	 * @return
	 */
	@Override
	public T queryUniqueResult(T record) {
		return mapper.selectOne(record);
	}

	/**
	 * 根据条件查询数据列表
	 *
	 * @param record
	 * @return
	 */
	@Override
	public List<T> queryListByCondition(T record) {
		return mapper.select(record);
	}

	/**
	 * 分页查询
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param record
	 * @return
	 */
	@Override
	public PageResult<T> queryPageListByCondition(T record, Integer pageNum, Integer pageSize) {
		// 设置分页条件
		PageHelper.startPage(pageNum, pageSize);
		List<T> list = this.queryListByCondition(record);
		Page<T> page = (Page<T>) list;
		return new PageResult<T>(list, page.getTotal(), pageNum, pageSize);
	}

	@Override
	public PageResult<T> queryPageListOrderByCondition(T record, Integer page, Integer rows, String order) {
		// 设置分页条件
		PageHelper.startPage(page, rows,order);
		List<T> list = this.queryListByCondition(record);
		Page<T> row = (Page<T>) list;
		return new PageResult<T>(list, row.getTotal(), page, rows);
	}

	/**
	 * 新增数据，返回成功的条数
	 *
	 * @param record
	 * @return
	 */
	@Override
	public Integer save(T record) {
		return mapper.insert(record);
	}

	@Override
	public int insertList(List<T> var1){
		return mapper.insertList(var1);
	}

	/**
	 * 新增数据，使用不为null的字段，返回成功的条数
	 *
	 * @param record
	 * @return
	 */
	@Override
	public Integer saveSelective(T record) {
		return mapper.insertSelective(record);
	}

	/**
	 * 修改数据，返回成功的条数
	 *
	 * @param record
	 * @return
	 */
	@Override
	public Integer update(T record) {
		return mapper.updateByPrimaryKey(record);
	}

	/**
	 * 修改数据，使用不为null的字段，返回成功的条数
	 *
	 * @param record
	 * @return
	 */
	@Override
	public Integer updateSelective(T record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据id删除数据
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Integer deleteById(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 * @param clazz
	 * @param property
	 * @param values
	 * @return
	 */
	@Override
	public Integer deleteByIds(Class<T> clazz, String property, List<Object> values) {
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, values);
		return mapper.deleteByExample(example);
	}

	/**
	 * 根据条件做删除
	 *
	 * @param record
	 * @return
	 */
	@Override
	public Integer deleteByWhere(T record) {
		return mapper.delete(record);
	}

}
