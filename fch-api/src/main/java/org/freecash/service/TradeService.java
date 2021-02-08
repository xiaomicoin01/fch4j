package org.freecash.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.freecash.domain.FchVout;
import org.freecash.dto.CreateTradeRequest;
import org.freecash.dto.CreateTradeResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final FchVoutService voutService;

    public CreateTradeResponse createTrade(CreateTradeRequest request){
        List<FchVout> outs = voutService.findByIds(request.getUtxo());

        List<CreateTradeResponse.Input> inputs = new ArrayList<>();
        for(int i=0;i<outs.size();i++){
            FchVout out = outs.get(i);
            inputs.add( CreateTradeResponse.Input.builder()
                    .address(out.getAddress()).amount(out.getAmount()).txid(out.getTxId())
                    .dealType(1).index(out.getN()).seq(i+1)
                    .build());
        }

        List<CreateTradeResponse.Output> outputs = new ArrayList<>();
        for(int i=0;i<request.getTo().size();i++){
            CreateTradeRequest.To to = request.getTo().get(i);
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

        List<Object> txt = Lists.newArrayList();
        txt.addAll(inputs);
        txt.addAll(outputs);
        txt.add(message);

        return CreateTradeResponse.builder().inputs(inputs).outputs(outputs)
                .message(message).text(txt)
                .build();
    }
}
