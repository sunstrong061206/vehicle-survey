package com.fibikky.detect.server.inspect.main.service.Iml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fibikky.detect.server.inspect.main.entity.Vehicle;
import com.fibikky.detect.server.inspect.main.mapper.VehicleMapper;
import com.fibikky.detect.server.inspect.main.service.IVehicleService;
import org.springframework.stereotype.Repository;
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
@Repository
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements IVehicleService {

}
