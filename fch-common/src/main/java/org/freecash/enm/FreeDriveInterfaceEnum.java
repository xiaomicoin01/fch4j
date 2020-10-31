package org.freecash.enm;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FreeDrive接口名称
 */
@Getter
@AllArgsConstructor
public enum FreeDriveInterfaceEnum {
    PUT("/api/put"),
    GET("/api/get"),
    LIST("/api/list"),
    GET_DRIVE_INFO("/api/get_drive_info"),
    AUTH("/api/auth"),
    //下面展示没用
    UPDATE("/api/update"),
    GET_TX_HISTORY("/api/get_tx_history"),
    GET_AUTH_INFO("/api/get_auth_info"),
    SET_AUTH("/api/set_auth"),
    GET_SP_INFO("/api/get_sp_info"),
    TEST_DRIVE_ID("/api/test_drive_id"),
    ARCHIVE("/api/archive"),
    MIGRATE("/api/migrate"),
    CREATE_TX("/api/create_tx"),
    PROOF_EXIST("/api/proof_exist");

    private String name;
}
