package org.freecash.service;

import javax.annotation.Resource;

import org.freecash.constant.ConstantKey;
import org.freecash.dao.IBlockInfoDao;
import org.freecash.domain.BlockInfo;
import org.freecash.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * @author wanglint
 * @date 2019/10/14
 */
@Service
public class BlockInfoService {

    @Resource
    private IBlockInfoDao blockInfoDao;

    public BlockInfo getBlockSyncHeight() {
        BlockInfo info = blockInfoDao.getByKey(ConstantKey.BLOCK_SYNC_POTION).orElse(
                BlockInfo.builder().key(ConstantKey.BLOCK_SYNC_POTION).value("0").desp("同步区块高度").build());
        return info;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveBlock(BlockInfo block) {
        blockInfoDao.save(block);

    }
}
