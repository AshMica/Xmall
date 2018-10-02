package com.augustars.xmall.swicher.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.augustars.xmall.entity.Role;
import com.augustars.xmall.pojo.XMallPage;
import com.augustars.xmall.service.RoleService;
import com.augustars.xmall.switcher.RoleSwitcher;

@Component("roleSwitcher")
@Service(version="1.0.0")
public class RoleSwitcherImpl implements RoleSwitcher{
	@Resource(name="roleService")
	private RoleService roleService;
	
	
	public XMallPage<Role> getRoleListPage(Integer pageNum, Integer pageSize, String roleName) throws Exception {
		
		return roleService.getRoleListByPage(pageNum,pageSize,roleName);
	}


	public boolean roleAuthc(Long roleId, String[] ids) throws Exception {
		
		return roleService.roleAuthc(roleId,ids);
	}


	public boolean roleDisable(Long roleId, String[] ids) throws Exception {
		return roleService.roleDisable(roleId,ids);
	}

}
