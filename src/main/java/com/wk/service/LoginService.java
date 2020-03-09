package com.wk.service;

import com.wk.entity.User;

public interface LoginService {

    User login(String username, String passwd);
}
