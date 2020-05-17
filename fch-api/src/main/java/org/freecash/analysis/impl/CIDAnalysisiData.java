package org.freecash.analysis.impl;

import lombok.extern.log4j.Log4j2;
import org.freecash.analysis.IAnalysisData;
import org.springframework.stereotype.Component;

/**
 * @author wanglint
 * @date 2020/5/17 15:01
 **/
@Component
@Log4j2
public class CIDAnalysisiData implements IAnalysisData {
    /**
     * CID协议类型
     *
     * @return
     */
    @Override
    public String getType() {
        return "FEIP";
    }

    /**
     * 协议处理类
     *
     * @param protocolValue 协议内容
     */
    @Override
    public void analysis(String protocolValue) {
        log.debug("CID协议内容：{}",protocolValue);
    }
}
