package com.augustars.xmall.swicher.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.augustars.xmall.entity.Menu;
import com.augustars.xmall.entity.Role;
import com.augustars.xmall.pojo.base.Node;
import com.augustars.xmall.service.MenuService;
import com.augustars.xmall.switcher.MenuSwitcher;

@Component
@Service(version="1.0.0")
public class MenuSwitcherImpl implements MenuSwitcher{

	@Resource(name="menuService")
	private MenuService menuService;
	
	public List<Menu> getMenuListByRole(Long role) throws Exception {
		List<Menu> menuList = menuService.getMenuListByRole(role);
		
		return menuList;
	}

	public List<Node> getNodeList(Long roleId) throws Exception {
		System.out.println("调用menuswitcher");
		return menuService.getNodeList(roleId);
	}

	public List<Node> getDisableNodeList(Long roleId) throws Exception {
		return menuService.getDisableNodeList(roleId);
	}



}
