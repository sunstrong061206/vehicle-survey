package com.fibikky.detect.server.inspect.main.service.Iml;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fibikky.detect.server.inspect.main.entity.Processing;
import com.fibikky.detect.server.inspect.main.mapper.ProcessingMapper;
import com.fibikky.detect.server.inspect.main.service.IProcessingService;
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
}
