package org.freecash.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @ConditionalOnMissingBean(RestTemplate.class)
    @Bean
    public RestTemplate createTemplate(){
        RestTemplate template = new RestTemplate();
        return template;
    }
}

