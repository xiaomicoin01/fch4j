package org.freecash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.freecash.dto.CidRequest;
import org.freecash.dto.CidResponse;
import org.freecash.service.Feip3Service;
import org.freecash.utils.HttpResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@Api(value = "CID接口")
@RestController
@RequestMapping("api/v1/cid")
@RequiredArgsConstructor
public class CidController {

    private final Feip3Service feip3Service;

    @ApiOperation(value = "获取CID列表")
    @PostMapping("list")
    public HttpResult<CidResponse> list(@RequestBody CidRequest request){
        CidResponse response = feip3Service.query(request);
        return HttpResult.SUCCESS(response);
    }
}
