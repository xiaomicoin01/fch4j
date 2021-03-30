package org.freecash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.freecash.utils.HttpResult;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@CrossOrigin(maxAge = 3600)
@Api(tags = "协议文档接口")
@RestController
@RequestMapping("api/v1/protocol")
@RequiredArgsConstructor
public class ProtocolDocumentController {

    @ApiOperation(value = "协议文档内容")
    @PostMapping("content")
    public HttpResult<String> list(String fileName) throws Exception{
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("protocols/" + fileName);
        String content = IOUtils.toString(input, StandardCharsets.UTF_8);
        return HttpResult.SUCCESS(content);
    }
}
