package org.freecash.api;

import org.freecash.core.client.FchdClient;
import org.freecash.core.domain.Block;
import org.freecash.core.domain.RawTransaction;
import org.freecash.core.domain.Transaction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wanglint
 * @date 2019/10/14 21:33
 **/
@Component
public class BlockApi {
    @Resource
    private FchdClient client;

    public int getBlockHeight() throws Exception{
        return client.getBlockCount();
    }

    public String getBlockHash(int height) throws Exception{
        return client.getBlockHash(height);
    }

    public Block getBlock(String blockHash) throws Exception{
        return client.getBlock(blockHash);
    }

    public Transaction getTransaction(String txId) throws  Exception{
        return  client.getTransaction(txId);
    }

    public RawTransaction getRawTransaction(String txId,boolean i) throws  Exception{
        return (RawTransaction)client.getRawTransaction(txId,i);
    }
}
