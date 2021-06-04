package com.union.design.dao.jpa.repository;

import com.union.design.dao.jpa.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true, transactionManager = "")
public interface UserRepository extends BaseRepository<User, Long> {


}