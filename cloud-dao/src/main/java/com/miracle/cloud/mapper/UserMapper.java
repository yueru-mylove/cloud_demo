package com.miracle.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miracle.cloud.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月04日 01:04:00
 */
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {

    /**
     * 用户名查找用户
     *
     * @param username 用户名
     * @return  List<User>
     */
    @Select("select id, userName, password, email, phone, roles from user where userName = #{username}")
    List<User> selectByUsername(@Param("username") String username);
}
