package com.fibikky.vehicle.web.pivot.apis.statistics;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fibikky.vehicle.common.web.response.Message;
import com.fibikky.vehicle.common.web.response.Response;
import com.fibikky.vehicle.common.web.response.ResponseBase;
import com.fibikky.vehicle.common.web.response.ReturnCode;
import com.fibikky.vehicle.common.web.excel.ExcelUtil;
import com.fibikky.vehicle.web.main.dto.ExceptionList;
import com.fibikky.vehicle.web.main.entities.Exception;
import com.fibikky.vehicle.web.main.services.IExceptionService;
import com.fibikky.vehicle.web.pivot.apis.UserTokenProvider;
import com.fibikky.vehicle.web.pivot.models.ExceptionQueryParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author sWwag
 */
@RestController
@RequestMapping(value = "/api/except")
public class
ExceptionController {
    final IExceptionService iExceptionService;

    public ExceptionController(IExceptionService iExceptionService) {
        this.iExceptionService = iExceptionService;
    }

    /**
     * 查询异常记录
     * @param token
     * @param exceptionQueryParam 异常查询参数
     * @return code,message
     */
    @PostMapping(value = "/queryException")
    @ApiOperation(value = "查询异常记录")
    public Response<IPage<ExceptionList>> search(@RequestHeader("token") String token, @RequestBody ExceptionQueryParam exceptionQueryParam) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else if (exceptionQueryParam != null) {
            Page<Exception> page = new Page<>(exceptionQueryParam.getPageNum(), exceptionQueryParam.getPageSize());
            return new Response<>(ReturnCode.OK, Message.OK, iExceptionService.listByPage(page, exceptionQueryParam));
        } else {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        }
    }

    /**
     * 新增或修改异常记录
     * @param token
     * @param exception 异常可修改参数
     * @return
     */
    @PostMapping(value = "/saveOrUpdate")
    @ApiOperation(value = "新增或修改异常记录")
    public ResponseBase saveOrUpdate(@RequestHeader("token") String token, @RequestBody Exception exception) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else if (exception != null) {
            if (iExceptionService.saveOrUpdate(exception)) {
                return new ResponseBase(ReturnCode.OK, Message.OK);
            } else {
                return new ResponseBase(ReturnCode.EXCEPTION_ADD_OR_UPDATE_ERROR, Message.EXCEPTION_ADD_OR_UPDATE_ERROR);
            }
        } else {
            return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        }
    }

    /**
     * 删除异常记录
     * @param token
     * @param request 异常车辆车牌号
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除异常记录")
    public ResponseBase delete(@RequestHeader("token") String token, @RequestBody Map<String, Integer> request) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else {
            if (request.get("exceptionId") != null) {
                iExceptionService.deleteByExceptionId(request.get("exceptionId"));
                return new ResponseBase(ReturnCode.OK, Message.OK);
            } else {
                return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
            }
        }
    }

    /**
     * 导出表格
     * @param token
     * @param response
     * @param exceptionQueryParam
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/export")
    @ApiOperation("导出表格")
    public ResponseBase export(@RequestHeader("token") String token, HttpServletResponse response, ExceptionQueryParam exceptionQueryParam) throws IOException {

        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else if (exceptionQueryParam != null) {
            Page<Exception> page = new Page<>(exceptionQueryParam.getPageNum(), exceptionQueryParam.getPageSize());
            ExcelUtil<Exception> util = new ExcelUtil<>(Exception.class);
            util.exportExcel(response, iExceptionService.export(exceptionQueryParam), "异常信息表");
            return null;
        } else {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        }
    }
}
