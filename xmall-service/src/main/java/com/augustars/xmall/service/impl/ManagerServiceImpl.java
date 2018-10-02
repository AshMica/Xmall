package com.augustars.xmall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.augustars.xmall.dao.ManagerDao;
import com.augustars.xmall.entity.Manager;
import com.augustars.xmall.service.ManagerService;

@Service("managerService")
@Transactional
public class ManagerServiceImpl implements ManagerService{
	@Resource(name="managerDao")
	private ManagerDao managerDao;

	public Manager getManagerByLoginName(String loginName) throws Exception {
		Manager manager = managerDao.findManagerByLoginName(loginName);
//		System.out.println(manager.toString());
		return manager;
	}

	// 										               当前第几页            每页显示数量                    查询条件
	public Map<String, Object> getManagerListBypage(Integer pageNum,Integer pageSize,String username) throws Exception {
		//结果集
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//使用managerId分页
		Sort sort = new Sort(Direction.ASC, "managerId");
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		Page<Manager> page = null;
		//没有查询条件的查询
		if(username == null) {	
//			page从0开始
			page = managerDao.findAll(pageable);
		}else {
			//有查询条件
			page = managerDao.findAll(new Specification<Manager>() {
				//CriteriaBuilder 类似于查询器
				public Predicate toPredicate(Root<Manager> root, CriteriaQuery<?> query,
						CriteriaBuilder cb) {
					List<Predicate> queryList = new ArrayList<Predicate>();
					//开始模糊查询   第一个参数:用那个值进行模糊查询   第二个   
					queryList.add(cb.like(root.get("username"), "%" + username + "%"));
					//创建一个Predicate类型的数组
					Predicate[] predicates = new Predicate[queryList.size()];
					return query.where(queryList.toArray(predicates)).getRestriction();
				}
			} , pageable);
		}
		//封装结果集
		resultMap.put("list", page.getContent());
		resultMap.put("pageNum", pageNum);
		resultMap.put("pageSize", pageSize);
		resultMap.put("totleCount", page.getTotalElements());
		resultMap.put("totlePage", page.getTotalPages());
		
		return resultMap;
	}

}








