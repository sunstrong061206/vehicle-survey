package com.fibikky.vehicle.web.main.services.impls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fibikky.vehicle.web.main.entities.Exception;
import com.fibikky.vehicle.web.main.entities.Processing;
import com.fibikky.vehicle.web.main.mappers.ProcessingMapper;
import com.fibikky.vehicle.web.main.services.IProcessingService;
import com.fibikky.vehicle.web.pivot.models.ProcessQueryParam;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sWwag
 * @since 2021-08-30
 */
@Service
public class ProcessingServiceImpl extends ServiceImpl<ProcessingMapper, Processing> implements IProcessingService {


    @Override
    public IPage<Processing> listByPage(IPage<Processing> page, ProcessQueryParam query) {
        return getBaseMapper().listByPage(page, query);
    }

    @Override
    public int deleteByVehicleNumber(String abnormalVehicleNumber) {
        QueryWrapper<Processing> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("abnormal_vehicle_number",abnormalVehicleNumber);
        return getBaseMapper().delete(queryWrapper);
    }
}
