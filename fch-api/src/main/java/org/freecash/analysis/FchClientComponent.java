package org.freecash.analysis;

import org.apache.http.impl.client.CloseableHttpClient;
import org.freecash.controller.api.VerboseFchdClientImpl;
import org.freecash.core.client.FchdClient;
import org.freecash.util.ResourceUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * @author wanglint
 * @date 2020/5/21 9:37
 **/
@Component
public class FchClientComponent {

    @Resource
    private ResourceUtils resourceUtils;

    @Bean
    public FchdClient createFchClient() throws Exception{
        CloseableHttpClient httpProvider = resourceUtils.getHttpProvider();
        Properties nodeConfig = resourceUtils.getNodeConfig();
        FchdClient client = new VerboseFchdClientImpl(httpProvider, nodeConfig);
        return client;
    }
}
