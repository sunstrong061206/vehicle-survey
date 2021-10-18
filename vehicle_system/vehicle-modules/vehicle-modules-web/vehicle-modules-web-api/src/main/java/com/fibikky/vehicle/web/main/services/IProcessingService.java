package com.fibikky.vehicle.web.main.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fibikky.vehicle.web.main.entities.Processing;
import com.fibikky.vehicle.web.pivot.models.ProcessQueryParam;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sWwag
 * @since 2021-08-30
 */
public interface IProcessingService extends IService<Processing> {

    IPage<Processing> listByPage(IPage<Processing> page, ProcessQueryParam query);

    int deleteByVehicleNumber(String abnormalVehicleNumber);
}
