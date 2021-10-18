package com.fibikky.vehicle.web.pivot.apis.users;

import com.fibikky.vehicle.common.log.LogUtil;
import com.fibikky.vehicle.common.web.response.Message;
import com.fibikky.vehicle.common.web.response.Response;
import com.fibikky.vehicle.common.web.response.ResponseBase;
import com.fibikky.vehicle.common.web.response.ReturnCode;
import com.fibikky.vehicle.web.main.entities.Usertoken;
import com.fibikky.vehicle.web.main.services.IUserService;
import com.fibikky.vehicle.web.main.services.IUserTokenService;
import com.fibikky.vehicle.web.pivot.apis.UserTokenProvider;
import com.fibikky.vehicle.web.pivot.apis.ValidationResult;
import com.fibikky.vehicle.web.pivot.models.UserPassword;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登入登出控制
 *
 * @author 16861
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    final IUserService userService;
    final IUserTokenService tokenService;

    public UserController(IUserService userService, IUserTokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/login")
    public Response<Usertoken> login(@RequestBody UserPassword up) {
        LogUtil.info(this,up.toString());
        return new Response<Usertoken>()
                .invalidWhen(up.userName == null || up.password == null)
                .onNotNull(userService.getUserByUserName(up.userName),
                        (r,user)-> r.when(user.getUserPassword().equals(up.getEncodedPassword()),
                                new Usertoken(user.getId(),UserTokenProvider.create()),
                                (r1,token)->{
                                    tokenService.insertOrUpdateToken(user.getId(), token.getToken());
                                    return r1.set(new Response<>(ReturnCode.OK,Message.OK)).data(token);
                                },
                                r1-> r1.set(ReturnCode.PASSWORD_INCORRECT, Message.PASSWORD_INCORRECT)),
                        r-> r.set(ReturnCode.USER_NOT_EXIST, Message.USER_NOT_EXIST));
    }

    @PostMapping(value = "/register")
    public Response<Usertoken> register(@RequestBody UserPassword up) {
        if (up == null || up.userName == null || up.password == null) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        }
        LogUtil.info(this,up.toUser(true).toString());
        if (userService.getUserByUserName(up.userName) == null) {
            userService.getBaseMapper().insert(up.toUser(true));

            return new Response<>(ReturnCode.OK, Message.OK);
        } else {
            return new Response<>(ReturnCode.USER_ALREADY_EXIST, Message.USER_ALREADY_EXIST);
        }
    }

    @PostMapping(value = "/logout")
    public ResponseBase logout(@RequestBody Usertoken token) {
        if (token == null || token.getUserId() == null || token.getToken() == null) {
            return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        }
        ValidationResult result = UserTokenProvider.validate(token, tokenService);
        switch (result) {
            case Valid -> {
                tokenService.deleteToken(token.getUserId());
                return new ResponseBase(ReturnCode.OK, Message.OK);
            }
            case Expired -> {
                return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.EXPIRED_USER_TOKEN);
            }
            case Invalid -> {
                return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
            }
            default -> throw new IllegalStateException("Unexpected value: " + result);
        }
    }
}
