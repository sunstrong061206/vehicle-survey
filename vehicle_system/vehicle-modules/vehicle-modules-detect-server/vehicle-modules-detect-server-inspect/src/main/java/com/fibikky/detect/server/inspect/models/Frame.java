package com.fibikky.detect.server.inspect.models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 本类中的字段必须尝试填满
 * 否则会导致检测失败
 *
 * @author 16861
 */
public class Frame {
    public Date time;
    public String vehicleNumber;
    public int line;
    public int station;
    public int monitor;
    public byte[] img;

    public HashMap<String,Boolean> states;

    public Frame(){
        states=new HashMap<String,Boolean>();
        //以下内容必须使用合理的值进行设置
        states.put("INSPECTOR_STAY_ON_CAR",null);
        states.put("INSPECTOR_LEFT_CAR",null);
        states.put("NON_INSPECTOR_APPEAR",null);
        states.put("SMOKE_PRESENT",null);
        states.put("PIPE_PRESENT",null);
        states.put("NOTHING_PRESENT",null);
        states.put("HALF_INSERT_PIPE_PRESENT",null);
        states.put("INSERT_OR_TRAPPED_PRESENT",null);
    }
}
