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
public class Monitor extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "monitor_id", type = IdType.AUTO)
    private Integer monitorId;

    private String monitorName;

    private Double monitorX;

    private Double monitorY;

}
