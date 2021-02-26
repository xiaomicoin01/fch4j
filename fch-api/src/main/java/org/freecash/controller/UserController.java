package org.freecash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.freecash.constant.ConstantKey;
import org.freecash.core.util.FreeCashUtil;
import org.freecash.domain.FchUser;
import org.freecash.domain.Feip3;
import org.freecash.dto.UserRequest;
import org.freecash.dto.UserResponse;
import org.freecash.service.FchUserService;
import org.freecash.service.Feip3Service;
import org.freecash.utils.HttpResult;
import org.freecash.utils.HttpResultCode;
import org.freecash.utils.SnowflakeIdWorker;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Api(value = "登陆相关")
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final FchUserService userService;
    private final Feip3Service feip3Service;

    @ApiOperation(value = "登陆")
    @PostMapping("login")
    public HttpResult<UserResponse> login(@RequestBody UserRequest userRequest){
        FchUser user = userService.findUserByUserName(userRequest.getUsername());
        if(Objects.isNull(user)){
            return HttpResult.FAIL(HttpResultCode.LOGIN_ERROR,"CID不存在",null);
        }
        if(!Objects.equals(user.getPassword(),userRequest.getPassword())){
            return HttpResult.FAIL(HttpResultCode.LOGIN_ERROR,"CID或者密码错误",null);
        }
        Feip3 feip3 = feip3Service.getFeip3(userRequest.getUsername());
        return HttpResult.SUCCESS(UserResponse.builder().username(user.getUsername())
                .address(user.getAddress()).cidAddress(feip3.getAddress()).build());
    }

    @ApiOperation(value = "验证码")
    @PostMapping("code")
    public HttpResult<String> code(HttpSession session){
        String tmp = SnowflakeIdWorker.getUUID();
        String code = tmp.substring(tmp.length() - 6);
        session.setAttribute(ConstantKey.CODE, code);
        return HttpResult.SUCCESS(code);
    }

    @ApiOperation(value = "登陆")
    @PostMapping("login/qr")
    public HttpResult<UserResponse> loginForQr(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception{
        String[] tmp = userRequest.getSignStr().split("----");
        String codeStr = tmp[0];
        String addressStr = tmp[1];
        String signStr = tmp[2];
        String code = request.getSession().getAttribute(ConstantKey.CODE).toString();
        if(!Objects.equals(code, codeStr)){
            return HttpResult.FAIL(HttpResultCode.LOGIN_ERROR,"验证码已过期",null);
        }
        boolean isValid = FreeCashUtil.validMessage(addressStr,signStr,code);
        if(!isValid){
            return HttpResult.FAIL(HttpResultCode.LOGIN_ERROR,"验证码签名不正确",null);
        }
        Feip3 feip3 = feip3Service.getFeip3ByAddress(tmp[1]);
        if(Objects.isNull(feip3)){
            return HttpResult.FAIL(HttpResultCode.LOGIN_ERROR,"地址：" + addressStr + "未注册地址CID",null);
        }
        FchUser user = userService.findUserByCid(feip3.getName());
        if(Objects.isNull(user)){
            user = userService.createUser(feip3.getName());
        }
        return HttpResult.SUCCESS(UserResponse.builder().username(user.getUsername())
                .address(user.getAddress()).cidAddress(feip3.getAddress()).build());
    }

    @ApiOperation(value = "注册或者修改密码")
    @PostMapping("register")
    public HttpResult<UserResponse> register(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception{
        String[] tmp = userRequest.getSignStr().split("----");
        String codeStr = tmp[0];
        String addressStr = tmp[1];
        String signStr = tmp[2];
        String code = request.getSession().getAttribute(ConstantKey.CODE).toString();
        if(!Objects.equals(code, codeStr)){
            return HttpResult.FAIL(HttpResultCode.LOGIN_ERROR,"验证码已过期",null);
        }
        boolean isValid = FreeCashUtil.validMessage(addressStr,signStr,code);
        if(!isValid){
            return HttpResult.FAIL(HttpResultCode.LOGIN_ERROR,"验证码签名不正确",null);
        }
        Feip3 feip3 = feip3Service.getFeip3ByAddress(tmp[1]);
        if(Objects.isNull(feip3)){
            return HttpResult.FAIL(HttpResultCode.LOGIN_ERROR,"地址：" + addressStr + "未注册地址CID",null);
        }
        FchUser user = userService.findUserByCid(feip3.getName());
        if(Objects.isNull(user)){
            user = userService.createUser(feip3.getName(),userRequest.getPassword());
        }else{
            user.setPassword(userRequest.getPassword());
            user = userService.add(user);
        }
        return HttpResult.SUCCESS(UserResponse.builder().username(user.getUsername())
                .address(user.getAddress()).cidAddress(feip3.getAddress()).build());
    }
}
