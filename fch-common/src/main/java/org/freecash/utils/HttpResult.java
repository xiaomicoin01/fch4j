package org.freecash.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HttpResult {
    private int code;
    private String message;
    private Object data;


    public HttpResult(int status, String message, Object data) {
        this.code = status;
        this.message = message;
        this.data = data;
    }
}
