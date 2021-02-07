package org.freecash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.freecash.dto.CreateTradeRequest;
import org.freecash.dto.CreateTradeResponse;
import org.freecash.service.TradeService;
import org.freecash.utils.HttpResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "交易创建接口")
@RestController
@RequestMapping("api/v1/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @ApiOperation(value = "创建交易")
    @PostMapping("create")
    public HttpResult<CreateTradeResponse> list(@RequestBody CreateTradeRequest request){
        CreateTradeResponse response = tradeService.createTrade(request);
        return HttpResult.SUCCESS(response);
    }
}
