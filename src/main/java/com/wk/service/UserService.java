package com.wk.service;

import com.wk.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findUserById(Integer id);

    List<User> findAllUsers();

    Integer deleteUserById(Integer id);

    Integer insertUser(User user);

    Integer updateUser(User user);

    List<User> findUsers(Map map);
}
