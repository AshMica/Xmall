package com.augustars.xmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.augustars.xmall.dao.MenuDao;
import com.augustars.xmall.entity.Menu;
import com.augustars.xmall.entity.Role;
import com.augustars.xmall.pojo.base.Node;
import com.augustars.xmall.service.MenuService;

@Service("menuService")
@Transactional
public class MenuServiceImpl implements MenuService{

	@Resource(name="menuDao")
	private MenuDao menuDao;
	
	public List<Menu> getMenuListByRole(Long role) throws Exception {
		System.out.println("roleId是"+role);
		List<Menu> menuList = menuDao.findFirstMenuList(role);
		//将获得的一级菜单列表进行循环,获得每个一级菜单的二级菜单集合
		for (Menu parent : menuList) {
			System.out.println("parent"+parent.getMenuId());
			List<Menu> childMenuList = menuDao.findSecondMenuList(role,parent.getMenuId());
			parent.setMenuList(childMenuList);
		}
		return menuList;
	}

	public List<Node> getNodeList(Long roleId) throws Exception {
		List<Node> nodeList = new ArrayList<Node>();
		//取出所有的菜单集合
		List<Menu> menuList = menuDao.findAll(new Sort(Direction.ASC, "sortOrder"));
		//查询该角色所对应的权限
		List<Menu> roleMenuList = menuDao.findMenuListByRole(roleId);
		
		//将Menu转换成Node
		if (menuList != null && menuList.size() > 0) {
			for (Menu menu : menuList) {
				Node node = new Node();
				node.setId(menu.getMenuId());
				node.setName(menu.getText());
				if (menu.getParent() != null) {
					node.setpId(menu.getParent().getMenuId());
					node.setOpen(false);
				}else {
					node.setpId(0L);
					node.setOpen(true);
				}
				//判断
				if (roleMenuList.contains(menu)) {
					node.setChecked(true);
					
				}else {
					node.setChecked(false);
				}
				
				System.out.println("内容是"+node.toString());
				
				nodeList.add(node);
			}
			
			
		}
		
		
		return nodeList;
	}

	public List<Node> getDisableNodeList(Long roleId) throws Exception {
	
		List<Node> nodeList = new ArrayList<Node>();
		
		//获取所有的菜单集合
		List<Menu> menuList = menuDao.findAll(new Sort(Direction.ASC,"sortOrder"));
		//查询该角色对应的权限
		List<Menu> roleMenuList = menuDao.findMenuListByRole(roleId);
		
		//将menu转换为node
		if (menuList != null && menuList.size() > 0) {
			for (Menu menu : menuList) {
				Node node = new Node();
				node.setId(menu.getMenuId());
				node.setName(menu.getText());
				if (menu.getParent() != null) {
					node.setpId(menu.getParent().getMenuId());
					node.setOpen(false);
				}else {
					node.setpId(0L);
					node.setOpen(true);
				}
				if (roleMenuList.contains(menu)) {
					node.setChkDisabled(false);
					
				}else {
					node.setChkDisabled(true);
				}
				//判断该角色有的菜单才可以被禁用
				
				nodeList.add(node);
			}
			
		}	
		return nodeList;
	
	}
}








