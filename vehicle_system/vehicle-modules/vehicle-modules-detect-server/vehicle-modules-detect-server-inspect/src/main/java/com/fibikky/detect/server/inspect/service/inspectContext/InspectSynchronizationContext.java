package com.fibikky.detect.server.inspect.service.inspectContext;

import com.fibikky.detect.server.inspect.models.Frame;
import com.fibikky.detect.server.inspect.service.InspectCore;

import java.util.Map;
import java.util.TreeMap;

import static com.fibikky.vehicle.common.util.DateUtil.timestamp2Date;

/**
 * 检测核心同步上下文
 *
 * @author 16861
 */
public class InspectSynchronizationContext {
    LprFrameRaw monitorFrame;
    FrontFrameRaw frontFrame;
    BehindFrameRaw behindFrame;
    Long latestUpdateTime = System.currentTimeMillis();
    public InspectCore currentCore;

    /**
     * 将帧发送至检测核心
     *
     * @param lpr
     * @param front
     * @param back
     * @return
     */
    public void post(LprFrameRaw lpr, FrontFrameRaw front, BehindFrameRaw back) {
        //TODO: marshal correct frame info here!
        var frontFrame = new Frame();
        var backFrame = new Frame();
        var combined = new CombinedFrame(frontFrame, backFrame);
        var vehicleNumber = lpr.getLp();
        if (vehicleNumber != null) {
            frontFrame.vehicleNumber = vehicleNumber;
            backFrame.vehicleNumber = vehicleNumber;
            //检测员在车上嘛？
            if (front.containsLabel(5)) {
                frontFrame.states.put("INSPECTOR_STAY_ON_CAR", true);
            } else {
                frontFrame.states.put("INSPECTOR_LEFT_CAR", true);
            }
            //检测到排气管了嘛？
            if (back.containsLabel(1)) {
                backFrame.states.put("PIPE_PRESENT", true);
            }
            //检测管只插了一半嘛？
            if (back.containsLabel(3)) {
                backFrame.states.put("HALF_INSERT_PIPE_PRESENT", true);
            }
            //检测管插的合格了嘛？
            if (back.containsLabel(0)|| back.containsLabel(2)) {
                backFrame.states.put("HALF_INSERT_PIPE_PRESENT", true);
            }
            frontFrame.time = timestamp2Date(latestUpdateTime);
            backFrame.time = timestamp2Date(latestUpdateTime);
        }
        currentCore.handle(combined);
    }

    private void updateLatestTime() {
        latestUpdateTime = System.currentTimeMillis();
    }

    public void updateMonitor(LprFrameRaw frame) {
        this.monitorFrame = frame;
        updateLatestTime();
        nullCheckAndTryReset();
    }

    public void updateFront(FrontFrameRaw frame) {
        this.frontFrame = frame;
        updateLatestTime();
        nullCheckAndTryReset();
    }

    public void updateBehind(BehindFrameRaw frame) {
        this.behindFrame = frame;
        updateLatestTime();
        nullCheckAndTryReset();
    }

    public boolean nullCheckAndTryReset() {
        boolean condition = monitorFrame != null && frontFrame != null && behindFrame != null;
        if (condition) {
            post(monitorFrame, frontFrame, behindFrame);

            monitorFrame = null;
            frontFrame = null;
            behindFrame = null;
        }
        return condition;
    }
}

