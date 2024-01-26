package cn.hqx.system.exception;

import cn.hqx.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description 全局异常处理
 * @Create by hqx
 * @Date 2024/1/26 20:19
 */

@RestControllerAdvice
public class GlobalExceptionHandler {


    // 全局异常
    @ExceptionHandler(Exception.class)
    public Result<Boolean> error(Exception e) {
        return Result.fail(false).message("执行了全局异常处理");
    }

    // 特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    public Result<Boolean> arithmeticError(Exception e) {
        return Result.fail(false).message(e.getMessage());
    }

    // 自定义异常处理
    @ExceptionHandler(BusinessException.class)
    public Result<Boolean> businessError(Exception e) {
        BusinessException businessException = (BusinessException) e;
        return Result.fail(false).message(businessException.getMsg());
    }

}
