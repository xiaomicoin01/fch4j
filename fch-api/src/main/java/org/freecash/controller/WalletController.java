package org.freecash.controller;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.freecash.domain.AddressBalance;
import org.freecash.dto.CreateTradeRequest;
import org.freecash.dto.FchUserTxRecordRequest;
import org.freecash.dto.FchUserTxRecordResponse;
import org.freecash.service.AddressBalanceService;
import org.freecash.service.FchUserTxRecordService;
import org.freecash.utils.BigDecimalUtil;
import org.freecash.utils.HttpResult;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "钱包相关")
@RestController
@RequestMapping("api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final AddressBalanceService addressBalanceService;
    private final FchUserTxRecordService fchUserTxRecordService;
    @ApiOperation(value = "余额")
    @PostMapping("amount")
    public HttpResult<String> amount(String address){
        List<AddressBalance> balances = addressBalanceService.getBalances(Lists.newArrayList(address));
        if(CollectionUtils.isEmpty(balances)){
            return HttpResult.SUCCESS("0");
        }
        return HttpResult.SUCCESS(BigDecimalUtil.format(balances.get(0).getAmount(),BigDecimalUtil.POS_8));
    }

    @ApiOperation(value = "交易记录")
    @PostMapping("records")
    public HttpResult<FchUserTxRecordResponse> records(@RequestBody FchUserTxRecordRequest request){
        FchUserTxRecordResponse response = fchUserTxRecordService.query(request);
        return HttpResult.SUCCESS(response);
    }

    @ApiOperation(value = "发送")
    @PostMapping("send")
    public HttpResult<String> send(@RequestBody CreateTradeRequest request){
        return HttpResult.SUCCESS("");
    }
}
