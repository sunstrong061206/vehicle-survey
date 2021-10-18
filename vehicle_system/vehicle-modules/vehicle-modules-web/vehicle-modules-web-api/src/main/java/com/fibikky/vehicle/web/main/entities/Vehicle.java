package com.fibikky.vehicle.web.main.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class Vehicle extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "vehicle_id", type = IdType.AUTO)
    private Integer vehicleId;

    /**
     * 车牌号
     */
    private String vehicleNumber;

    /**
     * 是否异常
     */
    @TableField("vehicle_isNormal")
    private Integer vehicleIsnormal;

    /**
     * 检测线
     */
    private Integer vehicleDetectionLine;

    /**
     * 检测站
     */
    private Integer vehicleDetectionStation;


}
