package org.freecash.analysis;

import lombok.*;

/**
 * @author wanglint
 * @date 2020/5/29 8:59
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ProtocolHeader {
    private String protocolName;
    private String protocolNo;
    private String protocolVersion;
    private String protocolType;
    private String fileHash;


    public ProtocolHeader(String protocolName, String protocolNo, String protocolVersion) {
        this.protocolName = protocolName;
        this.protocolNo = protocolNo;
        this.protocolVersion = protocolVersion;
    }
}