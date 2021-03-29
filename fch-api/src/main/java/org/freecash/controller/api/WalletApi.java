package org.freecash.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.bouncycastle.util.encoders.Hex;
import org.freecash.core.param.FchNetWorkParams;
import org.freecash.core.util.FreeCashUtil;
import org.freecash.dto.*;
import org.freecash.utils.HttpResult;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@Api(tags = "钱包API接口")
@RestController
@RequestMapping("api/v1/tool/wallet")
@RequiredArgsConstructor
public class WalletApi {

    @ApiOperation(value = "获取助记符，公钥，私钥和地址")
    @GetMapping("info")
    public HttpResult<AddressInfoResponse> list(){
        DeterministicSeed seed = new DeterministicSeed(new SecureRandom(), 128, "");
        Wallet wallet = Wallet.fromSeed(FchNetWorkParams.get(), seed);
        List<String> mnemonics = wallet.getKeyChainSeed().getMnemonicCode();
        String privateKey = wallet.currentReceiveKey().getPrivateKeyAsWiF(FchNetWorkParams.get());
        String publicKey = Hex.toHexString(ECKey.publicKeyFromPrivate(wallet.currentReceiveKey().getPrivKey(), true));
        String address = wallet.currentReceiveAddress().toBase58();

        return HttpResult.SUCCESS(AddressInfoResponse.builder()
                .mnemonics(mnemonics).privateKey(privateKey).publicKey(publicKey).address(address)
                .build());
    }

    @ApiOperation(value = "签名认证")
    @PostMapping("valid/message")
    public HttpResult<ValidMessageResponse> validMessage(@RequestBody ValidMessageRequest request){
        boolean res = FreeCashUtil.validMessage(request.getAddress(), request.getSignMessage(),request.getMessage());

        return HttpResult.SUCCESS(ValidMessageResponse.builder()
                .code(0).message("").valid(res)
                .build());
    }
}
