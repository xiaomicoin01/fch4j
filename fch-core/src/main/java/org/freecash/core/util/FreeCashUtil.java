package org.freecash.core.util;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.freecash.core.param.FchNetWorkParams;

import java.util.Arrays;
import java.util.List;

public class FreeCashUtil {

    public static List<String> getFreecashInfo() throws Exception {
        FchNetWorkParams params = FchNetWorkParams.get();
        ECKey key = new ECKey();

        String privateKey = key.getPrivateKeyEncoded(params).toBase58();
        String address = new Address(params, key.getPubKeyHash()).toBase58();
        return Arrays.asList(privateKey,address);
    }

    public static String signMessage(String msg, String privateKey) {
        FchNetWorkParams params = FchNetWorkParams.get();
        DumpedPrivateKey priKey = DumpedPrivateKey.fromBase58(params, privateKey);
        ECKey ecKey = priKey.getKey();
        return ecKey.signMessage(msg);
    }


    public static boolean validMessage(String address, String signature, String message) {
        try {
            FchNetWorkParams params = FchNetWorkParams.get();
            return ECKey.signedMessageToKey(message, signature).toAddress(params).toString().equals(address);
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        String signMessage = "HwYFvnD38at5mgZ2AXBZ06bFMQWAP+kj+Wrd6RpPf7zrcfw4gFc2gl+jHz14taYe7AAskKGSafkCqbfB3qNNOp4=";
        String message = "123123";
        String address = "FJbBv9bRcmq6fHBnFrg2DrQFQZupfBTC3t";
        System.out.println(validMessage(address,signMessage,message));
    }
}
