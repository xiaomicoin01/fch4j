package org.freecash.component;

import java.io.Serializable;

/**
 * @author wanglint
 * @date 2020/7/2 15:39
 **/
public interface FreeDriveEntity extends Serializable {
    String getId();
    void setDriveId(String driveId);
    String getDriveId();
    void setDataHash(String dataHash);
    void setAction(String action);

    default String getProtocol(){
        return "";
    }

    default int getPage(){
        return 0;
    }

    default String getDetail(){
        return "";
    }
}
