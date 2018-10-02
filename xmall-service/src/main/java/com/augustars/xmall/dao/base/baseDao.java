package com.augustars.xmall.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface baseDao<T,ID> extends JpaRepository<T, ID>,JpaSpecificationExecutor<T>{
                                     //  使用分页查询                              增加查询条件

}
