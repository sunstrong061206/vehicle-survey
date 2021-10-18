package com.fibikky.vehicle.web.main.entities;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class Usertoken extends Model {

    private static final long serialVersionUID = 1L;

    public Usertoken() {
    }

    public Usertoken(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    private Integer userId;

    private String token;
}
