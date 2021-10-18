package com.fibikky.vehicle.web.pivot.models;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author 16861
 */
public class ProcessQueryParam extends DefaultQueryParam {

    @ApiModelProperty(value = "处理车辆车牌号")
    public String abnormalVehicleNumber;

    @ApiModelProperty(value = "处理id")
    public Integer exceptionId;

    @ApiModelProperty(value = "处理状态")
    public Boolean processingStatus;

    @ApiModelProperty(value = "处理人")
    public String processingStaff;

    @ApiModelProperty(value = "处理开始时间")
    public Date processingStartTime;

    @ApiModelProperty(value = "处理结束时间")
    public Date processingEndTime;

    @ApiModelProperty(value = "处理反馈信息")
    public String processingFeedbackInfo;
}
