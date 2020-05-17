package org.freecash.core.jsonrpc.client;

import java.util.List;

import org.freecash.core.FreecashException;
import org.freecash.core.CommunicationException;
import org.freecash.core.jsonrpc.JsonMapper;
import org.freecash.core.jsonrpc.JsonPrimitiveParser;

public interface JsonRpcClient {

	String execute(String method) throws FreecashException, CommunicationException;
	
	<T> String execute(String method, T param) throws FreecashException, CommunicationException;
	
	<T> String execute(String method, List<T> params) throws FreecashException,
			CommunicationException;
	
	JsonPrimitiveParser getParser();

	JsonMapper getMapper();
	
	void close();
}