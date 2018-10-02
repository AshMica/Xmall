package com.augustars.xmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.augustars.xmall.entity.Menu;

public interface MenuDao extends JpaRepository<Menu, Long> {

	@Query("from Menu m join fetch m.roleList r where r.roleId=:roleId and m.parent is null")
	public List<Menu> findFirstMenuList(@Param("roleId")Long roleId)throws Exception;

	@Query("from Menu m join fetch m.roleList r where r.roleId=:roleId and m.parent.menuId=:parentId")
	public List<Menu> findSecondMenuList(@Param("roleId")Long roleId, @Param("parentId")Long parentId)throws Exception;

	
	@Query("from Menu m join fetch m.roleList r where r.roleId=:roleId")
	public List<Menu> findMenuListByRole(@Param("roleId")Long roleId) throws Exception;
	
	@Query("from Menu m where m.menuId=:menuId")
	public Menu findMenuById(@Param("menuId")Long menuId) throws Exception;

}
