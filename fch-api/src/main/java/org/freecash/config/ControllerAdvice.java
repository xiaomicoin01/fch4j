package org.freecash.config;

import org.freecash.utils.HttpResult;
import org.freecash.utils.HttpResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    public HttpResult<String> exception(Exception e){
        return HttpResult.<String>builder().code(HttpResultCode.EXCEPTION).message(e.getMessage()).data("").build();
    }
}
