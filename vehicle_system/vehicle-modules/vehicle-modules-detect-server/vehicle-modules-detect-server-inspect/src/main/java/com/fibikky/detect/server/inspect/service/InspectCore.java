package com.fibikky.detect.server.inspect.service;

import com.fibikky.detect.server.inspect.config.ServerStorageConfig;
import com.fibikky.detect.server.inspect.main.entity.Exception;
import com.fibikky.detect.server.inspect.main.entity.Processing;
import com.fibikky.detect.server.inspect.main.service.IExceptionService;
import com.fibikky.detect.server.inspect.main.service.IProcessingService;
import com.fibikky.detect.server.inspect.models.Frame;
import com.fibikky.detect.server.inspect.service.inspectContext.CombinedFrame;
import com.fibikky.vehicle.io.FileIOService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author 16861
 */
@Service
public class InspectCore {
    private static final String INSPECTOR_LEFT_CAR="检测员提前下车";
    private static final String NON_INSPECTOR_APPEAR="非检测人员进入";
    private static final String SMOKE_PRESENT="尾气异常";
    private static final String PIPE_PRESENT="检测管插入数量异常";
    private static final String NOTHING_PRESENT="未检测到排气管";
    private static final String HALF_INSERT_PIPE_PRESENT="检测管未完全插入";
    private static final String INSERT_OR_TRAPPED_PRESENT="提前抽离采样管";



    final ServerStorageConfig serverStorageConfig;
    final FileIOService fileIOService=new FileIOService();
    final IExceptionService exceptionService;
    final IProcessingService processingService;

    public InspectCore(IExceptionService exceptionService, ServerStorageConfig serverStorageConfig, IProcessingService processingService) {
        this.exceptionService = exceptionService;
        this.serverStorageConfig = serverStorageConfig;
        this.processingService = processingService;
    }

    InspectTask coreTask = new InspectTask();

    public void handle(CombinedFrame combinedFrame){
        handle(combinedFrame.front,combinedFrame.behind);
    }

    private void handle(Frame frontFrame, Frame behindFrame) {
        coreTask.startIfNotStarted();
        if (coreTask.IsStarted) {
            coreTask.update(frontFrame, behindFrame)
                    .stable("INSPECTOR_STAY_ON_CAR", inspectorOnCar -> inspectorOnCar
                                    .stable("INSPECTOR_LEFT_CAR", null,inspectorLeftCarEarly ->
                                            marshalBaseInfo(inspectorLeftCarEarly.currentFrontFrame, INSPECTOR_LEFT_CAR),true)
                                    .stable("NON_INSPECTOR_APPEAR", nonInspectorAppear ->
                                            marshalBaseInfo(nonInspectorAppear.currentFrontFrame, NON_INSPECTOR_APPEAR), true)
                                    .drive(behindDetectTask -> behindDetectTask
                                            .stable("SMOKE_PRESENT", smokePresent ->
                                                    marshalBaseInfo(smokePresent.currentBehindFrame, SMOKE_PRESENT), false)
                                            .stable("PIPE_PRESENT", pipePresent ->
                                                    marshalBaseInfo(pipePresent.currentBehindFrame, PIPE_PRESENT), false)
                                            .stable("NOTHING_PRESENT", nothingPresent ->
                                                    marshalBaseInfo(nothingPresent.currentBehindFrame, NOTHING_PRESENT), false)
                                            .stable("HALF_INSERT_PIPE_PRESENT", halfInsertPipePresent ->
                                                    marshalBaseInfo(halfInsertPipePresent.currentBehindFrame, HALF_INSERT_PIPE_PRESENT), false)
                                            .stable("INSERT_OR_TRAPPED_PRESENT", null, earlyDrawPipe ->
                                                    marshalBaseInfo(earlyDrawPipe.currentBehindFrame, INSERT_OR_TRAPPED_PRESENT), false)
                ),finishDetection -> finishDetection.IsFinished=true
            , true);
        } else if (coreTask.IsFinished) {
            coreTask = new InspectTask();
        }
    }

    /**
     * 向前端封送异常
     *
     * @param frame  帧
     * @param exType 异常类型字符串
     */
    private void marshalBaseInfo(Frame frame, String exType) {
        String path = serverStorageConfig.getB64ImgStoragePath() + UUID.randomUUID().toString();
        fileIOService.saveImg(frame.img, path);
        var ex = new Exception(frame.vehicleNumber, new Date(), exType, path, frame.line, frame.station, frame.monitor, 0);
        var pro = new Processing(ex.getExceptionId(),frame.vehicleNumber,0);
        exceptionService.getBaseMapper().insert(ex);
        processingService.getBaseMapper().insert(pro);
    }
}



