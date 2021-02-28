package org.freecash.config;

import lombok.extern.log4j.Log4j2;
import org.freecash.utils.HttpResult;
import org.freecash.utils.HttpResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    public HttpResult<String> exception(Exception e){
        log.error(e);
        return HttpResult.<String>builder().code(HttpResultCode.EXCEPTION).message(e.getMessage()).data("").build();
    }
}
