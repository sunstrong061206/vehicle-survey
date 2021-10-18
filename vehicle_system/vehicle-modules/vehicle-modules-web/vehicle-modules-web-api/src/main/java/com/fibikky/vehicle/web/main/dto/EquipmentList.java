package com.fibikky.vehicle.web.main.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentList {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer detectionStationId;

    private Integer detectionLineId;

    private Integer monitorId;

    private String detectionLineName;

    private Double detectionLineX;

    private Double detectionLineY;

    private String detectionStationName;

    private Double detectionStationX;

    private Double detectionStationY;

    private String monitorName;

    private Double monitorX;

    private Double monitorY;
}
