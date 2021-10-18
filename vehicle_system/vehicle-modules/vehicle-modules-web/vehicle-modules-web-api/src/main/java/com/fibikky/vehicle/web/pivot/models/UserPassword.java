package com.fibikky.vehicle.web.pivot.models;

import com.fibikky.vehicle.common.Crypto;
import com.fibikky.vehicle.web.main.entities.User;

import java.util.Date;

/**
 * @author 16861
 */
public class UserPassword {
    public String userName;
    public String password;

    public String getEncodedPassword() {
        return Crypto.encodeSha256(password);
    }

    public User toUser(boolean isRegistering) {
        var user = new User(userName, password);
        var date = new Date();
        if (isRegistering) {
            user.setUserRegistertime(date);
            user.setUserPassword(getEncodedPassword());
        }
        user.setUserLastlogintime(date);
        return user;
    }

    @Override
    public String toString() {
        return "UserPassword{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
