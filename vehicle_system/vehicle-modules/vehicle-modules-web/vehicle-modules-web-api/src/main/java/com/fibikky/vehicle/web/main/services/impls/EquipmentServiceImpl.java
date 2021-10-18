package com.fibikky.vehicle.web.main.services.impls;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fibikky.vehicle.web.main.dto.EquipmentList;
import com.fibikky.vehicle.web.main.entities.Equipment;
import com.fibikky.vehicle.web.main.mappers.EquipmentMapper;
import com.fibikky.vehicle.web.main.services.IEquipmentService;
import com.fibikky.vehicle.web.pivot.models.EquipmentQueryParam;
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
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements IEquipmentService {

    @Override
    public IPage<EquipmentList> listByPage(IPage<Equipment> page, EquipmentQueryParam query) {
        return getBaseMapper().listByPage(page, query);
    }
}
