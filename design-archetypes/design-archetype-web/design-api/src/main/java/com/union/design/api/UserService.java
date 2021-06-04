package com.union.design.api;

import com.union.design.dto.UserDTO;

public interface UserService {

    String getUsername(Long id);

    UserDTO addUser(UserDTO user);

}