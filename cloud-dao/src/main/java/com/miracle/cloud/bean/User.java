package com.miracle.cloud.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月04日 01:04:00
 */
@Data
@TableName("user")
public class User {

    private Integer id;

    @TableField("userName")
    private String userName;

    private String password;

    private String phone;

    private String email;

    private String roles;
}
