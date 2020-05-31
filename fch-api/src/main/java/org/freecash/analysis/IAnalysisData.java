package org.freecash.analysis;

import org.freecash.domain.ProtocolHeader;

import java.util.List;

/**
 * @author wanglint
 * @date 2020/5/17 14:50
 **/
public interface IAnalysisData {
    /**
     * 协议类型
     * @return
     */
    List<ProtocolHeader> getType();

    /**
     * 协议处理类
     * @param protocolValue 协议内容
     */
    void analysis(String protocolValue, String txId)throws Exception;
}
