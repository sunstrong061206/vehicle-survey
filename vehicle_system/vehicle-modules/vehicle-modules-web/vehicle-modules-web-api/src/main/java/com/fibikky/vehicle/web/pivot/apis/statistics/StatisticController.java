package com.fibikky.vehicle.web.pivot.apis.statistics;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fibikky.vehicle.common.util.DateUtil;
import com.fibikky.vehicle.common.web.response.Message;
import com.fibikky.vehicle.common.web.response.Response;
import com.fibikky.vehicle.common.web.response.ReturnCode;
import com.fibikky.vehicle.web.main.dto.WeekException;
import com.fibikky.vehicle.web.main.entities.Monitor;
import com.fibikky.vehicle.web.main.services.IExceptionService;
import com.fibikky.vehicle.web.main.services.IMonitorService;
import com.fibikky.vehicle.web.pivot.apis.UserTokenProvider;
import com.fibikky.vehicle.web.pivot.models.WeekQueryParam;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author 16861
 */
@RestController
@RequestMapping("/api/stat")
public class StatisticController {
    final IMonitorService monitorService;
    final IExceptionService exceptionService;

    public StatisticController(IMonitorService monitorService, IExceptionService exceptionService) {
        this.monitorService = monitorService;
        this.exceptionService = exceptionService;
    }

    @GetMapping("/getDistribution")
    public Response<List<Monitor>> getDistributionPosition(@RequestHeader("token") String token) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else {
            List<Monitor> list = monitorService.getBaseMapper()
                    .selectList(new QueryWrapper<Monitor>()
                            .lambda());
            return new Response<>(ReturnCode.OK, Message.OK, list);
        }
    }

    @GetMapping("/day/getExceptionCount")
    public Response<Integer> getDayExceptionCount(@RequestHeader("token") String token) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else {
            var d0 = new Date();
            d0.setHours(0);
            d0.setMinutes(0);
            d0.setSeconds(0);

            var d24 = new Date();
            d24.setHours(23);
            d24.setMinutes(59);
            d24.setSeconds(59);

            var count = exceptionService.getExceptionCountBetween(d0,d24);
            return new Response<>(ReturnCode.OK, Message.OK, count);
        }
    }

    @GetMapping("/day/getDetectionLineExceptionCount")
    public Response<Integer> getDayDetectionLineExceptionCount(@RequestHeader("token") String token, @RequestBody Map<String, Integer> request) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else {
            var d0 = new Date();
            d0.setHours(0);
            d0.setMinutes(0);
            d0.setSeconds(0);

            var d24 = new Date();
            d24.setHours(23);
            d24.setMinutes(59);
            d24.setSeconds(59);

            var count = exceptionService.getDetectionLineExceptionCount(request.get("detectionLineId"),d0,d24);
            return new Response<>(ReturnCode.OK, Message.OK, count);
        }
    }

    @GetMapping("/day/getNotDealtExceptionCount")
    public Response<Integer> getDayNotDealtExceptionCount(@RequestHeader("token") String token) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else {
            var d0 = new Date();
            d0.setHours(0);
            d0.setMinutes(0);
            d0.setSeconds(0);

            var d24 = new Date();
            d24.setHours(23);
            d24.setMinutes(59);
            d24.setSeconds(59);

            var count = exceptionService.getDayNotDealtExceptionCount(d0,d24);
            return new Response<>(ReturnCode.OK, Message.OK, count);
        }
    }

    @PostMapping("/week/getExceptionCount")
    public Response<List<WeekException>> getWeekExceptionCount(@RequestHeader("token") String token, @RequestBody WeekQueryParam weekQueryParam) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else {
            if(weekQueryParam.scope!=null){
                List<WeekException> list =exceptionService.getExceptionCountIntervalScopeDay(weekQueryParam);
                return new Response<>(ReturnCode.OK, Message.OK, list);
            }else {
                return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
            }
        }
    }
}
