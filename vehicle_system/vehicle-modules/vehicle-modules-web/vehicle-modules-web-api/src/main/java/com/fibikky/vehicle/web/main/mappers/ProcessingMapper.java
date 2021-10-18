package com.fibikky.vehicle.web.main.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fibikky.vehicle.web.main.entities.Processing;
import com.fibikky.vehicle.web.pivot.models.ProcessQueryParam;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sWwag
 * @since 2021-08-30
 */
public interface ProcessingMapper extends BaseMapper<Processing> {
    IPage<Processing> listByPage(@Param("page") IPage<Processing> page, @Param("query") ProcessQueryParam query);
}
