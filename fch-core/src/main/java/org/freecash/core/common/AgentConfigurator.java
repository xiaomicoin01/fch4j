package org.freecash.core.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.freecash.core.NodeProperties;

/**An abstract superclass containing the core functionality required for constructing 
 * &amp; configuring new <i>freecash</i> consumer instances (<i>i.e.</i> {@code fchdClient}
 * (JSON-RPC API), {@code fchdDaemon} ('callback-via-shell-command' API) etc.).*/
public abstract class AgentConfigurator {
	
	private static final Logger LOG = LoggerFactory.getLogger(AgentConfigurator.class);
	
	@Getter
	private Properties nodeConfig;
	
	
	public abstract Set<NodeProperties> getRequiredProperties();
	
	public Properties checkNodeConfig(Properties nodeConfig) {
		for (NodeProperties property : getRequiredProperties()) {
			if (nodeConfig.getProperty(property.getKey()) == null) {
				LOG.warn("-- checkNodeConfig(..): node property '{}' not set; reverting to "
						+ "default value '{}'", property.getKey(), property.getDefaultValue());
				nodeConfig.setProperty(property.getKey(), property.getDefaultValue());
			}
		}
		this.nodeConfig = nodeConfig;
		return nodeConfig;
	}
	
	public Properties toProperties(Object... values) {
		Properties properties = new Properties();
		List<NodeProperties> requiredProperties = new ArrayList<NodeProperties>(
				getRequiredProperties());
		for (int i = 0; i < requiredProperties.size(); i++) {
			if (values[i] != null) {
				String key = requiredProperties.get(i).getKey();
				properties.setProperty(key, values[i].toString());
			}
		}
		return properties;
	}
}