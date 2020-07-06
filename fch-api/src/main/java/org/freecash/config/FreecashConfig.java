package org.freecash.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanglint
 * @date 2020/7/6 18:52
 **/
@Component
@Data
@Configuration
@ConfigurationProperties(prefix = "freecash")
public class FreecashConfig {
    private BigDecimal amount;
    private String address;
    private BigDecimal fee;
    private Freedrive freedrive;
    private List<String> protocols;

    @Data
    static class Freedrive{
        String put;
        String update;
        String get;
        String getDriveId;
    }
}
