package org.freecash.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.HttpPost;
import org.freecash.dto.*;
import org.freecash.utils.HttpClientComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanglint
 * @date 2020/5/24 13:28
 **/
@Component
@Log4j2
public class FreeDriveComponent {
    @Value("${freecash.freedrive.get}")
    private String getUrl;


    public FreeDriveGetResponse put(FreeDriveGetRequest request) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("fch_addr",request.getAddress());
        map.put("drive_id",request.getDriveId());
        HttpPost post = HttpClientComponent.getPostForMap(getUrl,map);
        String res = HttpClientComponent.send(post);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(res,FreeDriveGetResponse.class);
    }

    private <T> T send(Object params, String url,Class<T> resultType)throws Exception{
        String json = toJson(params);
        HttpPost post = HttpClientComponent.getPostForJson(url,json);
        String res = HttpClientComponent.send(post);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(res,resultType);
    }
    private String toJson(Object obj) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();

        StringWriter sw = new StringWriter();
        objectMapper.writeValue(sw, obj);
        return sw.toString();
    }
}
