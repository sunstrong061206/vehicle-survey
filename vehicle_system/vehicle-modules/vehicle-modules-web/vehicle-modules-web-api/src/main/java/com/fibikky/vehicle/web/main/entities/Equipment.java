package com.fibikky.vehicle.web.main.entities;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-09-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Equipment extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer detectionStationId;

    private Integer detectionLineId;

    private Integer monitorId;


}
