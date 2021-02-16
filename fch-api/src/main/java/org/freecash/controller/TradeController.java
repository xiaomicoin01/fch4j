package org.freecash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.freecash.dto.CreateTradeRequest;
import org.freecash.dto.CreateTradeResponse;
import org.freecash.service.TradeService;
import org.freecash.utils.HttpResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@Api(value = "交易创建接口")
@RestController
@RequestMapping("api/v1/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @ApiOperation(value = "创建交易")
    @PostMapping("create")
    public HttpResult<CreateTradeResponse> list(@RequestBody CreateTradeRequest request) throws Exception{
        CreateTradeResponse response = tradeService.createTrade(request);
        return HttpResult.SUCCESS(response);
    }
}
