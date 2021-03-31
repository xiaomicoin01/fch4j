package org.freecash.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.bouncycastle.util.encoders.Hex;
import org.freecash.core.client.FchdClient;
import org.freecash.core.domain.RawInput;
import org.freecash.core.domain.RawTransaction;
import org.freecash.core.param.FchNetWorkParams;
import org.freecash.core.util.FreeCashUtil;
import org.freecash.domain.Feip3;
import org.freecash.dto.*;
import org.freecash.service.Feip3Service;
import org.freecash.utils.HttpResult;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;

@CrossOrigin(maxAge = 3600)
@Api(tags = "钱包API接口")
@RestController
@RequestMapping("api/v1/tool/wallet")
@RequiredArgsConstructor
public class WalletApi {

    private final Feip3Service feip3Service;
    private final FchdClient fchdClient;

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

    @ApiOperation(value = "通过地址获取公钥")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "地址", required = true, dataType = "string")
    })
    @PostMapping("address/pk")
    public HttpResult<String> getPublicKey(String address) throws Exception{
        Feip3 feip3 = feip3Service.getFeip3ByAddress(address);
        if(Objects.isNull(feip3)){
            return HttpResult.FAIL(100,"该地址暂未注册CID","");
        }
        String txId = feip3.getTxHash();
        RawTransaction rawTransaction = (RawTransaction)fchdClient.getRawTransaction(txId, true);
        RawInput input = rawTransaction.getVIn().get(0);
        String asm = input.getScriptSig().getAsm();
        String[] res = asm.split("\\[ALL\\|FORKID\\]");
        String pk = res.length == 1 ? "" : res[1].trim();
        return HttpResult.SUCCESS(pk);
    }
}
