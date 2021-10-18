package com.fibikky.vehicle.web.pivot.apis;

import com.fibikky.vehicle.common.Crypto;
import com.fibikky.vehicle.common.UnixTimestamp;
import com.fibikky.vehicle.common.web.response.Message;
import com.fibikky.vehicle.common.web.response.Response;
import com.fibikky.vehicle.common.web.response.ReturnCode;
import com.fibikky.vehicle.web.main.entities.Usertoken;
import com.fibikky.vehicle.web.main.services.IUserTokenService;

/**
 * @author 16861
 */
public class UserTokenProvider {
    private static final String RANDOM_STRING_TEMPLATE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SALT = "IntelligentVehicleDetection";

    public static String create() {
        //user login time
        String dynamicTime = UnixTimestamp.getCurrent();
        String check = Crypto.encodeMd5("salt=%s&t=%s".formatted(SALT, dynamicTime));
        return "%s;%s".formatted(dynamicTime, check);
    }

    /**
     * 仅验证token的正确性
     *
     * @param userToken
     * @return
     */
    public static boolean validate(String userToken) {
        String[] tokens = userToken.split(";");
        String check = Crypto.encodeMd5("salt=%s&t=%s".formatted(SALT, tokens[0]));
        return check.equals(tokens[1]);
    }

    public static long getTimestampOf(String userToken) {
        return Long.parseLong(userToken.split(";")[0]);
    }

    public static ValidationResult validate(Usertoken userToken, IUserTokenService tokenService) {
        return validate(userToken.getUserId(), userToken.getToken(), tokenService);
    }

    public static ValidationResult validate(Integer userId, String userToken, IUserTokenService tokenService) {
        return getTimestampOf(userToken) < getTimestampOf(tokenService.getUserTokenById(userId).getToken())
                ? ValidationResult.Expired
                : validate(userToken)
                ? ValidationResult.Valid
                : ValidationResult.Invalid;
    }

    public <T> Response<T> verifyAccess(String token){
        if(validate(token)){
            return new Response<>(ReturnCode.OK, Message.OK);
        }else{
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        }
    }
}

