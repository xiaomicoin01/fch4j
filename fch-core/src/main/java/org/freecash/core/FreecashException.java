package org.freecash.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**This exception is thrown when a non-null error object (originating from <i>freecash</i>) is
 *detected in a returning JSON-RPC response.*/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class FreecashException extends Exception {

	private static final long serialVersionUID = 1L;

	private int code;

	
	public FreecashException(int code, String message) {
		super(message); 
		this.code = code;
	}
}