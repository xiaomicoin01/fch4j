package org.freecash.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.freecash.component.dto.*;
import org.freecash.core.client.FchdClient;
import org.freecash.enm.ErrorCodeEnum;
import org.freecash.enm.FreeDriveInterfaceEnum;
import org.freecash.utils.HttpClientComponent;
import org.freecash.utils.HttpResult;
import org.freecash.utils.Sha256Util;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;

/**
 * @author wanglint
 **/
@Log4j2
public abstract class AbstractFreeDriveComponent<R extends FreeDriveEntity> {
    @Value("${freecash.freedrive.put}")
    private String putUrl;
    @Value("${freecash.freedrive.list}")
    private String listUrl;
    @Value("${freecash.freedrive.get}")
    private String getUrl;
    @Value("${freecash.freedrive.get_drive_info}")
    private String getDriveInfoUrl;
    @Value("${freecash.freedrive.auth}")
    private String authUrl;
    @Value("${freecash.address}")
    String dappAddress;
    @Value("${freecash.privkey}")
    String dappprivKey;

    @Resource
    FchdClient fchdClient;

    public FreeDrivePutResponse put(FreeDrivePutRequest request) throws Exception{
        return send(request,putUrl,FreeDrivePutResponse.class);
    }

    public FreeDriveGetResponse put(FreeDriveGetRequest request) throws Exception{
        return send(request,getUrl, FreeDriveGetResponse.class);
    }

    public FreeDriveListResponse put(FreeDriveListRequest request) throws Exception{
        return send(request,listUrl,FreeDriveListResponse.class);
    }

    public FreeDriveListDetailResponse put(FreeDriveListDetailRequest request) throws Exception{
        return send(request,listUrl,FreeDriveListDetailResponse.class);
    }

    public FreeDriveGetDriveInfoResponse put(FreeDriveGetDriveInfoRequest request) throws Exception{
        return send(request,getDriveInfoUrl, FreeDriveGetDriveInfoResponse.class);
    }

    public FreeDriveAuthResponse put(FreeDriveAuthRequest request) throws Exception{
        return send(request,authUrl, FreeDriveAuthResponse.class);
    }

    private String toJson(Object obj) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();

        StringWriter sw = new StringWriter();
        objectMapper.writeValue(sw, obj);
        return sw.toString();
    }

    public HttpResult<String> add(R freeProtocol,String address,String privkey){
        FreeDrivePutRequest request;
        try{
            request = getPutRequest(freeProtocol,address,privkey);
        }catch (Exception e){
            log.error(e.getMessage());
            return new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),e.getMessage(),"");
        }

        try {
            FreeDrivePutResponse response = put(request);
            if(Objects.equals(response.getCode(),ErrorCodeEnum.SUCCESS.getCode())){
                log.debug("推送文件到FreeDrive成功，上链成功");
                freeProtocol.setDriveId(response.getDriveId());
                return new HttpResult<>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),response.getDriveId());

            }else{
                log.error("推送文件到FreeDrive报错,错误码：{},错误信息：{}",response.getCode(),response.getMsg());
                return new HttpResult<>(ErrorCodeEnum.FREE_DRIVE_ERROR.getCode(),
                        "错误码："+response.getCode(),response.getDriveId());
            }
        } catch (Exception e) {
            try {
                log.error("推送文件到FreeDrive报错,错误信息：{},参数为：{}",e.getMessage(), toJson(request));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),
                    "推送文件到FreeDrive报错","");
        }
    }

    public HttpResult<List<FreeDriveGetResponse.FreeDriveGetDriveIdResponseItem>> get(R freeProtocol,String address,String privkey){
        FreeDriveGetRequest request;
        try{
            request = getGetRequest(freeProtocol, address,privkey);
        }catch (Exception e){
            log.error(e);
            return new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),e.getMessage(),null);
        }
        try {
            FreeDriveGetResponse response = put(request);
            if(Objects.equals(response.getCode(),ErrorCodeEnum.SUCCESS.getCode())){
                return new HttpResult<>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),response.getResult());
            }else{
                log.error("更新文件失败，driveId：{}，错误码：{},错误信息：{}",freeProtocol.getDriveId(),
                        response.getCode(),response.getMsg());
                return new HttpResult<>(ErrorCodeEnum.FREE_DRIVE_ERROR.getCode(),
                        "错误码："+response.getCode()+",错误信息为："+response.getMsg(),null);
            }
        } catch (Exception e) {
            log.error("更新文件失败，driveId：{}，信息为：{}",freeProtocol.getDriveId(),e.getMessage());
            return new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),
                    "推送文件到FreeDrive报错:"+e.getMessage(),null);
        }
    }

    public HttpResult<Object> list(R freeProtocol, String address, String privkey){
        if(Objects.equals(freeProtocol.getDetail(),"0")){
            FreeDriveListRequest request;
            try{
                request = getListRequest(freeProtocol, address,privkey);
            }catch (Exception e){
                log.error(e);
                return new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),e.getMessage(),null);
            }
            try {
                FreeDriveListResponse response = put(request);
                if(Objects.equals(response.getCode(),ErrorCodeEnum.SUCCESS.getCode())){
                    return new HttpResult<>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),response.getResult());
                }else{
                    log.error("更新文件失败，driveId：{}，错误码：{},错误信息：{}",freeProtocol.getDriveId(),
                            response.getCode(),response.getMsg());
                    return new HttpResult<>(ErrorCodeEnum.FREE_DRIVE_ERROR.getCode(),
                            "错误码："+response.getCode()+",错误信息为："+response.getMsg(),null);
                }
            } catch (Exception e) {
                log.error("更新文件失败，driveId：{}，信息为：{}",freeProtocol.getDriveId(),e.getMessage());
                return new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),
                        "推送文件到FreeDrive报错:"+e.getMessage(),null);
            }
        }else{
            FreeDriveListDetailRequest request;
            try{
                request = getListDetailRequest(freeProtocol, address,privkey);
            }catch (Exception e){
                log.error(e);
                return new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),e.getMessage(),null);
            }
            try {
                FreeDriveListDetailResponse response = put(request);
                if(Objects.equals(response.getCode(),ErrorCodeEnum.SUCCESS.getCode())){
                    return new HttpResult<>(ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),response.getResult());
                }else{
                    log.error("更新文件失败，driveId：{}，错误码：{},错误信息：{}",freeProtocol.getDriveId(),
                            response.getCode(),response.getMsg());
                    return new HttpResult<>(ErrorCodeEnum.FREE_DRIVE_ERROR.getCode(),
                            "错误码："+response.getCode()+",错误信息为："+response.getMsg(),null);
                }
            } catch (Exception e) {
                log.error("更新文件失败，driveId：{}，信息为：{}",freeProtocol.getDriveId(),e.getMessage());
                return new HttpResult<>(ErrorCodeEnum.FCH_ERROR.getCode(),
                        "推送文件到FreeDrive报错:"+e.getMessage(),null);
            }
        }

    }

    /**
     * 获取增加Reqeust
     * @param src 原始数据
     * @param signAddress 地址
     * @param signPrivkey 私钥
     * @return 返回添加的request
     * @throws Exception 异常
     */
    private  FreeDrivePutRequest getPutRequest(R src, String signAddress, String signPrivkey) throws Exception{
        FreeDrivePutRequest request = new FreeDrivePutRequest();

        request.setUserAddress(signAddress);
        request.setDappAddress(dappAddress);
        request.setTimestamp(System.currentTimeMillis()+"");

        String dataHex = getFreeDriveDataString(src);
        String sha265Hex = Sha256Util.getSHA256(dataHex);
        src.setDataHash(sha265Hex);
        request.setData(dataHex);

        String metDataHex = getFreeDriveMetaDataString(src);
        request.setMetadata(metDataHex);

        if(StringUtils.isEmpty(src.getDriveId())){
            request.setDriveId("0");
        }else{
            request.setDriveId(src.getDriveId());
        }
        String signMessage = getUserSignature(request,signPrivkey);
        request.setUserSignature(signMessage);
        return request;
    }

    /**
     * 获取增加Reqeust
     * @param src 原始数据
     * @param signAddress 地址
     * @param signPrivkey 私钥
     * @return 返回添加的request
     * @throws Exception 异常
     */
    private  FreeDriveGetRequest getGetRequest(R src, String signAddress, String signPrivkey) throws Exception{
        FreeDriveGetRequest request = new FreeDriveGetRequest();

        request.setDappAddress(dappAddress);
        if(StringUtils.isEmpty(src.getDriveId())){
            request.setDriveId("0");
        }else{
            request.setDriveId(src.getDriveId());
        }
        request.setTimestamp(System.currentTimeMillis()+"");
        String signMessage = getUserSignature(request,signPrivkey);
        request.setUserSignature(signMessage);
        return request;
    }
    /**
     * 获取增加Reqeust
     * @param src 原始数据
     * @param signAddress 地址
     * @param signPrivkey 私钥
     * @return 返回添加的request
     * @throws Exception 异常
     */
    private  FreeDriveListRequest getListRequest(R src, String signAddress, String signPrivkey) throws Exception{
        FreeDriveListRequest request = new FreeDriveListRequest();
        request.setProtocol(src.getProtocol());
        request.setAddr(signAddress);
        request.setPage(src.getPage());
        request.setDappAddress(dappAddress);
        request.setTimestamp(System.currentTimeMillis()+"");
        String signMessage = getUserSignature(request,signPrivkey);
        request.setUserSignature(signMessage);
        return request;
    }

    /**
     * 获取增加Reqeust
     * @param src 原始数据
     * @param signAddress 地址
     * @param signPrivkey 私钥
     * @return 返回添加的request
     * @throws Exception 异常
     */
    private  FreeDriveListDetailRequest getListDetailRequest(R src, String signAddress, String signPrivkey) throws Exception{
        FreeDriveListDetailRequest request = new FreeDriveListDetailRequest();
        request.setProtocol(src.getProtocol());
        request.setAddr(signAddress);
        request.setPage(src.getPage());
        request.setDappAddress(dappAddress);
        request.setTimestamp(System.currentTimeMillis()+"");
        String signMessage = getUserSignature(request,signPrivkey);
        request.setUserSignature(signMessage);
        return request;
    }

    public abstract String getFreeDriveDataString(R src) throws Exception;
    public abstract String getFreeDriveMetaDataString(R src) throws Exception;

    private <T> T send(Object params, String url,Class<T> resultType)throws Exception{
        String json = toJson(params);
        HttpPost post = HttpClientComponent.getPostForJson(url,json);
        String res = HttpClientComponent.send(post);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(res,resultType);
    }


    public String getUserSignature(FreeDrivePutRequest request,String privKey) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append(FreeDriveInterfaceEnum.PUT.getName())
                .append(request.getUserAddress())
                .append(request.getDappAddress())
                .append(request.getMetadata())
                .append(request.getData())
                .append(request.getDriveId())
                .append(request.getTimestamp());
        String hex = Sha256Util.getSHA256(sb.toString());
        return fchdClient.signMessageWithPrivkey(privKey,hex);
    }

    /**
     *
     * @param request
     * @param privKey dapp的privkey
     * @return
     * @throws Exception
     */
    public String getUserSignature(FreeDriveGetRequest request,String privKey) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append(FreeDriveInterfaceEnum.GET.getName())
                .append(request.getDappAddress())
                .append(request.getDriveId())
                .append(request.getPrev())
                .append(request.getNext())
                .append(request.getTimestamp());
        String hex = Sha256Util.getSHA256(sb.toString());
        return fchdClient.signMessageWithPrivkey(privKey,hex);
    }

    public String getUserSignature(FreeDriveListRequest request,String privKey) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append(FreeDriveInterfaceEnum.LIST.getName())
                .append(request.getProtocol())
                .append(request.getAddr())
                .append(request.getDappAddress())
                .append(request.getPage())
                .append(request.getDetail())
                .append(request.getTimestamp());
        String hex = Sha256Util.getSHA256(sb.toString());
        return fchdClient.signMessageWithPrivkey(privKey,hex);
    }

    public String getUserSignature(FreeDriveListDetailRequest request,String privKey) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append(FreeDriveInterfaceEnum.LIST.getName())
                .append(request.getProtocol())
                .append(request.getAddr())
                .append(request.getDappAddress())
                .append(request.getPage())
                .append(request.getDetail())
                .append(request.getTimestamp());
        String hex = Sha256Util.getSHA256(sb.toString());
        return fchdClient.signMessageWithPrivkey(privKey,hex);
    }

    public String getUserSignature(FreeDriveGetDriveInfoRequest request,String privKey) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append(FreeDriveInterfaceEnum.GET_DRIVE_INFO.getName())
                .append(request.getDappAddress())
                .append(request.getDriveId())
                .append(request.getTimestamp());
        String hex = Sha256Util.getSHA256(sb.toString());
        return fchdClient.signMessageWithPrivkey(privKey,hex);
    }

    public String getUserSignature(FreeDriveAuthRequest request,String privKey) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append(FreeDriveInterfaceEnum.AUTH.getName())
                .append(request.getUserAddress())
                .append(request.getDappAddress())
                .append(request.getDriveId())
                .append(String.join("", request.getAdmin()))
                .append(String.join("", request.getMember()))
                .append(request.getTimestamp());
        String hex = Sha256Util.getSHA256(sb.toString());
        return fchdClient.signMessageWithPrivkey(privKey,hex);
    }
}
