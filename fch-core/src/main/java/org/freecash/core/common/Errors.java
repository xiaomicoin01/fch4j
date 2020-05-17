package org.freecash.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**An enumeration specifying the error codes &amp; messages used by fchd-cli4j.*/
@Getter
@ToString
@AllArgsConstructor
public enum Errors {

	ARGS_COUNT_UNEVEN(1001001, "Expected the argument count to be 'even', but was 'uneven' instead."),
	ARGS_COUNT_UNEQUAL(1001002, "Expected the argument count to be 'equal', but was 'unequal' instead."),
	ARGS_VALUE_NEGATIVE(1001003, "Expected the argument value to be positive (>=0), but was negative (<0) "
			+ "instead."),
	ARGS_NULL(1001004, "Expected a non-null argument, but got 'null' instead."),
	ARGS_CONTAIN_NULL(1001005, "Expected only non-null arguments, but got >0 'null' instead."),
	ARGS_HTTP_METHOD_UNSUPPORTED(1001006, "Expected the argument to be a valid HTTP method, but was "
			+ "invalid/unsupported instead."),
	ARGS_HTTP_AUTHSCHEME_UNSUPPORTED(1001007, "Expected the argument to be a valid HTTP auth scheme, but "
			+ "was invalid/unsupported instead."),
	ARGS_fchd_CHAINTYPE_UNSUPPORTED(1001008, "Expected the argument to be a valid 'freecash' chain type, but "
			+ "was invalid/unsupported instead."),
	ARGS_fchd_PAYMENTCATEGORY_UNSUPPORTED(1001009, "Expected the argument to be a valid 'freecash' payment "
			+ "category, but was invalid/unsupported instead."),
	ARGS_fchd_SCRIPTTYPE_UNSUPPORTED(1001010, "Expected the argument to be a valid 'freecash' script type, "
			+ "but was invalid/unsupported instead."),
	ARGS_fchd_SIGHASHTYPE_UNSUPPORTED(1001011, "Expected the argument to be a valid 'freecash' signature hash "
			+ "type, but was invalid/unsupported instead."),
	ARGS_fchd_NOTIFICATION_UNSUPPORTED(1001012, "Expected the argument to be a valid 'freecash' notification "
			+ "type, but was invalid/unsupported instead."),
	ARGS_fchd_PROVIDER_NULL(1001013, "Expected a preconfigured 'freecash' JSON-RPC API provider, but got "
			+ "'null' instead."),
	REQUEST_HTTP_FAULT(1002001, "Request execution failed due an error in the HTTP protocol."),
	RESPONSE_HTTP_CLIENT_FAULT(1003001, "The server responded with a non-OK (4xx) HTTP status code. "
			+ "Status line: "),
	RESPONSE_HTTP_SERVER_FAULT(1003002, "The server responded with a non-OK (5xx) HTTP status code. "
			+ "Status line: "),
	RESPONSE_JSONRPC_NULL(1003003, "Expected a non-null JSON-RPC response object, but got 'null' instead."),
	RESPONSE_JSONRPC_NULL_ID(1003004, "Expected a non-null JSON-RPC response id, but got 'null' instead."),
	RESPONSE_JSONRPC_UNEQUAL_IDS(1003005, "Expected the JSON-RPC request and response ids to be 'equal', "
			+ "but were 'unequal' instead."),
	IO_STREAM_UNCLOSED(1004001, "Unable to close the specified stream."),
	IO_SOCKET_UNINITIALIZED(1004002, "Unable to open the specified socket."),
	IO_SERVERSOCKET_UNINITIALIZED(1004003, "Unable to open the specified server socket."),
	IO_UNKNOWN(1004004, "The operation failed due to an unknown IO exception."),
	PARSE_URI_FAILED(1005001, "Unable to parse the specified URI."),
	PARSE_JSON_UNKNOWN(1005002, "An unknown exception occurred while parsing/generating JSON content."),
	PARSE_JSON_MALFORMED(1005003, "Unable to parse the specified JSON content (malformed syntax detected)."),
	MAP_JSON_UNKNOWN(1006001, "An unknown exception ocurred while mapping the JSON content.");
	
	private final int code;
	private final String message;
	
	
	public String getDescription() {
		return String.format("Error #%s: %s", code, message);
	}
}