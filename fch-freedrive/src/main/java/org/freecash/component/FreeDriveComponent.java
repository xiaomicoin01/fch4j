package org.freecash.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.freecash.config.FreeDriveConfig;
import org.freecash.config.RestTemplateConfig;
import org.freecash.web.dto.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@Import(RestTemplateConfig.class)
@RequiredArgsConstructor
public class FreeDriveComponent {
    private final FreeDriveConfig freeDriveConfig;
    private final RestTemplate restTemplate;
    public PutResponse put(PutRequest request) throws Exception{
        FileSystemResource resource = new FileSystemResource(request.getFile());
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        param.add("fileName", request.getFileName());

        HttpHeaders headers = new HttpHeaders();
        addHeader(headers);

        HttpEntity<Map> entity = new HttpEntity<>(param,headers);

        return restTemplate.postForObject(freeDriveConfig.getFreeDrive().getPut(), entity, PutResponse.class);
    }

    public GetResponse get(GetRequest request) throws Exception{
//        Map<String,Object> params = Maps.newHashMap();
//        params.put("hash", request.getHash());
//        ObjectMapper mapper = new ObjectMapper();
//        String str = mapper.writeValueAsString(params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        addHeader(headers);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(freeDriveConfig.getFreeDrive().getGet()+"?hash="+request.getHash(),
                HttpMethod.GET, entity,byte[].class);
        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return GetResponse.builder()
                    .code(0)
                    .msg("")
                    .data(GetResponse.Data.builder().file(responseEntity.getBody()).build())
                    .build();
        }else{
            return GetResponse.builder()
                    .code(responseEntity.getStatusCode().value())
                    .msg(responseEntity.getStatusCode().getReasonPhrase())
                    .build();
        }
    }

    public CheckResponse check(CheckRequest request) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        addHeader(headers);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<CheckResponse> responseEntity = restTemplate.exchange(freeDriveConfig.getFreeDrive().getCheck()+"?hash="+request.getHash(),
                HttpMethod.GET, entity,CheckResponse.class);
        return responseEntity.getBody();
    }

    private void addHeader(HttpHeaders header){

        String token = String.format("Bearer %s",getToken());
        header.add("Authorization",token);
    }

    private String getToken(){
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJOYW1lIjoiZmNoIiwiVXNlckNvZGUiOiIxMDAwIn0.yDriBB2PrPfvfkcXyxG97k-wkKmPITYzUQnElIjDjB8";
    }
}
