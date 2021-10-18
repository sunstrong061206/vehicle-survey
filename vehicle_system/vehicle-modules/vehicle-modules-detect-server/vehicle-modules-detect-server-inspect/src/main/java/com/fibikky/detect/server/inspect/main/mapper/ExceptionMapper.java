package com.fibikky.detect.server.inspect.main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fibikky.detect.server.inspect.main.entity.Exception;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sWwag
 * @since 2021-09-09
 */
@Mapper
public interface ExceptionMapper extends BaseMapper<Exception> {

}
