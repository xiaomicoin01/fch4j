package org.freecash.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.freecash.component.TransactionComponent;
import org.freecash.domain.FchVout;
import org.freecash.dto.CreateTradeRequest;
import org.freecash.dto.CreateTradeResponse;
import org.freecash.utils.HexStringUtil;
import org.freecash.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final FchVoutService voutService;

    public CreateTradeResponse createTrade(CreateTradeRequest request) throws Exception {
        List<FchVout> outs = voutService.findByIds(request.getUtxo());
        BigDecimal inCount = BigDecimal.ZERO;
        BigDecimal outCount = BigDecimal.ZERO;
        BigDecimal fee;

        List<CreateTradeResponse.Input> inputs = new ArrayList<>();
        for(int i=0;i<outs.size();i++){
            FchVout out = outs.get(i);
            inCount = inCount.add(out.getAmount());
            inputs.add( CreateTradeResponse.Input.builder()
                    .address(out.getAddress()).amount(out.getAmount()).txid(out.getTxId())
                    .dealType(1).index(out.getN()).seq(i+1)
                    .build());
        }

        List<CreateTradeResponse.Output> outputs = new ArrayList<>();
        for(int i=0;i<request.getTo().size();i++){
            CreateTradeRequest.To to = request.getTo().get(i);
            outCount = outCount.add(new BigDecimal(to.getAmount()));
            outputs.add(CreateTradeResponse.Output.builder()
                    .address(to.getAddress())
                    .amount(to.getAmount())
                    .dealType(2)
                    .seq(i+1)
                    .build());
        }
        CreateTradeResponse.Message message = CreateTradeResponse.Message.builder()
                .msg(request.getMessage())
                .dealType(3).msgtype(1).build();

        int len = HexStringUtil.stringToHexString(StringUtils.isEmpty(request.getMessage()) ? "1" : request.getMessage()).length();
        fee = BigDecimal.valueOf((outs.size() * 148 + 34 * (outputs.size() + 1) + 10 + len ) * 0.00000001);
        BigDecimal diff = inCount.subtract(outCount).subtract(fee);
        if(diff.compareTo(BigDecimal.ZERO) < 0){
            throw new Exception("金额不足");
        }
        if(diff.compareTo(BigDecimal.ZERO) > 0){
            outputs.add(CreateTradeResponse.Output.builder()
                    .address(outs.get(0).getAddress())
                    .amount(diff.doubleValue())
                    .dealType(2)
                    .seq(outputs.size()+1)
                    .build());
        }
        List<Object> txt = Lists.newArrayList();
        txt.addAll(inputs);
        txt.addAll(outputs);
        txt.add(message);

        return CreateTradeResponse.builder().inputs(inputs).outputs(outputs)
                .message(message).text(txt)
                .build();
    }
}
