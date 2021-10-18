package com.fibikky.vehicle.web.main.services.impls;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fibikky.vehicle.web.main.entities.Vehicle;
import com.fibikky.vehicle.web.main.mappers.VehicleMapper;
import com.fibikky.vehicle.web.main.services.IVehicleService;
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
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements IVehicleService {

}
