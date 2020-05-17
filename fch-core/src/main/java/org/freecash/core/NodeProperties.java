package org.freecash.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**An enumeration specifying the <i>freecash</i> node properties currently recognized by
 * fchd-cli4j.**/
@Getter
@ToString
@AllArgsConstructor
public enum NodeProperties {
	
    RPC_PROTOCOL("node.freecash.rpc.protocol", "http"),
    RPC_HOST("node.freecash.rpc.host", "127.0.0.1"),
    RPC_PORT("node.freecash.rpc.port", "8332"),
    RPC_USER("node.freecash.rpc.user", "user"),
    RPC_PASSWORD("node.freecash.rpc.password", ""),
    HTTP_AUTH_SCHEME("node.freecash.http.auth_scheme", "Basic"),
    ALERT_PORT("node.freecash.notification.alert.port", "5158"),
    BLOCK_PORT("node.freecash.notification.block.port", "5159"),
    WALLET_PORT("node.freecash.notification.wallet.port", "5160");
	
    private final String key;
    private final String defaultValue;
}