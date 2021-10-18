package com.fibikky.vehicle.web.pivot.apis.authentications;

import com.fibikky.vehicle.common.web.response.Message;
import com.fibikky.vehicle.common.web.response.Response;
import com.fibikky.vehicle.common.web.response.ReturnCode;
import com.fibikky.vehicle.web.main.entities.Usertoken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 16861
 */
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    @PostMapping(value = "/getUserToken")
    public Response<Usertoken> getUserToken() {
        return new Response<Usertoken>(ReturnCode.OK, Message.OK);
    }
}
