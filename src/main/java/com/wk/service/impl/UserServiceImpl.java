package com.wk.service.impl;

import com.wk.entity.User;
import com.wk.mapper.UserMapper;
import com.wk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(Integer id) {
        return userMapper.findUserById(id);
    }

    public List<User> findAllUsers(){
        return userMapper.findAllUsers();
    }

    @Override
    public Integer deleteUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public Integer insertUser(User user) {
        userMapper.insertUser(user);
        return user.getId();
    }

    @Override
    public Integer updateUser(User user) {
        user.setAge(user.getAge()+1);
        Integer i = userMapper.updateUser(user);
 //       int a = 9/0;
        return i;
    }

    @Override
    public List<User> findUsers(Map map) {
        return userMapper.findUsers(map);
    }

}
