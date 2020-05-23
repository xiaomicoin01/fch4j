package org.freecash;

import org.freecash.core.client.FchdClient;
import org.freecash.api.VerboseFchdClientImpl;
import org.freecash.util.ResourceUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * @date 2019/10/14
 * @author wanglint
 */
//@SpringBootApplication
public class RpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcApplication.class, args);
    }

    @Resource
    private ResourceUtils resourceUtils;

    @Bean
    public FchdClient getClient() throws Exception{
        CloseableHttpClient httpProvider = resourceUtils.getHttpProvider();
        Properties nodeConfig = resourceUtils.getNodeConfig();
        FchdClient client = new VerboseFchdClientImpl(httpProvider, nodeConfig);
        return client;
    }
}
