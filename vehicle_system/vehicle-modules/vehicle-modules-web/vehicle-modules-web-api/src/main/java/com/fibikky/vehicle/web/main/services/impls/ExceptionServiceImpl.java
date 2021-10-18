package com.fibikky.vehicle.web.main.services.impls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fibikky.vehicle.web.main.dto.ExceptionList;
import com.fibikky.vehicle.web.main.dto.WeekException;
import com.fibikky.vehicle.web.main.entities.Exception;
import com.fibikky.vehicle.web.main.entities.Usertoken;
import com.fibikky.vehicle.web.main.mappers.ExceptionMapper;
import com.fibikky.vehicle.web.main.services.IExceptionService;
import com.fibikky.vehicle.web.pivot.models.ExceptionQueryParam;
import com.fibikky.vehicle.web.pivot.models.WeekQueryParam;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sWwag
 * @since 2021-09-09
 */
@Service
public class ExceptionServiceImpl extends ServiceImpl<ExceptionMapper, Exception> implements IExceptionService {

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    public IPage<ExceptionList> listByPage(IPage<Exception> page, ExceptionQueryParam query) {
        return getBaseMapper().listByPage(page, query);
    }

    /**
     * 检索导出
     *
     * @param query
     * @return
     */
    @Override
    public List<Exception> export(ExceptionQueryParam query) {
        return getBaseMapper().export(query);
    }

    @Override
    public void updateExceptionStatus(int exceptionId, int status) {
        UpdateWrapper<Exception> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("exception_id",exceptionId);
        Exception exception=new Exception();
        exception.setExceptionStatus(status);
        this.getBaseMapper().update(exception,updateWrapper);
    }

    @Override
    public int deleteByExceptionId(int exceptionId) {
        QueryWrapper<Exception> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exception_id",exceptionId);
        return getBaseMapper().delete(queryWrapper);
    }

    @Override
    public int getExceptionCountBetween(Date d0, Date d24) {
        QueryWrapper<Exception> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("exception_time",d0,d24);
        return  getBaseMapper().selectCount(queryWrapper);
    }

    @Override
    public List<WeekException> getExceptionCountIntervalScopeDay(WeekQueryParam weekQueryParam) {
        return getBaseMapper().getExceptionCountIntervalScopeDay(weekQueryParam);
    }

    @Override
    public int getDetectionLineExceptionCount(int detectionLine, Date d0,Date d24) {
        QueryWrapper<Exception> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exception_detection_line",detectionLine).between("exception_time",d0,d24);
        return getBaseMapper().selectCount(queryWrapper);
    }

    @Override
    public int getDayNotDealtExceptionCount(Date d0, Date d24) {
        QueryWrapper<Exception> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exception_status",0).between("exception_time",d0,d24);
        return  getBaseMapper().selectCount(queryWrapper);
    }
}
