package com.hbnu.knowledgebase.exception;

import com.hbnu.knowledgebase.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<?>> handleValidationExceptions() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Result.error(400, "请求参数不合法"));
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Result<?>> handleAllExceptions() {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(Result.error(500, "服务器内部错误"));
//    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        e.printStackTrace(); // 打印异常堆栈，用于调试
        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部错误：" + e.getMessage());
    }
}







