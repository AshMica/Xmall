package com.augustars.xmall.service;


import java.util.Map;

import com.augustars.xmall.entity.Manager;

public interface ManagerService {
	public Manager getManagerByLoginName(String loginName)throws Exception;

	public Map<String, Object> getManagerListBypage(Integer pageNum,Integer pageSize,String username) throws Exception ;
}
