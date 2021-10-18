package com.fibikky.vehicle.web.main.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fibikky.vehicle.web.main.entities.Usertoken;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sWwag
 * @since 2021-08-30
 */
public interface IUserTokenService extends IService<Usertoken> {
    /**
     * 添加或更新用户令牌
     *
     * @param token
     * @return
     */
    void insertOrUpdateToken(Integer userId, String token);

    /**
     * 删除用户的令牌
     *
     * @param userId
     */
    void deleteToken(Integer userId);

    /**
     * 获取用户令牌
     *
     * @param userId
     */
    Usertoken getUserTokenById(Integer userId);
}
