package com.fibikky.detect.server.inspect.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author sWwag
 * @since 2021-09-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Exception extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "exception_id", type = IdType.AUTO)
    private Integer exceptionId;

    /**
     * 异常车辆车牌号
     */
    private String abnormalVehicleNumber;

    /**
     * 异常时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exceptionTime;

    /**
     * 异常类型
     */
    private String exceptionType;


    /**
     * 异常图片地址
     */
    private String exceptionImage;

    /**
     * 异常检测线
     */
    private Integer exceptionDetectionLine;

    /**
     * 异常检测站
     */
    private Integer exceptionDetectionStation;

    /**
     * 异常检测摄像头
     */
    private Integer exceptionDetectionMonitor;

    /**
     * 异常是否被处理
     */
    private Integer exceptionStatus;

    public Exception(){}
    public Exception(String abnormalVehicleNumber,
                     Date exceptionTime,
                     String exceptionType,
                     String exceptionImage,
                     Integer exceptionDetectionLine,
                     Integer exceptionDetectionStation,
                     Integer exceptionDetectionMonitor,
                     Integer exceptionStatus){
        this.abnormalVehicleNumber=abnormalVehicleNumber;
        this.exceptionTime=exceptionTime;
        this.exceptionType=exceptionType;
        this.exceptionImage=exceptionImage;
        this.exceptionDetectionLine=exceptionDetectionLine;
        this.exceptionDetectionStation=exceptionDetectionStation;
        this.exceptionDetectionMonitor=exceptionDetectionMonitor;
        this.exceptionStatus=exceptionStatus;
    }

    public static final Integer HANDLED = 1;
    public static final Integer UNHANDLED = 0;
}
