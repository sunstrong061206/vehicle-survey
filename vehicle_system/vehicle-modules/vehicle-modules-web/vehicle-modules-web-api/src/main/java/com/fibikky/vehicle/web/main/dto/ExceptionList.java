package com.fibikky.vehicle.web.main.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionList {

    private static final long serialVersionUID = 1L;

    @TableId(value = "exception_id", type = IdType.AUTO)
    private Integer exceptionId;

    private String abnormalVehicleNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exceptionTime;

    private String exceptionType;

    private Integer exceptionDetectionMonitor;

    private Integer exceptionDetectionLine;

    private Integer exceptionDetectionStation;

    private Integer exceptionStatus;

}
