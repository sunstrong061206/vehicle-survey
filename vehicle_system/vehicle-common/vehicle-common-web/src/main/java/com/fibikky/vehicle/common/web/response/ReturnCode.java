package com.fibikky.vehicle.common.web.response;

/**
 * @author 16861
 */
public class ReturnCode {
    public static final int OK = 0;
    /**
     * 无效的请求
     */
    public static final int INVALID_REQUEST = -1;

    public static final int INTERNAL_SERVER_ERROR = -500;

    public static final int USER_NOT_EXIST = -1000;
    public static final int PASSWORD_INCORRECT = -1001;
    public static final int USER_ALREADY_EXIST = -1002;

    public static final int EXCEPTION_ADD_OR_UPDATE_ERROR = -2000;
    public static final int EXCEPTION_DELETE_ERROR = -2001;

    public static final int PROCESSING_ADD_OR_UPDATE_ERROR = -3000;
    public static final int PROCESSING_DELETE_ERROR = -3001;

    public static final int DETECTION_STATION_ADD_OR_UPDATE_ERROR = -4000;
    public static final int DETECTION_STATION_DELETE_ERROR = -4001;

    public static final int DETECTION_LINE_ADD_OR_UPDATE_ERROR = -5000;
    public static final int DETECTION_LINE_DELETE_ERROR = -5001;

    public static final int MONITOR_ADD_OR_UPDATE_ERROR = -6000;
    public static final int MONITOR_DELETE_ERROR = -6001;
}
