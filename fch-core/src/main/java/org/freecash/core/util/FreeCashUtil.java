package org.freecash.core.util;

import org.bitcoinj.core.*;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.bouncycastle.util.encoders.Hex;
import org.freecash.core.param.FchNetWorkParams;

import java.security.SecureRandom;
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
        NetworkParameters networkParameters = FchNetWorkParams.get() ;
        DeterministicSeed seed = new DeterministicSeed(new SecureRandom(), 128, "");
        Wallet wallet;
        String mnemonics = "";
        String privateKey = "";
        String publicKey = "";
        String address = "";
        String pwd = "";
            wallet = Wallet.fromSeed(networkParameters, seed);
            //私钥
            privateKey = wallet.currentReceiveKey().getPrivateKeyAsWiF(networkParameters);
            //助记词
            mnemonics = wallet.getKeyChainSeed().getMnemonicCode().toString();
            publicKey = Hex.toHexString(ECKey.publicKeyFromPrivate(wallet.currentReceiveKey().getPrivKey(), true));
            //地址
            address = wallet.currentReceiveAddress().toBase58();

        System.out.println("privateKey："+ privateKey);
        System.out.println("publicKey："+ publicKey);
        System.out.println("address："+ address);
    }
}
