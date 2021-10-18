package com.fibikky.vehicle.web.main.services.impls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fibikky.vehicle.web.main.entities.User;
import com.fibikky.vehicle.web.main.mappers.UserMapper;
import com.fibikky.vehicle.web.main.services.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public User getUserByUserName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_username",userName);
        return getBaseMapper().selectOne(queryWrapper);
    }
}
