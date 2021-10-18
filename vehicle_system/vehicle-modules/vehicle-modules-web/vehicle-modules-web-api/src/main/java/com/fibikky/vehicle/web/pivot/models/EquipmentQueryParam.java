package com.fibikky.vehicle.web.pivot.models;

import io.swagger.annotations.ApiModelProperty;

public class EquipmentQueryParam extends DefaultQueryParam {

    @ApiModelProperty(value = "检测站ID")
    public Integer detectionStationId;

    @ApiModelProperty(value = "检测线ID")
    public Integer detectionLineId;

    @ApiModelProperty(value = "监控摄像头ID")
    public Integer monitorId;

    @ApiModelProperty(value = "检测站名称")
    public String monitorName;

    @ApiModelProperty(value = "检测站名称")
    public String detectionLineName;

    @ApiModelProperty(value = "检测站名称")
    public String detectionStationName;

}
