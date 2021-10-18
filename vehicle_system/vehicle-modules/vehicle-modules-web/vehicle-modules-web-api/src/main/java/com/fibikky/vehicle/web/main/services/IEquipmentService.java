package com.fibikky.vehicle.web.main.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fibikky.vehicle.web.main.dto.EquipmentList;
import com.fibikky.vehicle.web.main.entities.Equipment;
import com.fibikky.vehicle.web.pivot.models.EquipmentQueryParam;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sWwag
 * @since 2021-09-09
 */
public interface IEquipmentService extends IService<Equipment> {

    IPage<EquipmentList> listByPage(IPage<Equipment> page, EquipmentQueryParam query);

}
