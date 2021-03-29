package org.freecash;

import org.freecash.core.client.FchdClient;
import org.freecash.controller.api.VerboseFchdClientImpl;
import org.freecash.util.ResourceUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.SpringApplication;
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

//    @Resource
//    private FchdClient fchdClient;
//
//    @Bean
//    public CommandLineRunner runner(){
//        return args -> {
//            List<OutputOverview> outs = Arrays.asList(
//              new OutputOverview("7ec66ea4e7c07a30b2919cd6c9a51062d75b563bde94c5dc13fa0ee87538dca7",0)
//            );
//
//            Map<String,Object> map11 = new HashMap<>();
//            map11.put("FMp145T6LEUAieMCSbiuDAKuZ7bsKVw9re", new BigDecimal("0.01"));
//            Map<String,Object> map12 = new HashMap<>();
//            map12.put("FBCnVJ4hK99WcTJCb9DjK1zgufbageGTDq",new BigDecimal("0.10"));
//
//            Map<String,Object> map2 = new HashMap<>();
//            map2.put("data", HexStringUtil.stringToHexString("123"));
//
//            List<Map<String,Object>> params = new ArrayList<>();
//            params.add(map11);
//            params.add(map12);
//            params.add(map2);
//            String hex = fchdClient.createRawTransaction(outs,params);
//            System.out.println(hex);
//        };
//    }
}
