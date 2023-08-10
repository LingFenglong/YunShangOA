package com.lingfenglong.common.config.exception;

import com.lingfenglong.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> error(Exception e) {
        e.printStackTrace();
        return Result.fail(null).message("来自全局异常处理器");
    }

    // 特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result<?> arithmeticException(Exception e) {
        e.printStackTrace();
        return Result.fail(null).message("来自特定异常处理器");
    }

    // 自定义异常处理
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Result<?> myException(MyException e) {
        e.printStackTrace();
        return Result.fail(null)
                .code(e.getCode())
                .message(e.getMessage());
    }
}
