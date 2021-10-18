package com.fibikky.vehicle.web.main.entities;

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
 * @since 2021-08-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Processing extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "processing_id", type = IdType.AUTO)
    private Integer processingId;

    
    private Integer exceptionId;

    /**
     * 处理车辆车牌号
     */
    private String abnormalVehicleNumber;

    /**
     * 处理状态
     */
    private Integer processingStatus;

    /**
     * 处理人员
     */
    private String processingStaff;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date processingTime;

    /**
     * 处理反馈信息
     */
    private String processingFeedbackInfo;

}
