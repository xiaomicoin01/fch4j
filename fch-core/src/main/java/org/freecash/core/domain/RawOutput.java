package org.freecash.core.domain;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.freecash.core.common.Defaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawOutput extends Entity {
	
	@Setter(AccessLevel.NONE)
	private BigDecimal value;
	private Integer n;
	private PubKeyScript scriptPubKey;
	
	
	public void setValue(BigDecimal value) {
		this.value = value.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}
}