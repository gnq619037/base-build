package com.gnq.base.enums;

public enum ResponseCode {

    SYSTEM_ERROR(-1, "系统异常"),
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    PARAMS_ERROR(2, "参数不合规"),
    SERVICE_ERROR(3, "服务端异常"),
    NO_LOGIN(5, "未登录"),
    NO_PERMISSION(403, "没有操作权限");

    private int code;
    private String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public static String getDescByCode(int code){
        ResponseCode[] responseCodes = values();
        for(ResponseCode responseCode : responseCodes) {
            if(responseCode.code == code) {
                return responseCode.desc;
            }
        }
        return null;
    }

    public static int getCodeByDesc(String desc){
        ResponseCode[] responseCodes = values();
        for(ResponseCode responseCode : responseCodes) {
            if(responseCode.desc.equals(desc)) {
                return responseCode.code;
            }
        }
        return ResponseCode.SYSTEM_ERROR.code;
    }
}