package com.fibikky.vehicle.web.main.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fibikky.vehicle.web.main.dto.ExceptionList;
import com.fibikky.vehicle.web.main.dto.WeekException;
import com.fibikky.vehicle.web.main.entities.Exception;
import com.fibikky.vehicle.web.pivot.models.ExceptionQueryParam;
import com.fibikky.vehicle.web.pivot.models.WeekQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sWwag
 * @since 2021-09-09
 */
public interface ExceptionMapper extends BaseMapper<Exception> {

    IPage<ExceptionList> listByPage(@Param("page") IPage<Exception> page, @Param("query") ExceptionQueryParam query);

    List<Exception> export(@Param("query") ExceptionQueryParam query);

    List<WeekException> getExceptionCountIntervalScopeDay(@Param("query")WeekQueryParam query);

}
