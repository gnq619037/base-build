package com.gnq.base;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {
    protected int errorCode;
    protected String errorMsg;

    public BaseException() {
        super();
    }

    public BaseException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}