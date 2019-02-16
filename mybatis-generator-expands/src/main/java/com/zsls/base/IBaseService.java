package com.zsls.base;

import java.util.List;

/**
 *
 * @param <T> 实体类
 */
 public interface IBaseService<T> {
	/**
	 * 根据id查询数据
	 *
	 * @param id
	 * @return
	 */
	 T queryById(Long id) ;

	/**
	 * 查询所有数据
	 *
	 * @return
	 */
	 List<T> queryList() ;

	/**
	 * 根据条件查询一条数据，如果有多条数据会抛出异常
	 *
	 * @param record
	 * @return
	 */
	 T queryUniqueResult(T record) ;

	/**
	 * 根据条件查询数据列表
	 *
	 * @param record
	 * @return
	 */
	 List<T> queryListByCondition(T record);

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param rows
	 * @param record
	 * @return
	 */
	 PageResult<T> queryPageListByCondition(T record, Integer page, Integer rows) ;
	/**
	 * 分页查询
	 *	排序
	 * @param page
	 * @param rows
	 * @param record
	 * @return
	 */
	PageResult<T> queryPageListOrderByCondition(T record, Integer page, Integer rows, String order) ;

	/**
	 * 新增数据，返回成功的条数
	 *
	 * @param record
	 * @return
	 */
	 Integer save(T record);

	/**
	 * 新增多条数据，返回成功的条数
	 *
	 * @param var1
	 * @return
	 */
	 int insertList(List<T> var1);

	/**
	 * 新增数据，使用不为null的字段，返回成功的条数
	 *
	 * @param record
	 * @return
	 */
	 Integer saveSelective(T record);

	/**
	 * 修改数据，返回成功的条数
	 *
	 * @param record
	 * @return
	 */
	 Integer update(T record);

	/**
	 * 修改数据，使用不为null的字段，返回成功的条数
	 *
	 * @param record
	 * @return
	 */
	 Integer updateSelective(T record);

	/**
	 * 根据id删除数据
	 *
	 * @param id
	 * @return
	 */
	Integer deleteById(Long id);

	/**
	 * 批量删除
	 * @param clazz
	 * @param property
	 * @param values
	 * @return
	 */
	Integer deleteByIds(Class<T> clazz, String property, List<Object> values) ;

	/**
	 * 根据条件做删除
	 *
	 * @param record
	 * @return
	 */
	Integer deleteByWhere(T record) ;


}
