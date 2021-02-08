package org.freecash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.freecash.dto.UtxoRequest;
import org.freecash.dto.UtxoResponse;
import org.freecash.service.FchVoutService;
import org.freecash.utils.HttpResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@Api(value = "UTXO接口")
@RestController
@RequestMapping("api/v1/utxo")
@RequiredArgsConstructor
public class UtxoController {

    private final FchVoutService fchVoutService;

    @ApiOperation(value = "获取UTXO列表")
    @PostMapping("list")
    public HttpResult<UtxoResponse> list(@RequestBody UtxoRequest request) throws Exception{
        UtxoResponse response = fchVoutService.query(request);
        return HttpResult.SUCCESS(response);
    }
}
