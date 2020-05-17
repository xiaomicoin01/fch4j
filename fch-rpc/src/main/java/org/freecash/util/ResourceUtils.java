package org.freecash.util;

import java.io.IOException;
import java.util.Properties;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import org.freecash.core.FreecashException;
import org.freecash.core.CommunicationException;
import org.freecash.core.client.FchdClient;
import org.freecash.core.client.FchdClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceUtils {
    @Value("${rpc.protocol}")
	private String protocol;
    @Value("${rpc.host}")
	private String host;
    @Value("${rpc.port}")
	private String port;
    @Value("${rpc.user}")
	private String user;
    @Value("${rpc.password}")
	private String password;
    @Value("${rpc.scheme}")
	private String scheme;

	public CloseableHttpClient getHttpProvider() {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpProvider = HttpClients.custom().setConnectionManager(connManager)
				.build();
		return httpProvider;
	}
	
	public FchdClient getfchdProvider() throws FreecashException, CommunicationException,
			IOException {
		return new FchdClientImpl(getHttpProvider(), getNodeConfig());
	}
	
	public Properties getNodeConfig() throws IOException {
		Properties nodeConfig = new Properties();

		nodeConfig.setProperty("node.freecash.rpc.protocol",protocol);


		nodeConfig.setProperty("node.freecash.rpc.host",host);
		nodeConfig.setProperty("node.freecash.rpc.port",port);
		nodeConfig.setProperty("node.freecash.rpc.user",user);
		nodeConfig.setProperty("node.freecash.rpc.password",password);
		nodeConfig.setProperty("node.freecash.http.auth_scheme",scheme);

		nodeConfig.setProperty("node.freecash.notification.alert.port","5158");
		nodeConfig.setProperty("node.freecash.notification.block.port","5159");
		nodeConfig.setProperty("node.freecash.notification.wallet.port","5160");

		return nodeConfig;
	}
}