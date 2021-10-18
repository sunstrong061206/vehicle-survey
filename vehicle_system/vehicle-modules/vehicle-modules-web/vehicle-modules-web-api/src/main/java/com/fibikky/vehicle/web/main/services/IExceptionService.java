package com.fibikky.vehicle.web.main.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fibikky.vehicle.web.main.dto.ExceptionList;
import com.fibikky.vehicle.web.main.dto.WeekException;
import com.fibikky.vehicle.web.main.entities.Exception;
import com.fibikky.vehicle.web.pivot.models.ExceptionQueryParam;
import com.fibikky.vehicle.web.pivot.models.WeekQueryParam;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sWwag
 * @since 2021-09-09
 */
public interface IExceptionService extends IService<Exception> {

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    IPage<ExceptionList> listByPage(IPage<Exception> page, ExceptionQueryParam query);

    /**
     * 导出表格
     *
     * @param query
     * @return
     */
    List<Exception> export(ExceptionQueryParam query);

    void updateExceptionStatus(int exceptionId,int status);

    int deleteByExceptionId(int exceptionId);

    int getExceptionCountBetween(Date d0, Date d24);

    List<WeekException> getExceptionCountIntervalScopeDay(WeekQueryParam weekQueryParam);

    int getDetectionLineExceptionCount(int detectionLine, Date d0, Date d24);

    int getDayNotDealtExceptionCount(Date d0,Date d24);
}
