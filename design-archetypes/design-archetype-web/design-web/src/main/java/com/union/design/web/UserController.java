package com.union.design.web;

import com.union.design.api.UserService;
import com.union.design.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/username")
    public String getUserName(@RequestParam("id") Long id) {
        return userService.getUsername(id);
    }

    @RequestMapping("/add")
    @ResponseBody
    public UserDTO addUser(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        UserDTO user = new UserDTO();
        user.setName(name);
        user.setAge(age);
        return userService.addUser(user);
    }

}