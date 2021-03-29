package org.freecash.controller.api;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.freecash.constant.ConstantKey;
import org.freecash.core.client.FchdClient;
import org.freecash.domain.Feip3;
import org.freecash.domain.FchUser;
import org.freecash.dto.*;
import org.freecash.enm.ErrorCodeEnum;
import org.freecash.jtw.util.JwtHelper;
import org.freecash.service.Feip3Service;
import org.freecash.service.FchUserService;
import org.freecash.utils.HttpResult;
import org.freecash.utils.MD5Util;
import org.freecash.utils.SnowflakeIdWorker;
import org.freecash.utils.StringUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author wanglint
 **/
@Api(tags = "基于CID的单点登陆接口")
@RestController
@RequestMapping("api/v1/tool/sso")
public class SsoController {
    @Resource
    private FchdClient client;
    @Resource
    private Feip3Service feip3Service;
    @Resource
    private FchUserService fchUserService;
    /**
     * 获取登陆验证码
     */
    @ApiOperation(value = "获取随机码")
    @PostMapping("random")
    public HttpResult<TokenResponse> getToken(){
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        String uuid = SnowflakeIdWorker.getUUID();
        session.setAttribute(ConstantKey.UUID,uuid);
        return new HttpResult<>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),new TokenResponse(uuid));
    }


    /**
     * 获取登陆验证码
     * @param request 请求参数
     */
    @ApiOperation(value = "签名登陆")
    @PostMapping("signature/login")
    public HttpResult<SignatureLoginResponse> login(@RequestBody SignatureLoginRequest request){
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = httpServletRequest.getSession();
        String cid = request.getCid();
        Feip3 feip3 = feip3Service.getFeip3(cid);
        if(feip3 == null){
            new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),"CID："+cid+"不存在","");
        }
        String uuid = session.getAttribute(ConstantKey.UUID).toString();
        try {
            boolean verifyRes = client.verifyMessage(feip3.getAddress(),request.getCode(),uuid);
            if(verifyRes){
                String userAgent = httpServletRequest.getHeader(ConstantKey.USER_AGENT);
                String token = JwtHelper.generateJWT(Integer.toString(feip3.getPid()),feip3.getName(),userAgent);
                return new HttpResult<>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),new SignatureLoginResponse(token));
            }else{
                return new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),"签名错误",new SignatureLoginResponse());
            }

        } catch (Exception e) {
            return new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),"签名错误",new SignatureLoginResponse());
        }
    }


    /**
     * 获取登陆验证码
     * @param request 请求参数
     * @return
     */
    @ApiOperation(value = "注册密码,可替代签名信息")
    @PostMapping("password/register")
    public HttpResult<Boolean> register(@RequestBody PasswordRegisterRequest request){
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = httpServletRequest.getSession();
        String cid = request.getCid();
        Feip3 feip3 = feip3Service.getFeip3(cid);
        if(feip3 == null){
            new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),"CID："+cid+"不存在",false);
        }
        String uuid = session.getAttribute(ConstantKey.UUID).toString();
        try {
            boolean verifyRes = client.verifyMessage(feip3.getAddress(),request.getCode(),uuid);
            if(verifyRes){
                FchUser user;
                FchUser old = fchUserService.findUserByUserName(cid);
                if(old == null){
                    user = new FchUser();
                    user.setUsername(cid);
                    user.setNickname(cid);
                    user.setPassword(MD5Util.encode(request.getPassword()));
                    String address = client.getAccountAddress(Integer.toString(user.getPid()));
                    String privkey = client.dumpPrivKey(address);
                    user.setAddress(address);
                    user.setPrivkey(privkey);
                    user = fchUserService.add(user);
                }else{
                    old.setPassword(MD5Util.encode(request.getPassword()));
                    user = fchUserService.add(old);
                }

                if(user != null){
                    return new HttpResult<>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),true);
                }else{
                    return new HttpResult<>(ErrorCodeEnum.SERVICE_ERROR.getCode(),ErrorCodeEnum.SERVICE_ERROR.getMessage(),false);
                }
            }else{
                return new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),"签名错误",false);
            }
        } catch (Exception e) {
            return new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),"签名错误",false);
        }
    }

    /**
     * 获取登陆验证码
     * @param request 请求参数
     */
    @ApiOperation(value = "密码登录")
    @PostMapping("password/login")
    public HttpResult<PasswordLoginResponse> register(@RequestBody PasswordLoginRequest request){
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String userName = request.getCid();
        String password = MD5Util.encode(request.getPassword());
        FchUser user = fchUserService.findUser(userName,password);
        if(user == null){
            return new HttpResult<>(ErrorCodeEnum.LOGIN_ERROR.getCode(),"CID或者密码错误",new PasswordLoginResponse());
        }
        String userAgent = httpServletRequest.getHeader(ConstantKey.USER_AGENT);
        String token = JwtHelper.generateJWT(Integer.toString(user.getPid()),user.getUsername(),userAgent);
        return new HttpResult<>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),new PasswordLoginResponse(token));
    }

    /**
     * 获取登陆验证码
     * @param token 待验证token
     */
    @ApiOperation(value = "验证token是否有效")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String")
    @PostMapping("validate")
    public HttpResult<ValidTokenReponse> validToken(@RequestParam(value = "token") String token){
        String res = JwtHelper.validateLogin(token);
        if(StringUtil.isEmpty(res)){
            return new HttpResult<>(ErrorCodeEnum.TOKEN_ERROR.getCode(),ErrorCodeEnum.TOKEN_ERROR.getMessage(),new ValidTokenReponse());
        }else{
            ValidTokenReponse response = JSON.parseObject(res,ValidTokenReponse.class);
            return new HttpResult<>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),response);
        }
    }
}
