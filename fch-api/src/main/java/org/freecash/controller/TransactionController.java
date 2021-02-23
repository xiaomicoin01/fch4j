package org.freecash.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.freecash.core.client.FchdClient;
import org.freecash.dto.TransactionRequest;
import org.freecash.utils.HttpResult;
import org.springframework.web.bind.annotation.*;

@Api(value = "交易创建接口")
@RestController
@RequestMapping("api/v1/tx")
@RequiredArgsConstructor
public class TransactionController {
    private final FchdClient client;

    @ApiOperation(value = "广播交易")
    @PostMapping("broadcast")
    public HttpResult<String> broadcastTransaction(@RequestBody TransactionRequest hex) throws Exception{
        String txId = client.sendRawTransaction(hex.getHex(),true);
        return HttpResult.SUCCESS(txId);
    }

    @ApiOperation(value = "解码交易")
    @PostMapping("decode")
    public HttpResult<String> decode(@RequestBody TransactionRequest hex) throws Exception{
        String tx = JSONObject.toJSONString(client.decodeRawTransaction(hex.getHex()));
        return HttpResult.SUCCESS(tx);
    }
}
