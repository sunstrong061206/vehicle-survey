package com.fibikky.vehicle.web.pivot.apis.statistics;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fibikky.vehicle.common.web.response.Message;
import com.fibikky.vehicle.common.web.response.Response;
import com.fibikky.vehicle.common.web.response.ResponseBase;
import com.fibikky.vehicle.common.web.response.ReturnCode;
import com.fibikky.vehicle.web.main.entities.Exception;
import com.fibikky.vehicle.web.main.entities.Processing;
import com.fibikky.vehicle.web.main.services.IExceptionService;
import com.fibikky.vehicle.web.main.services.IProcessingService;
import com.fibikky.vehicle.web.pivot.apis.UserTokenProvider;
import com.fibikky.vehicle.web.pivot.models.ProcessQueryParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 16861
 */
@RestController
@RequestMapping(value = "/api/pro")
public class ProcessController {
    final IProcessingService iProcessingService;
    final IExceptionService exceptionService;

    public ProcessController(IProcessingService iProcessingService, IExceptionService exceptionService) {
        this.iProcessingService = iProcessingService;
        this.exceptionService = exceptionService;
    }

    @PostMapping(value = "/queryProcessing")
    @ApiOperation(value = "查询处理记录")
    public Response<IPage<Processing>> search(@RequestHeader("token") String token, @RequestBody ProcessQueryParam processQueryParam) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else {
            if (processQueryParam != null) {
                Page<Processing> page = new Page<>(processQueryParam.getPageNum(), processQueryParam.getPageSize());
                return new Response<>(ReturnCode.OK, Message.OK, iProcessingService.listByPage(page, processQueryParam));
            } else {
                return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
            }
        }
    }

    @PostMapping(value = "/saveOrUpdate")
    @ApiOperation(value = "新增或修改处理记录")
    public ResponseBase saveOrUpdate(@RequestHeader("token") String token, @RequestBody Processing processing) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else {
            if (processing != null) {
                if (iProcessingService.saveOrUpdate(processing)) {
                    if(processing.getProcessingId()!=null){
                        exceptionService.updateExceptionStatus(processing.getExceptionId(),processing.getProcessingStatus());
                    }
                    return new ResponseBase(ReturnCode.OK, Message.OK);
                } else {
                    return new ResponseBase(ReturnCode.PROCESSING_ADD_OR_UPDATE_ERROR, Message.PROCESSING_ADD_OR_UPDATE_ERROR);
                }
            } else {
                return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
            }
        }
    }

    @PostMapping(value = "/delete")
    @ApiOperation("删除处理记录")
    public ResponseBase delete(@RequestHeader("token") String token, @RequestBody Map<String, String> request) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else {
            if (request.get("abnormalVehicleNumber") != null) {
                if(iProcessingService.deleteByVehicleNumber(request.get("abnormalVehicleNumber"))==1){
                    return new ResponseBase(ReturnCode.OK, Message.OK);
                }else {
                    return new ResponseBase(ReturnCode.PROCESSING_DELETE_ERROR,Message.PROCESSING_DELETE_ERROR);
                }

            } else {
                return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
            }
        }
    }
}
