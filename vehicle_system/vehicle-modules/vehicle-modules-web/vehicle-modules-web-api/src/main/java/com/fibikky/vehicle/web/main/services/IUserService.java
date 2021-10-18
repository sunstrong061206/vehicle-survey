package com.fibikky.vehicle.web.main.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fibikky.vehicle.web.main.entities.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sWwag
 * @since 2021-08-30
 */
public interface IUserService extends IService<User> {
    /**
     * 按用户名获取用户
     *
     * @param userName 用户名
     * @return 用户
     */
    User getUserByUserName(String userName);
}
