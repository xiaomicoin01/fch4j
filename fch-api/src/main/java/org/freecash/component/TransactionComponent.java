package org.freecash.component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.freecash.core.client.FchdClient;
import org.freecash.core.domain.OutputOverview;
import org.freecash.core.domain.RawInput;
import org.freecash.core.domain.RawTransaction;
import org.freecash.core.domain.SignatureResult;
import org.freecash.domain.FchVout;
import org.freecash.domain.Feip3;
import org.freecash.service.Feip3Service;
import org.freecash.utils.BigDecimalUtil;
import org.freecash.utils.HexStringUtil;
import org.freecash.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author wanglint
 **/
@Component
@RequiredArgsConstructor
public class TransactionComponent {
    private final FchdClient fchdClient;
    private final Feip3Service feip3Service;

    /**
     * 创建交易
     * @param inputs 所有输入，对应FCH的outputs
     * @param outputs 所有输出
     * @param smallChangeAddress 找零地址
     * @param message 消息原文，发送到上了之前会被转成16进制
     * @return 交易的16进制表示方式
     * @throws Exception 异常
     */
    public String createRawTransaction(List<FchVout> inputs, List<FchVout> outputs, String smallChangeAddress, BigDecimal fee, String message) throws Exception {
        BigDecimal total = inputs.stream().map(FchVout::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal sendTotal = outputs.stream().map(FchVout::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal diff = total.subtract(sendTotal).subtract(fee);
        if(diff.compareTo(BigDecimal.ZERO) < 0){
            throw new Exception("发送金额:"+ BigDecimalUtil.format(total,BigDecimalUtil.POS_8) +
                    "接收金额：" + BigDecimalUtil.format(sendTotal,BigDecimalUtil.POS_8) + ",余额不足，请减少发币量");
        }


        List<OutputOverview> outputOverviews = new ArrayList<>();
        inputs.forEach(in->
            outputOverviews.add(new OutputOverview(in.getTxId(),in.getN()))
        );

        boolean existAddress = false;
        List<Map<String, Object>> params = Lists.newArrayList();
        for(FchVout out : outputs){
            Map<String, Object> params1 = Maps.newHashMap();
            if(Objects.equals(out.getAddress(),smallChangeAddress)){
                params1.put(out.getAddress(),out.getAmount().add(diff));
                existAddress = true;
            }else{
                params1.put(out.getAddress(),out.getAmount());
            }
            params.add(params1);
        }
        if(!existAddress){
            Map<String, Object> params1 = Maps.newHashMap();
            params1.put(smallChangeAddress, diff);
            params.add(params1);
        }

        Map<String, Object> params2 = Maps.newHashMap();
        params2.put("data", HexStringUtil.stringToHexString(StringUtil.isEmpty(message) ? "1" : message));
        params.add(params2);
        return fchdClient.createRawTransaction(outputOverviews, params);
    }

    /**
     * 签名交易
     * @param txHash 交易的Hex字符串
     * @param privateKey 私钥
     * @return 签名后的交易的Hex字符串
     * @throws Exception 异常
     */
    public String signRawTransactionWithKey(String txHash,String privateKey) throws Exception {
        SignatureResult signatureResult = fchdClient.signRawTransactionWithKey(txHash, Lists.newArrayList(privateKey));
        if (signatureResult.getComplete()) {
            return signatureResult.getHex();
        } else {
            throw new Exception("签名失败");
        }
    }

    /**
     * 广播交易
     * @param txHash 签名后的交易的Hex字符串
     * @return 返回交易ID
     * @throws Exception 异常
     */
    public String sendRawTransaction(String txHash) throws Exception {
        return fchdClient.sendRawTransaction(txHash, true);
    }


    /**
     * 通过交易ID获取公钥
     * @param cid CID
     * @return 公钥
     * @throws Exception 异常
     */
    public String getPublicKey(String cid) throws Exception{
        Feip3 feip3v2 = feip3Service.getFeip3(cid);
        if(Objects.isNull(feip3v2)){
            throw new Exception("CID:"+cid+",未找到");
        }
        RawTransaction rawTransaction = (RawTransaction)fchdClient.getRawTransaction(feip3v2.getTxHash(),true);
        RawInput input = rawTransaction.getVIn().get(0);
        String asm = input.getScriptSig().getAsm();
        String[] res = asm.split("\\[ALL\\|FORKID\\]");
        return res.length == 1 ? "" : res[1].trim();
    }

    public Integer getCoinDay(String txId, BigDecimal amount) throws Exception{
        RawTransaction rawTransaction = (RawTransaction)fchdClient.getRawTransaction(txId,true);
        long start = rawTransaction.getTime();
        long end = new Date().getTime();
        return ((Double)((end/1000 - start)/3600/24 * amount.doubleValue())).intValue();
    }
}
