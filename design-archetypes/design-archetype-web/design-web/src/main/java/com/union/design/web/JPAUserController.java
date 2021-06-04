package com.union.design.web;

import com.alibaba.fastjson.JSONObject;
import com.union.design.dao.jpa.entity.User;
import com.union.design.dao.jpa.repository.UserRepository;
import com.union.design.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/userjpa")
public class JPAUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/findAll")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PostMapping("/findAll")
    public List<User> findAllById(@RequestBody List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    @RequestMapping("/username")
    public String getUserName(@RequestParam("id") Long id) {
        return userRepository.getOne(id).getName();
    }

    @RequestMapping("/add")
    @ResponseBody
    public User addUser(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return userRepository.save(user);
    }

    @RequestMapping("/update")
    @ResponseBody
    public User updateUser(@RequestParam("id")Long id,@RequestParam("name") String name, @RequestParam("age") Integer age) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setAge(age);
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteById(@PathVariable("id")Long id) {
        userRepository.logicDeleteById(id);
        return "SUCCESS";
    }

    @PostMapping("/deleteAll")
    @ResponseBody
    public String deleteAllById(@RequestBody List<Long> ids) {
        userRepository.logicDeleteAllById(ids);
        return "SUCCESS";
    }

}