package com.union.design.dao.mybatis.mapper;

import com.union.design.dao.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Transactional(transactionManager = "mybatisPlusTxManager")
@Mapper
public interface UserMapper {

    int insertUser(User user);

    int logicDeleteUserById(Long id);

    User getUserById(Long id);

}