package org.freecash.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author wanglint
 * @date 2020/5/17 13:25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetWork extends Entity {
    private String name;
    private Boolean limited;
    private Boolean reachable;
    private String proxy;
    @JsonProperty("proxy_randomize_credentials")
    private Boolean proxyRandomizeCredentials;
}
