package org.freecash.core.http.client;

import org.freecash.core.http.HttpLayerException;

public interface SimpleHttpClient {
	
	String execute(String reqMethod, String reqPayload) throws HttpLayerException;
	
	void close();
}