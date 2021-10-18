package com.fibikky.vehicle.common.web.response;

/**
 * @author 16861
 */
@FunctionalInterface
public interface ResponseLambda<TResponseType, TParamType> {
    /**
     * 调用方法
     * @param param
     * @return
     */
    Response<TResponseType> invoke(Response<TResponseType> response,TParamType param);
}

