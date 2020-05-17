package org.freecash.service;

import javax.annotation.Resource;

import org.freecash.constant.ConstantKey;
import org.freecash.dao.IBlockInfoDao;
import org.freecash.domain.BlockInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @date 2019/10/14
 * @author wanglint
 */
@Service
public class BlockInfoService {

	@Resource
	private IBlockInfoDao blockInfoDao;
	
	public int getBlockSyncHeight() {
		BlockInfo info =  blockInfoDao.getByKey(ConstantKey.BLOCK_SYNC_POTION).orElse(new BlockInfo(ConstantKey.BLOCK_SYNC_POTION,"0"));
		return Integer.parseInt(info.getValue());
	}

	@Transactional(rollbackFor = Exception.class)
	public void saveBlock(BlockInfo block) {
        blockInfoDao.save(block);
		
	}
}
