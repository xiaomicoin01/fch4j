package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wanglint
 * @date 2020/5/29 8:59
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProtocolHeader {
    private String protocolName;
    private String protocolNo;
    private String protocolVersion;


    public static void main(String[] args) {
        ProtocolHeader p1 = new ProtocolHeader("1","2","3");
        ProtocolHeader p2 = new ProtocolHeader("1","2","");
        System.out.println(p1.equals(p2));
        System.out.println(p1.hashCode());
    }
}
