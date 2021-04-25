package com.gnq.base.config;

import com.gnq.base.BaseException;
import com.gnq.base.BaseResponse;
import com.gnq.base.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public BaseResponse<String> exceptionHandler(Exception e){
        log.error("Exception:{}", e);
        return BaseResponse.error(ResponseCode.FAIL.code());
    }

    @ExceptionHandler(value = Throwable.class)
    public BaseResponse<String> throwableHandler(Throwable e){
        log.error("Throwable:{}", e);
        return BaseResponse.error(ResponseCode.FAIL.code());
    }

    /**
     * 业务异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    public BaseResponse<String> baseExceptionHandler(BaseException e){
        log.error("BaseException:{}", e);
        return BaseResponse.error(e.getErrorCode(), e.getErrorMsg());
    }
}