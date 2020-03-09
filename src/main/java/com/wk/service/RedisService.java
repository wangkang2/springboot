package com.wk.service;

import java.util.List;

public interface RedisService {

    boolean set(String key,Object value);

    boolean set(String key,Object value,Long expireTime);

    boolean exists(String key);

    boolean remove(String key);

    boolean remove(List<String> keys);

    Object get(String key);


}
