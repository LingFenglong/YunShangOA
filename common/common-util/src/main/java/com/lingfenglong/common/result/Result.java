package com.lingfenglong.common.result;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;   // 状态码
    private String message; // 信息
    private T data;         // 数据

    private Result() {
    }

    private static <T> Result<T> build(T data, ResultCode resultCode) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }

    /**
     *
     * @param code 状态码
     * @return 这个 Result<?> 对象
     */
    public Result<?> code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     *
     * @param message 返回的信息内容
     * @return 这个 Result<?> 对象
     */
    public Result<?> message(String message) {
        this.setMessage(message);
        return this;
    }

    // 成功
    public static <T> Result<T> ok(T data) {
        return build(data, ResultCode.SUCCESS);
    }

    // 失败
    public static <T> Result<T> fail(T data) {
        return build(data, ResultCode.FAIL);
    }

    // 通过布尔类型的数据，返回成功或失败的结果
    public static Result<?> bool(boolean success) {
        if (success) {
            return ok(null);
        } else {
            return fail(null);
        }
    }
}
