package org.freecash.core.client;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import lombok.Getter;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.freecash.core.common.AgentConfigurator;
import org.freecash.core.common.Defaults;
import org.freecash.core.common.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.freecash.core.NodeProperties;
import org.freecash.core.domain.Block;
import org.freecash.core.util.CollectionUtils;
import org.freecash.core.util.StringUtils;

public class ClientConfigurator extends AgentConfigurator {

	private static final Logger LOG = LoggerFactory.getLogger(ClientConfigurator.class);
	
	@Getter
	private String nodeVersion;
	
	
	@Override
	public Set<NodeProperties> getRequiredProperties() {
		return EnumSet.of(NodeProperties.RPC_PROTOCOL, NodeProperties.RPC_HOST, 
				NodeProperties.RPC_PORT, NodeProperties.RPC_USER, NodeProperties.RPC_PASSWORD, 
				NodeProperties.HTTP_AUTH_SCHEME);
	}
	
	public CloseableHttpClient checkHttpProvider(CloseableHttpClient httpProvider) {
		if (httpProvider == null) {
			LOG.warn("-- checkHttpProvider(..): no preconfigured HTTP provider detected; reverting "
					+ "to library default settings");
			httpProvider = getDefaultHttpProvider();
		}
		return httpProvider;
	}
	
	public String checkNodeVersion(Integer encodedVersion) {
		nodeVersion = decodeNodeVersion(encodedVersion);
		for (String supportedVersion : Defaults.NODE_VERSIONS) {
			if (nodeVersion.equals(supportedVersion)) {
				return nodeVersion;
			}
		}
		LOG.warn("-- checkNodeVersion(..): server version mismatch - client optimized for '{}'"
				+ ", node responded with '{}'", Defaults.NODE_VERSIONS, nodeVersion);
		return nodeVersion;
	}

	public boolean checkNodeHealth(Block bestBlock) {
		long currentTime = System.currentTimeMillis() / 1000;
		if ((currentTime - bestBlock.getTime()) > TimeUnit.HOURS.toSeconds(6)) {
			LOG.warn("-- checkNodeHealth(..): last available block was mined >{} hours ago; please "
					+ "check your network connection", ((currentTime - bestBlock.getTime()) / 3600));
			return false;
		}
		return true;
	}

	private CloseableHttpClient getDefaultHttpProvider() {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpProvider = HttpClients.custom().setConnectionManager(connManager)
				.build();
		return httpProvider;
	}
	
	private String decodeNodeVersion(Integer extendedFormat) {
		if(extendedFormat == null) {
			throw new IllegalArgumentException(Errors.ARGS_NULL.getDescription());
		}
		String canonicalFormat = StringUtils.pad(extendedFormat.toString(), 8, '0', true);
		List<String> splittedFormat = StringUtils.split(canonicalFormat, 2);
		splittedFormat = StringUtils.replaceAll(splittedFormat, "^0", "");
		List<Object> separatedFormat = CollectionUtils.mergeInterlaced(splittedFormat, 
				CollectionUtils.duplicate(".", 4));
		separatedFormat.remove(separatedFormat.size() - 1);
		if (separatedFormat.get(separatedFormat.size() - 1).equals("0")) {
			separatedFormat.subList(separatedFormat.size() - 2, separatedFormat.size()).clear();
		}
		return StringUtils.join(separatedFormat);    
	}
}