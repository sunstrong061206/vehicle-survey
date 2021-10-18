package com.fibikky.vehicle.web.main.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author sWwag
 * @since 2021-08-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
public class User extends Model {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String userUsername;
    private String userPassword;
    @TableField("user_registerTime")
    private Date userRegistertime;
    @TableField("user_lastLoginTime")
    private Date userLastlogintime;
    private String userAuthority;

    public User(String userUsername, String password) {
        this.userUsername = userUsername;
        this.userPassword = password;
    }
}
