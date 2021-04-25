package com.gnq.base;

import com.gnq.base.enums.ResponseCode;
import lombok.Data;

@Data
public class BaseResponse<T>{

    private int code = ResponseCode.SUCCESS.code();
    private String message;
    private T result;

    public BaseResponse() {
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse code(int code){
        this.code = code;
        this.message = ResponseCode.getDescByCode(code);
        return this;
    }

    public BaseResponse message(String message){
        this.message = message;
        return this;
    }

    public BaseResponse result(T result){
        this.result = result;
        return this;
    }

    /**
     * 失败
     * @param code
     * @return
     */
    public static BaseResponse<String> error(int code) {
        return new BaseResponse<String>().code(code);
    }

    /**
     * 失败
     * @param code
     * @param message
     * @return
     */
    public static BaseResponse<String> error(int code, String message) {
        return new BaseResponse<>(code, message);
    }
}