package com.zsls.repository;

import com.zsls.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@InterfaceName UserRepository
 *@Description TODO
 *@Author zsls
 *@Date 2019/1/23 18:26
 *@Version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	/**
	 * @Author zsls
	 * @Description //TODO 
	 * @Date 18:30 2019/1/23
	 * @Param [collegeCode]
	 * @return java.util.List<com.zsls.entity.User>
	 */
	List<User> queryAllByCollegeCode(String collegeCode);

}
