package com.fibikky.vehicle.common.web.response;

/**
 * 包装数据对象，使其总是包含返回代码与返回消息
 *
 * @param <T> 包装的实体数据类型
 * @author 16861
 */
public class Response<T> extends ResponseBase {
    public T data = null;

    public Response() {
        super();
    }

    public Response(int returnCode, String message) {
        super(returnCode, message);
    }

    public Response(int returnCode, String message, T data) {
        super(returnCode, message);
        this.data = data;
    }

    //链式调用专区

    /**
     * 修改data
     * @param data
     * @return
     */
    public Response<T> data(T data) {
        this.data=data;
        return this;
    }

    /**
     * 快速设置响应标准内容
     * @param value
     * @return
     */
    public Response<T> set(ResponseBase value){
        this.returnCode=value.returnCode;
        this.message=value.message;
        return this;
    }

    /**
     * 快速设置响应标准内容
     * @return
     */
    public Response<T> set(int returnCode,String message){
        this.returnCode=returnCode;
        this.message=message;
        return this;
    }

    /**
     * 当条件满足时将响应置为无效请求
     * @param condition
     * @return
     */
    public Response<T> invalidWhen(boolean condition) {
        if(condition){
            returnCode=ReturnCode.INVALID_REQUEST;
            message=Message.INVALID_REQUEST;
        }
        return this;
    }

    /**
     *
     * @param condition
     * @return
     */
    public <E> Response<T> when(boolean condition,E object,ResponseLambda<T,E> funcIf,ResponseNullLambda<T> funcElse) {
        if(this.returnCode!=ReturnCode.INVALID_REQUEST){
            if(condition) {
                return funcIf.invoke(this,object);
            }else {
                return funcElse.invoke(this);
            }
        }else{
            return this;
        }
    }

    public <E> Response<T> onNotNull(E object,ResponseLambda<T,E> funcIf,ResponseNullLambda<T> funcElse) {
        if(this.returnCode!=ReturnCode.INVALID_REQUEST){
            if(object!=null){
                return funcIf.invoke(this,object);
            }else {
                return funcElse.invoke(this);
            }
        }else{
            return this;
        }

    }
}

