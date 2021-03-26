package org.freecash.component;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.freecash.config.FreeDriveConfig;
import org.freecash.config.RestTemplateConfig;
import org.freecash.web.dto.*;
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

    public PutResponse put(PutRequest request) {
        FileSystemResource resource = new FileSystemResource(request.getFile());
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        param.add("fileName", request.getFileName());

        HttpHeaders headers = new HttpHeaders();
        addHeader(headers);

        return restTemplate.postForObject(freeDriveConfig.getFreeDrive().getPut(), new HttpEntity<>(param,headers), PutResponse.class);
    }

    public GetResponse get(GetRequest request) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("hash", request.getHash());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        addHeader(headers);

        String url = getUrl(freeDriveConfig.getFreeDrive().getGet(),params);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, new HttpEntity<>(headers),byte[].class,params);
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

    public CheckResponse check(CheckRequest request) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("hash", request.getHash());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        addHeader(headers);

        String url = getUrl(freeDriveConfig.getFreeDrive().getCheck(),params);
        ResponseEntity<CheckResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(headers),CheckResponse.class,params);
        return responseEntity.getBody();
    }

    private String getUrl(String url, Map<String,Object> params){
        StringBuilder sb = new StringBuilder(url);
        sb.append("?1=1");
        params.keySet().forEach(key-> sb.append("&").append(key).append("={").append(key).append("}"));
        return sb.toString();
    }
    private void addHeader(HttpHeaders header){

        String token = String.format("Bearer %s",getToken());
        header.add("Authorization",token);
    }

    private String getToken(){
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJOYW1lIjoiZmNoIiwiVXNlckNvZGUiOiIxMDAwIn0.yDriBB2PrPfvfkcXyxG97k-wkKmPITYzUQnElIjDjB8";
    }
}
