package com.augustars.xmall.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.augustars.xmall.dao.base.baseDao;
import com.augustars.xmall.entity.Manager;

public interface ManagerDao extends baseDao<Manager, Long> {
//	SELECT * FROM xmall_manager m JOIN role r ON r.role_id = m.role_id;
//	String i = "from manager m join role r on r.role_id = m.role_id where m.loginName=:loginName";
	
//	@Query(value="from manager m join role r on r.role_id = m.role_id where m.loginName=:loginName")
	@Query("from Manager m where m.loginName=:loginName")
	public Manager findManagerByLoginName(@Param("loginName")String loginName) throws Exception;
}