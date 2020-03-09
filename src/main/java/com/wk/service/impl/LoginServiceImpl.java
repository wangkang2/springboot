package com.wk.service.impl;

import com.wk.entity.User;
import com.wk.mapper.UserMapper;
import com.wk.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String passwd) {
        return userMapper.login(username,passwd);
    }
}
