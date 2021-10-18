package com.fibikky.detect.server.inspect.service;

import com.fibikky.detect.server.inspect.models.Frame;

import java.util.Date;
import java.util.HashMap;

public class InspectTask {
    public boolean IsStarted = false;
    public boolean IsFinished = false;
    public Frame currentFrontFrame;
    public Frame currentBehindFrame;

    private final HashMap<String, Date> timeStamp = new HashMap<String, Date>();

    private final HashMap<String,Integer> timeOffset = new HashMap<String,Integer>();

    public InspectTask(){
        timeStamp.put("INSPECTOR_STAY_ON_CAR",null);
        timeStamp.put("INSPECTOR_LEFT_CAR",null);
        timeStamp.put("NON_INSPECTOR_APPEAR",null);
        timeStamp.put("SMOKE_PRESENT",null);
        timeStamp.put("PIPE_PRESENT",null);
        timeStamp.put("NOTHING_PRESENT",null);
        timeStamp.put("HALF_INSERT_PIPE_PRESENT",null);
        timeStamp.put("INSERT_OR_TRAPPED_PRESENT",null);

        timeOffset.put("INSPECTOR_STAY_ON_CAR",0);
        timeOffset.put("INSPECTOR_LEFT_CAR",0);
        timeOffset.put("NON_INSPECTOR_APPEAR",0);
        timeOffset.put("SMOKE_PRESENT",0);
        timeOffset.put("PIPE_PRESENT",0);
        timeOffset.put("NOTHING_PRESENT",0);
        timeOffset.put("HALF_INSERT_PIPE_PRESENT",0);
        timeOffset.put("INSERT_OR_TRAPPED_PRESENT",0);
    }

    public InspectTask stable(String actionName,InspectAction action,boolean isFront){
        //初始化起始时间戳
        if(timeStamp.get(actionName)==null){
            timeStamp.put(actionName,getFrame(isFront).time);
        }else{//存在时间戳，说明之前已开始稳定性判断
            var f= getFrame(isFront);
            var stateTime=timeStamp.get(actionName);
            //当前帧存在该状态
            if (f.states.get(actionName)){
                //继续验证
                if(stateTime.getTime()+timeOffset.get(actionName)>f.time.getTime()){
                    //时间不足，等待后续帧判断
                }else{
                    //时间足够，完成稳定检测
                    if(action!=null){
                        action.invoke(this);
                    }
                }
            }else{
                //动作结束，继续验证
                if(stateTime.getTime()+timeOffset.get(actionName)>f.time.getTime()){
                    //提前结束
                }else{
                    //正常结束
                    if(action!=null){
                        action.invoke(this);
                    }
                }
                timeStamp.put(actionName,null);
            }
        }
        return this;
    }
    public InspectTask stable(String actionName,InspectAction action,InspectAction earlyAction,boolean isFront){
        //初始化起始时间戳
        if(timeStamp.get(actionName)==null){
            timeStamp.put(actionName,getFrame(isFront).time);
        }else{//存在时间戳，说明之前已开始稳定性判断
            var f= getFrame(isFront);
            var stateTime=timeStamp.get(actionName);
            //当前帧存在该状态
            if (f.states.get(actionName)){
                //继续验证
                if(stateTime.getTime()+timeOffset.get(actionName)>f.time.getTime()){
                    //时间不足，等待后续帧判断
                }else{
                    //时间足够，完成稳定检测
                    if(action!=null){
                        action.invoke(this);
                    }
                }
            }else{
                //动作结束，继续验证
                if(stateTime.getTime()+timeOffset.get(actionName)>f.time.getTime()){
                    //提前结束
                    if(earlyAction!=null){
                        earlyAction.invoke(this);
                    }
                }else{
                    //正常结束
                    if(action!=null){
                        action.invoke(this);
                    }
                }
                timeStamp.put(actionName,null);
            }
        }
        return this;
    }

    public InspectTask drive(InspectAction action){
        if(action!=null){
            action.invoke(this);
        }
        return this;
    }

    public void startIfNotStarted() {
        if(!IsFinished){
            if(!IsStarted){
                IsStarted = true;
            }
        }

    }

    private Frame getFrame(boolean isFront){
        return isFront?currentFrontFrame : currentBehindFrame;
    }


    /**
     * 将当前的处理帧替换为新的帧
     * @param frontFrame
     * @param behindFrame
     */
    public InspectTask update(Frame frontFrame, Frame behindFrame) {
        this.currentFrontFrame=frontFrame;
        this.currentBehindFrame=behindFrame;
        return this;
    }
}

