package com.augustars.xmall.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.augustars.xmall.dao.MenuDao;
import com.augustars.xmall.dao.RoleDao;
import com.augustars.xmall.entity.Menu;
import com.augustars.xmall.entity.Role;
import com.augustars.xmall.pojo.XMallPage;
import com.augustars.xmall.service.RoleService;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService{

	@Resource(name="roleDao")
	private RoleDao roleDao;
	
	@Resource(name="menuDao")
	private MenuDao menuDao;
	
	
	public XMallPage<Role> getRoleListByPage(Integer pageNum, Integer pageSize, String roleName) throws Exception {
		
		
		//设定排序字段
		Sort sort = new Sort(Direction.ASC,"roleId");
		//设定分页对象                   pageNum数据库给值给1  但是有参构造要从0开始
		Pageable pageable = PageRequest.of(pageNum-1, pageSize,sort);
		Page<Role> page = null;
		//判断是否有模糊查询
		if (roleName == null) {
			//没有携带查询条件 直接调用dao层查询
			page = roleDao.findAll(pageable);
		}else {
			//携带查询条件的查询
			page = roleDao.findAll(new Specification<Role>() {
				public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.like(root.get("roleName"), "%"+roleName+"%");
					
					return query.where(predicate).getRestriction();
				}
			}, pageable);
		}
		//将page对象的数据传入XMallPage中
		XMallPage<Role> xmallPage = new XMallPage<>(page.getNumber(),  page.getSize(),  page.getContent(),  page.getTotalPages(),  page.getNumberOfElements());
		
		
		return xmallPage;
	}


	public boolean roleAuthc(Long roleId, String[] ids) throws Exception {
		//创建菜单表集合
		List<Menu> menuList = new ArrayList<Menu>();
		//循环Id
		for (String id : ids) {
			Long menuId = Long.parseLong(id);
			//根据menuId 获得对应的Menu对象
			
			Menu menu = menuDao.findMenuById(menuId);
			menuList.add(menu);
			
		}
		Role role = roleDao.findRoleById(roleId);
		//做role和menu之间的关联
		role.setMenuList(menuList);
		roleDao.save(role);
		
		
		return true;
	}

	public boolean roleDisable(Long roleId, String[] ids) throws Exception {
		//创建菜单表集合
		List<Menu> menuList = new ArrayList<>();
		//循环ID
		for (String id : ids) {
			Long menuId = Long.parseLong(id);
			//根据menuId 获得对应的Menu对象
			Menu menu = menuDao.findMenuById(menuId);
			menuList.add(menu);
		}
		Role role = roleDao.findRoleById(roleId);
		//做role和menu之间的关联
		role.setMenuList(menuList);
		roleDao.delete(role);
		
		
		return true;
	}

}








