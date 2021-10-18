package com.fibikky.vehicle.common.web.response;

/**
 * @author 16861
 */
public class ResponseBase {
    public int returnCode = 0;
    public String message = null;

    public ResponseBase() {

    }

    public ResponseBase(int returnCode, String message) {
        this.returnCode = returnCode;
        this.message = message;
    }
}
