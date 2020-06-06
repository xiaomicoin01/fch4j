package org.freecash.core.jsonrpc.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.freecash.core.jsonrpc.domain.JsonRpcError;
import org.freecash.core.jsonrpc.domain.JsonRpcRequest;
import org.freecash.core.jsonrpc.domain.JsonRpcResponse;

import org.freecash.core.FreecashException;
import org.freecash.core.CommunicationException;
import org.freecash.core.common.Defaults;
import org.freecash.core.common.Errors;
import org.freecash.core.http.HttpConstants;
import org.freecash.core.http.client.SimpleHttpClient;
import org.freecash.core.http.client.SimpleHttpClientImpl;
import org.freecash.core.jsonrpc.JsonMapper;
import org.freecash.core.jsonrpc.JsonPrimitiveParser;
import org.freecash.core.jsonrpc.JsonRpcLayerException;
@Slf4j
public class JsonRpcClientImpl implements JsonRpcClient {
	private SimpleHttpClient httpClient;
	private JsonPrimitiveParser parser;
	private JsonMapper mapper;


	public JsonRpcClientImpl(CloseableHttpClient httpProvider, Properties nodeConfig) {
		log.info("** JsonRpcClientImpl(): initiating the JSON-RPC communication layer");
		httpClient = new SimpleHttpClientImpl(httpProvider, nodeConfig);
		parser = new JsonPrimitiveParser();
		mapper = new JsonMapper();
	}

	@Override
	public String execute(String method) throws FreecashException, CommunicationException {
		return execute(method, null);
	}

	@Override
	public <T> String execute(String method, T param) throws FreecashException,
			CommunicationException {
		List<T> params = new ArrayList<T>();
		params.add(param);
		return execute(method, params);
	}

	@Override
	public <T> String execute(String method, List<T> params) throws FreecashException,
			CommunicationException {
//		log.info(">> execute(..): invoking 'freecash' JSON-RPC API command '{}' with params: '{}'",
//				method, params);
		String requestUuid = getNewUuid();
		JsonRpcRequest<T> request = getNewRequest(method, params, requestUuid);
		String requestJson = mapper.mapToJson(request);
//		log.debug("-- execute(..): sending JSON-RPC request as (raw): '{}'", requestJson.trim());
		String responseJson = httpClient.execute(HttpConstants.REQ_METHOD_POST, requestJson);
//		log.debug("-- execute(..): received JSON-RPC response as (raw): '{}'", responseJson.trim());
		JsonRpcResponse response = mapper.mapToEntity(responseJson, JsonRpcResponse.class);
		response = verifyResponse(request, response);
		response = checkResponse(response);
//		log.info("<< execute(..): returning result for 'freecash' API command '{}' as: '{}'",
//				method, response.getResult());
		return response.getResult();
	}

	@Override
	public JsonPrimitiveParser getParser() {
		return parser;
	}

	@Override
	public JsonMapper getMapper() {
		return mapper;
	}

	@Override
	public void close() {
		httpClient.close();
	}
	
	private <T> JsonRpcRequest<T> getNewRequest(String method, List<T> params, String id) {
		JsonRpcRequest<T> rpcRequest = new JsonRpcRequest<T>();
		rpcRequest.setJsonrpc(Defaults.JSON_RPC_VERSION);
		rpcRequest.setMethod(method);
		rpcRequest.setParams(params);
		rpcRequest.setId(id);
		return rpcRequest;
	}

	private JsonRpcResponse getNewResponse(String result, JsonRpcError error, String id) {
		JsonRpcResponse rpcResponse = new JsonRpcResponse();
		rpcResponse.setJsonrpc(Defaults.JSON_RPC_VERSION);
		rpcResponse.setResult(result);
		rpcResponse.setError(error);
		rpcResponse.setId(id);
		return rpcResponse;
	}

	private String getNewUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	private <T> JsonRpcResponse verifyResponse(JsonRpcRequest<T> request, JsonRpcResponse response) 
			throws JsonRpcLayerException {
//		log.debug(">> verifyResponse(..): verifying JSON-RPC response for basic protocol conformance");
		if(response == null) {
			throw new JsonRpcLayerException(Errors.RESPONSE_JSONRPC_NULL);
		}
		if(response.getId() == null) {
			throw new JsonRpcLayerException(Errors.RESPONSE_JSONRPC_NULL_ID);
		}
		if(!response.getId().equals(request.getId())) {
			throw new JsonRpcLayerException(Errors.RESPONSE_JSONRPC_UNEQUAL_IDS);
		}
		if((response.getJsonrpc() != null) && (!response.getJsonrpc().equals(
				Defaults.JSON_RPC_VERSION))) {
			log.warn("-- verifyResponse(..): JSON-RPC version mismatch - client optimized for '{}'"
					+ ", node responded in '{}'", Defaults.JSON_RPC_VERSION, response.getJsonrpc());
		}
		return response;
	}

	private <T> JsonRpcResponse checkResponse(JsonRpcResponse response) throws FreecashException {
//		log.debug(">> checkResponse(..): checking JSON-RPC response for nested 'freecash' errors");
		if(!(response.getError() == null)) {
			JsonRpcError freecashError = response.getError();
			throw new FreecashException(freecashError.getCode(), String.format("Error #%s: %s",
					freecashError.getCode(), freecashError.getMessage()));
		}
		return response;
	}
}