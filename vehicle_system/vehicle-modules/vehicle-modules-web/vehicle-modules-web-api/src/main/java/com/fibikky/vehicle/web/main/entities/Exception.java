package com.fibikky.vehicle.web.main.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fibikky.vehicle.common.web.excel.annotations.Excel;
import lombok.*;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exception extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "exception_id", type = IdType.AUTO)
    @Excel(name = "异常编号", type = Excel.Type.ALL)
    private Integer exceptionId;

    /**
     * 异常车辆车牌号
     */
    @Excel(name = "异常车辆车牌号", type = Excel.Type.ALL)
    private String abnormalVehicleNumber;

    /**
     * 异常时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "异常发生时间", type = Excel.Type.ALL)
    private Date exceptionTime;

    /**
     * 异常类型
     */
    @Excel(name = "异常类型", type = Excel.Type.ALL)
    private String exceptionType;


    /**
     * 异常图片地址
     */
    @Excel(name = "异常图片存放地址", type = Excel.Type.ALL)
    private String exceptionImage;

    /**
     * 异常检测线
     */
    @Excel(name = "发生异常情况的检测线编号", type = Excel.Type.ALL)
    private Integer exceptionDetectionLine;

    /**
     * 异常检测站
     */
    @Excel(name = "发生异常情况的检测站编号", type = Excel.Type.ALL)
    private Integer exceptionDetectionStation;

    /**
     * 异常检测摄像头
     */
    @Excel(name = "发生异常情况的检测摄像头编号", type = Excel.Type.ALL)
    private Integer exceptionDetectionMonitor;

    /**
     * 异常是否被处理
     */
    @Excel(name = "异常处理情况", type = Excel.Type.ALL)
    private Integer exceptionStatus;

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
