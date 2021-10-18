package com.fibikky.vehicle.web.main.services.impls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fibikky.vehicle.web.main.entities.Usertoken;
import com.fibikky.vehicle.web.main.mappers.UserTokenMapper;
import com.fibikky.vehicle.web.main.services.IUserTokenService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sWwag
 * @since 2021-08-30
 */
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, Usertoken> implements IUserTokenService {

    QueryWrapper<Usertoken> wrapper = new QueryWrapper<>();

    @Override
    public void insertOrUpdateToken(Integer userId, String token) {
        Usertoken userToken = getUserTokenById(userId);
        if (userToken != null) {
            UpdateWrapper<Usertoken> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("user_id",userId);
            userToken.setToken(token);
            this.getBaseMapper().update(userToken,updateWrapper);
        } else {
            this.getBaseMapper().insert(new Usertoken(userId, token));
        }
    }

    @Override
    public void deleteToken(Integer userId) {
        Usertoken userToken = getUserTokenById(userId);
        UpdateWrapper<Usertoken> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("user_id",userId);
        userToken.setToken("");
        this.getBaseMapper().update(userToken,updateWrapper);
    }

    @Override
    public Usertoken getUserTokenById(Integer userId) {
        wrapper.eq("user_id",userId);
        return getBaseMapper().selectOne(wrapper);
    }
}
