package org.freecash.math.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wanglint
 * @date 2020/7/13 21:36
 **/
@Configuration
@ConfigurationProperties(prefix = "index")
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexConfig {
    private boolean init;
    private String url;
    private String token;
    private String beginDate;
    private List<String> stockCodes;
    private List<String> metricsList;
}
