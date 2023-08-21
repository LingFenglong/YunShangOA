package com.lingfenglong.common.result;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    LOGIN_ERROR(207, "登录失败"),
    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
