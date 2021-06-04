package com.union.design.service;

import com.union.design.api.UserService;
import com.union.design.dao.mybatis.entity.User;
import com.union.design.dao.mybatis.mapper.UserMapper;
import com.union.design.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private static final BeanCopier copier = BeanCopier.create(UserDTO.class, User.class, false);

    @Override
    public String getUsername(Long id) {
        User user = userMapper.getUserById(id);
        return Objects.nonNull(user) ? user.getName() : null;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        User user = new User();
        copier.copy(userDTO, user, null);

        userMapper.insertUser(user);
        userDTO.setId(user.getId());
        return userDTO;
    }

}