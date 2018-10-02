package com.augustars.xmall.swicher.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.augustars.xmall.entity.Manager;
import com.augustars.xmall.service.ManagerService;
import com.augustars.xmall.switcher.ManagerSwitcher;


@Component("managerSwitcher")
@Service(version="1.0.0")
public class ManagerSwitcherImpl implements ManagerSwitcher{
	@Resource(name="managerService")
	private ManagerService managerService;

	@Override
	public Manager getManagerByLogin(String loginName) throws Exception {
		Manager manager = managerService.getManagerByLoginName(loginName);
		return manager;
	}

	@Override
	public Map<String, Object> getManagerListByPage(Integer pageNum,Integer pageSize , String username) throws Exception {
		Map<String, Object> resultMap = managerService.getManagerListBypage(pageNum, pageSize, username);
		
		return resultMap;
	}

}
