package com.fibikky.vehicle.web.pivot.models;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


/**
 * @author 16861
 */
public class ExceptionQueryParam extends DefaultQueryParam {

    @ApiModelProperty(value = "异常处理编号")
    private Integer exceptionId;

    @ApiModelProperty(value = "异常是否处理")
    public Integer isHandled;

    @ApiModelProperty(value = "异常车辆车牌号")
    public String abnormalVehicleNumber;

    @ApiModelProperty(value = "异常类型")
    public String exceptionType;

    @ApiModelProperty(value = "异常搜索起始时间")
    public Date exceptionStartTime;

    @ApiModelProperty(value = "异常搜索结束时间")
    public Date exceptionEndTime;

    @ApiModelProperty(value = "异常出现摄像头名")
    public String monitorName;

    @ApiModelProperty(value = "异常出现检测站名")
    public String detectionStationName;

    @ApiModelProperty(value = "异常出现摄检测线名")
    public String detectionLineName;

}
