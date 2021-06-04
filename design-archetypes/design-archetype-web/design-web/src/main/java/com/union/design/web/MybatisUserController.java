package com.union.design.web;

import com.union.design.dao.jpa.entity.User;
import com.union.design.dao.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usermybatis")
public class MybatisUserController {

    @Autowired
    private UserMapper userMapper;

    @DeleteMapping("/{id}")
    @ResponseBody
    public int updateUser(@PathVariable("id")Long id) {
        return userMapper.logicDeleteUserById(id);
    }


}