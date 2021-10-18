package com.fibikky.vehicle.web.main.services.impls;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fibikky.vehicle.web.main.entities.Monitor;
import com.fibikky.vehicle.web.main.mappers.MonitorMapper;
import com.fibikky.vehicle.web.main.services.IMonitorService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sWwag
 * @since 2021-09-09
 */
@Service
public class MonitorServiceImpl extends ServiceImpl<MonitorMapper, Monitor> implements IMonitorService {

}
