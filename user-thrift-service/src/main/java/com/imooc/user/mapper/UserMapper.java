package com.imooc.user.mapper;

import com.imooc.thrift.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface UserMapper {

    @Select("select id, username, password, realname, mobile, email from users where id=#{id}")
    UserInfo getUserById(@Param("id")int id);

    @Select("select id, username, password, realname, mobile, email from users where username=#{username}")
    UserInfo getUserMyName(@Param("username") String username);

    @Insert("insert into users (username, password. realname, mobile, email) values (#{u.username}, " +
            "#{u.password}, #{u.realname}, #{u.mobile}, #{u.email})")
    void registerUser(@Param("u") UserInfo userInfo);
}
