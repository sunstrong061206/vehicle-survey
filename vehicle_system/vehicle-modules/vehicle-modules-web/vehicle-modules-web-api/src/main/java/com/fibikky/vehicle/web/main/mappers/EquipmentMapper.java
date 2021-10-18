package com.fibikky.vehicle.web.main.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fibikky.vehicle.web.main.dto.EquipmentList;
import com.fibikky.vehicle.web.main.entities.Equipment;
import com.fibikky.vehicle.web.pivot.models.EquipmentQueryParam;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sWwag
 * @since 2021-09-09
 */
public interface EquipmentMapper extends BaseMapper<Equipment> {

    IPage<EquipmentList> listByPage(@Param("page") IPage<Equipment> page, @Param("query") EquipmentQueryParam query);
}
