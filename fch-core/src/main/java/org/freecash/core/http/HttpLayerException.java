package org.freecash.core.http;

import org.freecash.core.common.Errors;
import org.freecash.core.CommunicationException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**This exception is thrown to indicate a HTTP-specific error in the underlying communication
 * infrastructure.*/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class HttpLayerException extends CommunicationException {

	private static final long serialVersionUID = 1L;

	
	public HttpLayerException(Errors error) {
		super(error); 
	}

	public HttpLayerException(Errors error, String additionalMsg) {
		super(error, additionalMsg);
	}
	
	public HttpLayerException(Errors error, Exception cause) {
		super(error, cause);
	}
}