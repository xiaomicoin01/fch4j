package org.freecash.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 结果接
 * @param <T>
 */
@Data
@NoArgsConstructor
public class HttpResult<T> {
    @ApiModelProperty("状态码，非0失败，0成功")
    private int code;
    @ApiModelProperty("状态码")
    private String message;
    @ApiModelProperty("状态码")
    private T data;

    public HttpResult(int status, String message, T data) {
        this.code = status;
        this.message = message;
        this.data = data;
    }
}
