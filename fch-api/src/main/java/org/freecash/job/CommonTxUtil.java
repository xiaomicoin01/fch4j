package org.freecash.job;

import org.freecash.core.domain.RawInput;
import org.freecash.core.domain.RawOutput;
import org.freecash.core.domain.RawTransaction;
import org.freecash.domain.FchVin;
import org.freecash.domain.FchVout;
import org.freecash.utils.SnowflakeIdWorker;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CommonTxUtil {

    public static void processVoutAndVin(RawTransaction t, Set<FchVin> vins, Set<FchVout> vouts){
        List<RawOutput> outputs = t.getVOut();
        for(RawOutput out : outputs){
            BigDecimal amount =  out.getValue();

            List<String> addresses = out.getScriptPubKey().getAddresses();

            if(addresses == null || addresses.size() == 0){
                continue;
            }

            FchVout fchVout = new FchVout(
                    SnowflakeIdWorker.getUUID(),
                    t.getTxId(),
                    addresses.get(0),
                    out.getN(),
                    amount
            );
            vouts.add(fchVout);
        }

        for(RawInput input : t.getVIn()){
            String txId = input.getTxId();
            if(Objects.isNull(txId)){
                continue;
            }
            int n = input.getVOut();
            FchVin tmp = new FchVin(SnowflakeIdWorker.getUUID(),txId,n);
            vins.add(tmp);
        }
    }
}
