package com.augustars.xmall.service;

import com.augustars.xmall.entity.Role;
import com.augustars.xmall.pojo.XMallPage;

public interface RoleService {

	public XMallPage<Role> getRoleListByPage(Integer pageNum, Integer pageSize, String roleName) throws Exception;

	public boolean roleAuthc(Long roleId, String[] ids) throws Exception;

	public boolean roleDisable(Long roleId, String[] ids) throws Exception;

}
