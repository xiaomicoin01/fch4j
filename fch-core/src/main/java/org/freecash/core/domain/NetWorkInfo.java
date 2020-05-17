package org.freecash.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetWorkInfo extends Entity {
	

	private Integer version;
	private String subversion;
	@JsonProperty("protocolversion")
	private Integer protocolVersion;
	@JsonProperty("localservices")
	private String localServices;
	@JsonProperty("localrelay")
	private Boolean localRelay;

	@JsonProperty("timeoffset")
	private Integer timeOffset;

	@JsonProperty("networkactive")
	private Boolean netWorkActive;

	private Integer connections;

	private String warnings;

	private List<NetWork> networks;
}