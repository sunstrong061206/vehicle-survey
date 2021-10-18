package com.fibikky.vehicle.web.pivot.apis.equipments;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fibikky.vehicle.common.web.response.Message;
import com.fibikky.vehicle.common.web.response.Response;
import com.fibikky.vehicle.common.web.response.ResponseBase;
import com.fibikky.vehicle.common.web.response.ReturnCode;
import com.fibikky.vehicle.web.main.dto.EquipmentList;
import com.fibikky.vehicle.web.main.entities.DetectionLine;
import com.fibikky.vehicle.web.main.entities.DetectionStation;
import com.fibikky.vehicle.web.main.entities.Equipment;
import com.fibikky.vehicle.web.main.entities.Monitor;
import com.fibikky.vehicle.web.main.services.IDetectionLineService;
import com.fibikky.vehicle.web.main.services.IDetectionStationService;
import com.fibikky.vehicle.web.main.services.IEquipmentService;
import com.fibikky.vehicle.web.main.services.IMonitorService;
import com.fibikky.vehicle.web.pivot.apis.UserTokenProvider;
import com.fibikky.vehicle.web.pivot.models.EquipmentQueryParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 16861
 */
@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    final IEquipmentService equipmentService;
    final IDetectionLineService detectionLineService;
    final IDetectionStationService detectionStationService;
    final IMonitorService monitorService;

    public EquipmentController(IEquipmentService equipmentService, IDetectionLineService detectionLineService, IDetectionStationService detectionStationService, IMonitorService monitorService) {
        this.equipmentService = equipmentService;
        this.detectionLineService = detectionLineService;
        this.detectionStationService = detectionStationService;
        this.monitorService = monitorService;
    }

    /**
     * 设备综合检索
     * @param token
     * @param equipmentQueryParam 设备检索可用字段
     * @return Code,Message,分页对象
     */
    @PostMapping("/queryEquipment")
    @ApiOperation("设备综合检索")
    public Response<IPage<EquipmentList>> queryEquipment(@RequestHeader("token") String token, @RequestBody EquipmentQueryParam equipmentQueryParam) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else if (equipmentQueryParam != null) {
            Page<Equipment> page = new Page<>(equipmentQueryParam.getPageNum(), equipmentQueryParam.getPageSize());
            return new Response<>(ReturnCode.OK, Message.OK, equipmentService.listByPage(page, equipmentQueryParam));
        } else {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        }
    }

    /**
     * 新增或修改检测站记录
     * @param token
     * @param detectionStation 检测站参数
     * @return
     */
    @PostMapping(value = "/DetectionStation/saveOrUpdate")
    @ApiOperation(value = "新增或修改检测站记录")
    public ResponseBase saveOrUpdateDs(@RequestHeader("token") String token, @RequestBody DetectionStation detectionStation) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else if (detectionStation.getDetectionStationId() != null) {
            if (detectionStationService.saveOrUpdate(detectionStation)) {
                return new ResponseBase(ReturnCode.OK, Message.OK);
            } else {
                return new ResponseBase(ReturnCode.DETECTION_STATION_ADD_OR_UPDATE_ERROR, Message.DETECTION_STATION_ADD_OR_UPDATE_ERROR);
            }
        } else {
            return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        }
    }

    /**
     * 删除检测站记录
     * @param token
     * @param request 检测站id
     * @return
     */
    @PostMapping(value = "/DetectionStation/delete")
    @ApiOperation("删除检测站记录")
    public ResponseBase deleteDs(@RequestHeader("token") String token, @RequestBody Map<String, Integer> request) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else {
            if (request.get("detectionStationId") != null) {
                if (detectionStationService.removeById(request.get("detectionStationId"))) {
                    return new ResponseBase(ReturnCode.OK, Message.OK);
                } else {
                    return new ResponseBase(ReturnCode.DETECTION_STATION_DELETE_ERROR, Message.DETECTION_STATION_DELETE_ERROR);
                }

            } else {
                return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
            }
        }
    }

    /**
     * 新增或修改检测线记录
     * @param token
     * @param detectionLine 检测线参数
     * @return
     */
    @PostMapping(value = "/DetectionLine/saveOrUpdate")
    @ApiOperation(value = "新增或修改检测线记录")
    public ResponseBase saveOrUpdateDl(@RequestHeader("token") String token, @RequestBody DetectionLine detectionLine) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else if (detectionLine.getDetectionLineId() != null) {
            if (detectionLineService.saveOrUpdate(detectionLine)) {
                return new ResponseBase(ReturnCode.OK, Message.OK);
            } else {
                return new ResponseBase(ReturnCode.DETECTION_LINE_ADD_OR_UPDATE_ERROR, Message.DETECTION_LINE_ADD_OR_UPDATE_ERROR);
            }
        } else {
            return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        }
    }

    /**
     * 删除检测线记录
     * @param token
     * @param request 检测线id
     * @return
     */
    @PostMapping(value = "/DetectionLine/delete")
    @ApiOperation("删除检测线记录")
    public ResponseBase deleteDl(@RequestHeader("token") String token, @RequestBody Map<String, Integer> request) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else {
            if (request.get("detectionLineId") != null) {
                if (detectionLineService.removeById(request.get("detectionLineId"))) {
                    return new ResponseBase(ReturnCode.OK, Message.OK);
                } else {
                    return new ResponseBase(ReturnCode.DETECTION_LINE_DELETE_ERROR, Message.DETECTION_LINE_DELETE_ERROR);
                }

            } else {
                return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
            }
        }
    }

    /**
     * 新增或修改摄像头记录
     * @param token
     * @param monitor 监控参数
     * @return
     */
    @PostMapping(value = "/Monitor/saveOrUpdate")
    @ApiOperation(value = "新增或修改摄像头记录")
    public ResponseBase saveOrUpdateMon(@RequestHeader("token") String token, @RequestBody Monitor monitor) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else if (monitor.getMonitorId() != null) {
            if (monitorService.saveOrUpdate(monitor)) {
                return new ResponseBase(ReturnCode.OK, Message.OK);
            } else {
                return new ResponseBase(ReturnCode.MONITOR_ADD_OR_UPDATE_ERROR, Message.MONITOR_ADD_OR_UPDATE_ERROR);
            }
        } else {
            return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        }
    }

    /**
     * 删除摄像头记录
     * @param token
     * @param request 监控id
     * @return
     */
    @PostMapping(value = "/Monitor/delete")
    @ApiOperation("删除摄像头记录")
    public ResponseBase deleteMon(@RequestHeader("token") String token, @RequestBody Map<String, Integer> request) {
        if (!UserTokenProvider.validate(token)) {
            return new Response<>(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
        } else {
            if (request.get("monitorId") != null) {
                if (monitorService.removeById(request.get("monitorId"))) {
                    return new ResponseBase(ReturnCode.OK, Message.OK);
                } else {
                    return new ResponseBase(ReturnCode.MONITOR_DELETE_ERROR, Message.MONITOR_LINE_DELETE_ERROR);
                }
            } else {
                return new ResponseBase(ReturnCode.INVALID_REQUEST, Message.INVALID_REQUEST);
            }
        }
    }
}
