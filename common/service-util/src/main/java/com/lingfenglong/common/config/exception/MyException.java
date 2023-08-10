package com.lingfenglong.common.config.exception;

import com.lingfenglong.common.result.ResultCode;

public class MyException extends RuntimeException {
    private Integer code;   // 状态码

    public MyException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public MyException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    @Override
    public String toString() {
        return "MyException{" +
                "code=" + code +
                ", message='" + getMessage() + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }
}
