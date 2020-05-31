package org.freecash.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.freecash.constant.ConstantKey;
import org.freecash.core.client.FchdClient;
import org.freecash.domain.Feip3v2;
import org.freecash.domain.SysUser;
import org.freecash.dto.*;
import org.freecash.enm.ErrorCodeEnum;
import org.freecash.jtw.util.JwtHelper;
import org.freecash.service.Feip3v2Service;
import org.freecash.service.SysUserService;
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
 * @date 2020/5/29 17:29
 **/
@Api(value = "CID登陆接口")
@RestController
@RequestMapping("api/v1/auth")
public class SsoController {
    @Resource
    private FchdClient client;
    @Resource
    private Feip3v2Service feip3v2Service;
    @Resource
    private SysUserService sysUserService;
    /**
     * 获取登陆验证码
     * @return
     */
    @ApiOperation(value = "获取随机码")
    @PostMapping("token")
    public HttpResult<TokenResponse> getToken(){
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        String uuid = SnowflakeIdWorker.getUUID();
        session.setAttribute(ConstantKey.UUID,uuid);
        return new HttpResult<TokenResponse>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),new TokenResponse(uuid));
    }


    /**
     * 获取登陆验证码
     * @param request 请求参数
     * @return
     */
    @ApiOperation(value = "签名登陆")
    @PostMapping("signature/login")
    public HttpResult<SignatureLoginResponse> login(@RequestBody SignatureLoginRequest request){
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = httpServletRequest.getSession();
        String cid = request.getCid();
        Feip3v2 feip3v2 = feip3v2Service.getFeip3v2(cid);
        if(feip3v2 == null){
            new HttpResult<String>(ErrorCodeEnum.FCH_ERROR.getCode(),"CID："+cid+"不存在","");
        }
        String uuid = session.getAttribute(ConstantKey.UUID).toString();
        try {
            boolean verifyRes = client.verifyMessage(feip3v2.getAddress(),request.getCode(),uuid);
            if(verifyRes){
                String userAgent = httpServletRequest.getHeader(ConstantKey.USER_AGENT);
                String token = JwtHelper.generateJWT(feip3v2.getId(),feip3v2.getNickName(),userAgent);
                return new HttpResult<SignatureLoginResponse>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),new SignatureLoginResponse(token));
            }else{
                return new HttpResult<SignatureLoginResponse>(ErrorCodeEnum.FCH_ERROR.getCode(),"签名错误",new SignatureLoginResponse());
            }

        } catch (Exception e) {
            return new HttpResult<SignatureLoginResponse>(ErrorCodeEnum.FCH_ERROR.getCode(),"签名错误",new SignatureLoginResponse());
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
        Feip3v2 feip3v2 = feip3v2Service.getFeip3v2(cid);
        if(feip3v2 == null){
            new HttpResult<Boolean>(ErrorCodeEnum.FCH_ERROR.getCode(),"CID："+cid+"不存在",false);
        }
        String uuid = session.getAttribute(ConstantKey.UUID).toString();
        try {
            boolean verifyRes = client.verifyMessage(feip3v2.getAddress(),request.getCode(),uuid);
            if(verifyRes){
                SysUser user;
                SysUser old = sysUserService.findUserByUserName(cid);
                if(old == null){
                    user = new SysUser();
                    user.setId(SnowflakeIdWorker.getUUID());
                    user.setUsername(cid);
                    user.setNickname(cid);
                    user.setPassword(MD5Util.encode(request.getPassword()));
                    String address = client.getAccountAddress(user.getId());
                    String privkey = client.dumpPrivKey(address);
                    user.setAddress(address);
                    user.setPrivkey(privkey);
                    user = sysUserService.add(user);
                }else{
                    old.setPassword(MD5Util.encode(request.getPassword()));
                    user = sysUserService.add(old);
                }

                if(user != null){
                    return new HttpResult<Boolean>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),true);
                }else{
                    return new HttpResult<Boolean>(ErrorCodeEnum.SERVICE_ERROR.getCode(),ErrorCodeEnum.SERVICE_ERROR.getMessage(),false);
                }
            }else{
                return new HttpResult<Boolean>(ErrorCodeEnum.FCH_ERROR.getCode(),"签名错误",false);
            }
        } catch (Exception e) {
            return new HttpResult<Boolean>(ErrorCodeEnum.FCH_ERROR.getCode(),"签名错误",false);
        }
    }

    /**
     * 获取登陆验证码
     * @param request 请求参数
     * @return
     */
    @ApiOperation(value = "密码登录")
    @PostMapping("password/login")
    public HttpResult<PasswordLoginResponse> register(@RequestBody PasswordLoginRequest request){
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String userName = request.getCid();
        String password = MD5Util.encode(request.getPassword());
        SysUser user = sysUserService.findUser(userName,password);
        if(user == null){
            return new HttpResult<>(ErrorCodeEnum.LOGIN_ERROR.getCode(),"CID或者密码错误",new PasswordLoginResponse());
        }
        String userAgent = httpServletRequest.getHeader(ConstantKey.USER_AGENT);
        String token = JwtHelper.generateJWT(user.getId(),user.getUsername(),userAgent);
        return new HttpResult<>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),new PasswordLoginResponse(token));
    }

    /**
     * 获取登陆验证码
     * @param token 待验证token
     * @return
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
