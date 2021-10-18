package com.fibikky.detect.server.inspect.main.service.Iml;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fibikky.detect.server.inspect.main.entity.Exception;
import com.fibikky.detect.server.inspect.main.mapper.ExceptionMapper;
import com.fibikky.detect.server.inspect.main.service.IExceptionService;
import org.springframework.stereotype.Repository;
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
@Repository
public class ExceptionServiceImpl extends ServiceImpl<ExceptionMapper, Exception> implements IExceptionService {

}
