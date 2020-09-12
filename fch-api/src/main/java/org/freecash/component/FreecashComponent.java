package org.freecash.component;

import org.freecash.core.client.FchdClient;
import org.freecash.core.domain.RawInput;
import org.freecash.core.domain.RawOutput;
import org.freecash.core.domain.RawTransaction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FreecashComponent {
    @Resource
    private FchdClient fchdClient;

    /**
     * 获取交易的发起方
     * @param txId 交易ID
     * @return 交易发起方地址
     * @throws Exception 异常
     */
    public String getBindAddress(String txId) throws Exception{
        RawTransaction rawTransaction = (RawTransaction)fchdClient.getRawTransaction(txId,true);
        RawInput rawInput = rawTransaction.getVIn().get(0);
        String id = rawInput.getTxId();
        int vout = rawInput.getVOut();

        rawTransaction = (RawTransaction)fchdClient.getRawTransaction(id,true);
        RawOutput rawOutput = rawTransaction.getVOut().get(vout);
        return rawOutput.getScriptPubKey().getAddresses().get(0);

    }

    /**
     * 获取交易的所有接收方地址
     * @param txId 交易id
     * @return 交易接收方地址列表
     * @throws Exception 异常
     */
    public List<String> getOutAddress(String txId) throws Exception{
        RawTransaction rawTransaction = (RawTransaction)fchdClient.getRawTransaction(txId,true);

        List<RawOutput> rawOutput = rawTransaction.getVOut();
        List<String> res = new ArrayList<>();
        rawOutput.forEach(item->{
            List<String> addresses = item.getScriptPubKey().getAddresses();
            if(Objects.nonNull(addresses)){
                res.addAll(addresses);
            }
        });
        return res;
    }
}
