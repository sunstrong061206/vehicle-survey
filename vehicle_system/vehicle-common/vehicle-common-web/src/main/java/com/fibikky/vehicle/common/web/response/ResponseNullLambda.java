package com.fibikky.vehicle.common.web.response;

/**
 * @author 16861
 */
@FunctionalInterface
public interface ResponseNullLambda<TResponseType> {
    /**
     * 调用方法
     * @return
     */
    Response<TResponseType> invoke(Response<TResponseType> response);
}
