package com.wk.mapper;

import com.wk.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    @Select(value = "select id,name,age from t_user where id = #{id}")
    User findUserById(Integer id);

    @Select(value = "select * from t_user")
    List<User> findAllUsers();

    @Delete(value = "delete from t_user where id = #{id}")
    Integer deleteUserById(Integer id);

    @Insert(value = "insert into t_user(name,age,birthDay,comeTime,regTime) values(#{name},#{age},#{birthDay},#{comeTime},#{regTime})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Integer insertUser(User user);

    @Update(value = "update t_user set name=#{name},age=#{age} where id=#{id}")
    Integer updateUser(User user);

    @SelectProvider(type = UserProvider.class,method = "findUsers")
    List<User> findUsers(Map map);

    @Select(value = "select * from t_user where username = #{username} and passwd = #{passwd}")
    User login(String username, String passwd);

    class UserProvider{
        public String findUsers(Map map){

            String name = (String) map.get("name");

            String age = (String) map.get("age");

            return new SQL(){{
                SELECT("id,name");
                SELECT("age");
                FROM("t_user");
                if(!StringUtils.isEmpty(name)){
                    WHERE("name = #{name}");
                }
                if(!StringUtils.isEmpty(age)){
                    WHERE("age = #{age}");
                }

            }}.toString();
        }
    }

}
