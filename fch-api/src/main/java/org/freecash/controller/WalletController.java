package org.freecash.controller;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.freecash.constant.ConstantKey;
import org.freecash.core.client.FchdClient;
import org.freecash.domain.AddressBalance;
import org.freecash.domain.FchUser;
import org.freecash.domain.FchVout;
import org.freecash.dto.CreateTradeRequest;
import org.freecash.dto.FchUserTxRecordRequest;
import org.freecash.dto.FchUserTxRecordResponse;
import org.freecash.service.AddressBalanceService;
import org.freecash.service.FchUserTxRecordService;
import org.freecash.service.FchVoutService;
import org.freecash.utils.BigDecimalUtil;
import org.freecash.utils.HttpResult;
import org.freecash.utils.HttpResultCode;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tools.FchTool;
import org.tools.TxInput;
import org.tools.TxOutput;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


@Api(value = "钱包相关")
@RestController
@RequestMapping("api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final AddressBalanceService addressBalanceService;
    private final FchUserTxRecordService fchUserTxRecordService;
    private final FchVoutService fchVoutService;
    private final FchdClient client;

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
    public HttpResult<String> send(@RequestBody CreateTradeRequest tradeRequest, HttpSession session) throws Exception{
        FchUser user = (FchUser)session.getAttribute(ConstantKey.SESSION_USER);
        if(!Objects.equals(tradeRequest.getFrom(),user.getAddress())){
            return HttpResult.FAIL(HttpResultCode.LOGIN_REQUIRE,"请重新登陆",null);
        }
        List<FchVout> outs = fchVoutService.findByAddress(user.getAddress());
        List<TxInput> inputs= Lists.newArrayList();
        BigDecimal inCount = BigDecimal.ZERO;
        for( FchVout item : outs) {
            TxInput input1=new TxInput();
            input1.setAmount(new BigDecimal(item.getAmount().toString()).multiply(new BigDecimal(100000000L)).longValue());
            input1.setTxId(item.getTxId());
            input1.setIndex(item.getN());
            input1.setPrivateKey(user.getPrivkey());
            inputs.add(input1);
            inCount = inCount.add(item.getAmount());
        }

        List<TxOutput> outputs= Lists.newArrayList();
        BigDecimal outCount = BigDecimal.ZERO;
        for(CreateTradeRequest.To to : tradeRequest.getTo()){
            TxOutput output3=new TxOutput();
            output3.setAmount(new BigDecimal(to.getAmount().toString()).multiply(new BigDecimal(100000000L)).longValue());
            output3.setAddress(to.getAddress());
            outputs.add(output3);
            outCount = outCount.add(new BigDecimal(to.getAmount().toString()));
        }

        long fee = FchTool.calcMinFee(inputs.size(), outputs.size(),tradeRequest.getMessage(),user.getAddress(),1L);
        BigDecimal diff = inCount.subtract(outCount);
        if(diff.compareTo(BigDecimal.ZERO)<0){
            return HttpResult.FAIL(HttpResultCode.AMOUNT_LESS,"余额不足",null);
        }
        diff = diff.subtract(new BigDecimal(fee*0.00000001));
        if(diff.compareTo(BigDecimal.ZERO)<0){
            return HttpResult.FAIL(HttpResultCode.FEE_LESS,"余额不足矿工费",null);
        }

        String tx = FchTool.createTransactionSign(inputs,outputs,tradeRequest.getMessage(),user.getAddress(),fee);
        String txId = client.sendRawTransaction(tx,true);
        return HttpResult.SUCCESS(txId);
    }
}
