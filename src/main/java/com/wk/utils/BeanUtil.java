package com.wk.utils;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {

    public static Map<String,Object> convertToMap(Object object) throws IllegalAccessException {
        Map<String,Object> map = new HashMap<>();
        Class clazz = object.getClass();
        for (Field field:clazz.getDeclaredFields()){
            field.setAccessible(true);
            String key = field.getName();
            Object value = field.get(object);
            if(value!=null){
                map.put(key,value);
            }
        }
        return map;
    }
}
