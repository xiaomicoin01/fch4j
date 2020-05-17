package org.freecash.core.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.impl.client.CloseableHttpClient;
import org.freecash.core.common.DataFormats;
import org.freecash.core.common.Defaults;
import org.freecash.core.jsonrpc.client.JsonRpcClient;
import org.freecash.core.jsonrpc.client.JsonRpcClientImpl;
import org.freecash.core.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.freecash.core.FreecashException;
import org.freecash.core.Commands;
import org.freecash.core.CommunicationException;
import org.freecash.core.util.CollectionUtils;
import org.freecash.core.util.NumberUtils;

public class FchdClientImpl implements FchdClient {
	
	private static final Logger LOG = LoggerFactory.getLogger(FchdClientImpl.class);
	
	private ClientConfigurator configurator;
	private JsonRpcClient rpcClient;
	

	public FchdClientImpl(Properties nodeConfig) throws FreecashException, CommunicationException {
		this(null, nodeConfig);
	}

	public FchdClientImpl(CloseableHttpClient httpProvider, Properties nodeConfig)
			throws FreecashException, CommunicationException {
		initialize();
		rpcClient = new JsonRpcClientImpl(configurator.checkHttpProvider(httpProvider),
				configurator.checkNodeConfig(nodeConfig));
		configurator.checkNodeVersion(getNetWorkInfo().getVersion());
		configurator.checkNodeHealth((Block)getBlock(getBestBlockHash(), true));
	}

	public FchdClientImpl(String rpcUser, String rpcPassword) throws FreecashException,
			CommunicationException {
		this(null, null, rpcUser, rpcPassword);
	}
	
	public FchdClientImpl(CloseableHttpClient httpProvider, String rpcUser, String rpcPassword)
			throws FreecashException, CommunicationException {
		this(httpProvider, null, null, rpcUser, rpcPassword);
	}
	
	public FchdClientImpl(String rpcHost, Integer rpcPort, String rpcUser, String rpcPassword)
			throws FreecashException, CommunicationException {
		this((String)null, rpcHost, rpcPort, rpcUser, rpcPassword);
	}
	
	public FchdClientImpl(CloseableHttpClient httpProvider, String rpcHost, Integer rpcPort,
			String rpcUser, String rpcPassword) throws FreecashException, CommunicationException {
		this(httpProvider, null, rpcHost, rpcPort, rpcUser, rpcPassword);
	}
	
	public FchdClientImpl(String rpcProtocol, String rpcHost, Integer rpcPort, String rpcUser,
			String rpcPassword) throws FreecashException, CommunicationException {
		this(rpcProtocol, rpcHost, rpcPort, rpcUser, rpcPassword, null);
	}
	
	public FchdClientImpl(CloseableHttpClient httpProvider, String rpcProtocol, String rpcHost,
			Integer rpcPort, String rpcUser, String rpcPassword) throws FreecashException,
			CommunicationException {
		this(httpProvider, rpcProtocol, rpcHost, rpcPort, rpcUser, rpcPassword, null);
	}

	public FchdClientImpl(String rpcProtocol, String rpcHost, Integer rpcPort, String rpcUser,
			String rpcPassword, String httpAuthScheme) throws FreecashException,
			CommunicationException {
		this(null, rpcProtocol, rpcHost, rpcPort, rpcUser, rpcPassword, httpAuthScheme);
	}

	public FchdClientImpl(CloseableHttpClient httpProvider, String rpcProtocol, String rpcHost,
			Integer rpcPort, String rpcUser, String rpcPassword, String httpAuthScheme) 
			throws FreecashException, CommunicationException {
		this(httpProvider, new ClientConfigurator().toProperties(rpcProtocol, rpcHost, rpcPort, 
				rpcUser, rpcPassword, httpAuthScheme));
	}
	
	@Override
	public String addMultiSigAddress(Integer minSignatures, List<String> addresses) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(minSignatures, addresses);
		String multiSigAddressJson = rpcClient.execute(Commands.ADD_MULTI_SIG_ADDRESS.getName(), 
				params);
		String multiSigAddress = rpcClient.getParser().parseString(multiSigAddressJson);
		return multiSigAddress;		
	}

	@Override
	public String addMultiSigAddress(Integer minSignatures, List<String> addresses, 
			String account) throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(minSignatures, addresses, account);
		String multiSigAddressJson = rpcClient.execute(Commands.ADD_MULTI_SIG_ADDRESS.getName(),
				params);
		String multiSigAddress = rpcClient.getParser().parseString(multiSigAddressJson);
		return multiSigAddress;
	}
	
	@Override
	public void backupWallet(String filePath) throws FreecashException, CommunicationException {
		rpcClient.execute(Commands.BACKUP_WALLET.getName(), filePath);
	}
	
	@Override
	public MultiSigAddress createMultiSig(Integer minSignatures, List<String> addresses)
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(minSignatures, addresses);
		String multiSigAddressJson = rpcClient.execute(Commands.CREATE_MULTI_SIG.getName(), params);
		MultiSigAddress multiSigAddress = rpcClient.getMapper().mapToEntity(multiSigAddressJson, 
				MultiSigAddress.class);
		return multiSigAddress;
	}

	@Override
	public String createRawTransaction(List<OutputOverview> outputs, 
			Map<String, BigDecimal> toAddresses) throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(outputs, toAddresses);
		String hexTransactionJson = rpcClient.execute(Commands.CREATE_RAW_TRANSACTION.getName(), 
				params);
		String hexTransaction = rpcClient.getParser().parseString(hexTransactionJson);
		return hexTransaction;
	}

	@Override
	public RawTransactionOverview decodeRawTransaction(String hexTransaction) 
			throws FreecashException, CommunicationException {
		String rawTransactionJson = rpcClient.execute(Commands.DECODE_RAW_TRANSACTION.getName(), 
				hexTransaction);
		RawTransactionOverview rawTransaction = rpcClient.getMapper().mapToEntity(
				rawTransactionJson, RawTransactionOverview.class);
		return rawTransaction;
	}
	
	@Override
	public RedeemScript decodeScript(String hexRedeemScript) throws FreecashException,
			CommunicationException {
		String redeemScriptJson = rpcClient.execute(Commands.DECODE_SCRIPT.getName(), 
				hexRedeemScript);
		RedeemScript redeemScript = rpcClient.getMapper().mapToEntity(redeemScriptJson, 
				RedeemScript.class);
		redeemScript.setHex(hexRedeemScript);
		return redeemScript;
	}
	
	@Override
	public String dumpPrivKey(String address) throws FreecashException, CommunicationException {
		String privateKeyJson = rpcClient.execute(Commands.DUMP_PRIV_KEY.getName(), address);
		String privateKey = rpcClient.getParser().parseString(privateKeyJson);
		return privateKey;
	}
	
	@Override
	public void dumpWallet(String filePath) throws FreecashException, CommunicationException {
		rpcClient.execute(Commands.DUMP_WALLET.getName(), filePath);
	}
	
	@Override
	public String encryptWallet(String passphrase) throws FreecashException,
			CommunicationException {
		String noticeMsgJson = rpcClient.execute(Commands.ENCRYPT_WALLET.getName(), passphrase);
		String noticeMsg = rpcClient.getParser().parseString(noticeMsgJson);
		return noticeMsg;
	}
	
	@Override
	public BigDecimal estimateFee(Integer maxBlocks) throws FreecashException,
			CommunicationException {
		String estimatedFeeJson = rpcClient.execute(Commands.ESTIMATE_FEE.getName(), maxBlocks);
		BigDecimal estimatedFee = rpcClient.getParser().parseBigDecimal(estimatedFeeJson);
		return estimatedFee;
	}
	
	@Override
	public String getAccount(String address) throws FreecashException, CommunicationException {
		String accountJson = rpcClient.execute(Commands.GET_ACCOUNT.getName(), address);
		String account = rpcClient.getParser().parseString(accountJson);
		return account;
	}
	
	@Override
	public String getAccountAddress(String account) throws FreecashException,
			CommunicationException {
		String addressJson = rpcClient.execute(Commands.GET_ACCOUNT_ADDRESS.getName(), account);
		String address = rpcClient.getParser().parseString(addressJson);
		return address;
	}
	
	@Override
	public List<String> getAddressesByAccount(String account) throws FreecashException,
			CommunicationException {
		String addressesJson = rpcClient.execute(Commands.GET_ADDRESSES_BY_ACCOUNT.getName(), 
				account);
		List<String> addresses = rpcClient.getMapper().mapToList(addressesJson, String.class);
		return addresses;
	}

	@Override
	public BigDecimal getBalance() throws FreecashException, CommunicationException {
		String balanceJson = rpcClient.execute(Commands.GET_BALANCE.getName());
		BigDecimal balance = rpcClient.getParser().parseBigDecimal(balanceJson);
		return balance;
	}
	
	@Override
	public BigDecimal getBalance(String account) throws FreecashException, CommunicationException {
		String balanceJson = rpcClient.execute(Commands.GET_BALANCE.getName(), account);
		BigDecimal balance = rpcClient.getParser().parseBigDecimal(balanceJson);
		return balance;
	}
	
	@Override
	public BigDecimal getBalance(String account, Integer confirmations) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(account, confirmations);
		String balanceJson = rpcClient.execute(Commands.GET_BALANCE.getName(), params);
		BigDecimal balance = rpcClient.getParser().parseBigDecimal(balanceJson);
		return balance;
	}

	@Override
	public BigDecimal getBalance(String account, Integer confirmations, Boolean withWatchOnly)
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(account, confirmations, withWatchOnly);
		String balanceJson = rpcClient.execute(Commands.GET_BALANCE.getName(), params);
		BigDecimal balance = rpcClient.getParser().parseBigDecimal(balanceJson);
		return balance;
	}
	
	@Override
	public String getBestBlockHash() throws FreecashException, CommunicationException {
		String headerHashJson = rpcClient.execute(Commands.GET_BEST_BLOCK_HASH.getName());
		String headerHash = rpcClient.getParser().parseString(headerHashJson);
		return headerHash;
	}
	
	@Override
	public Block getBlock(String headerHash) throws FreecashException, CommunicationException {
		String blockJson = rpcClient.execute(Commands.GET_BLOCK.getName(), headerHash);
		Block block = rpcClient.getMapper().mapToEntity(blockJson, Block.class);
		return block;
	}

	@Override
	public Object getBlock(String headerHash, Boolean isDecoded) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(headerHash, isDecoded);
		String blockJson = rpcClient.execute(Commands.GET_BLOCK.getName(), params);
		if(isDecoded) {
			Block block = rpcClient.getMapper().mapToEntity(blockJson, Block.class);
			return block;
		} else {
			String block = rpcClient.getParser().parseString(blockJson);
			return block;
		}
	}
	
	@Override
	public Integer getBlockCount() throws FreecashException, CommunicationException {
		String blockHeightJson = rpcClient.execute(Commands.GET_BLOCK_COUNT.getName());
		Integer blockHeight = rpcClient.getParser().parseInteger(blockHeightJson);
		return blockHeight;
	}

	@Override
	public NetWorkInfo getNetWorkInfo() throws FreecashException, CommunicationException {
		String res = rpcClient.execute(Commands.GET_NET_WORK_INOF.getName());
		NetWorkInfo netWorkInfo = rpcClient.getMapper().mapToEntity(res, NetWorkInfo.class);
		return netWorkInfo;
	}

	@Override
	public String getBlockHash(Integer blockHeight) throws FreecashException,
			CommunicationException {
		String headerHashJson = rpcClient.execute(Commands.GET_BLOCK_HASH.getName(), blockHeight);
		String headerHash = rpcClient.getParser().parseString(headerHashJson);
		return headerHash;
	}
	
	@Override
	public BigDecimal getDifficulty() throws FreecashException, CommunicationException {
		String difficultyJson = rpcClient.execute(Commands.GET_DIFFICULTY.getName());
		BigDecimal difficulty = rpcClient.getParser().parseBigDecimal(difficultyJson);
		return difficulty;
	}
	
	@Override
	public Boolean getGenerate() throws FreecashException, CommunicationException {
		String isGenerateJson = rpcClient.execute(Commands.GET_GENERATE.getName());
		Boolean isGenerate = rpcClient.getParser().parseBoolean(isGenerateJson);
		return isGenerate;
	}
	
	@Override
	public Long getHashesPerSec() throws FreecashException, CommunicationException {
		String hashesPerSecJson = rpcClient.execute(Commands.GET_HASHES_PER_SEC.getName());
		Long hashesPerSec = rpcClient.getParser().parseLong(hashesPerSecJson);
		return hashesPerSec;
	}
	
	@Override
	public Info getInfo() throws FreecashException, CommunicationException {
		String infoJson = rpcClient.execute(Commands.GET_INFO.getName());
		Info info = rpcClient.getMapper().mapToEntity(infoJson, Info.class);
		return info;
	}
	
	@Override
	public MemPoolInfo getMemPoolInfo() throws FreecashException, CommunicationException {
		String memPoolInfoJson = rpcClient.execute(Commands.GET_MEM_POOL_INFO.getName());
		MemPoolInfo memPoolInfo = rpcClient.getMapper().mapToEntity(memPoolInfoJson, 
				MemPoolInfo.class);
		return memPoolInfo;
	}

	@Override
	public MiningInfo getMiningInfo() throws FreecashException, CommunicationException {
		String miningInfoJson = rpcClient.execute(Commands.GET_MINING_INFO.getName());
		MiningInfo miningInfo = rpcClient.getMapper().mapToEntity(miningInfoJson, MiningInfo.class);
		return miningInfo;
	}
	
	@Override
	public String getNewAddress() throws FreecashException, CommunicationException {
		String addressJson = rpcClient.execute(Commands.GET_NEW_ADDRESS.getName());
		String address = rpcClient.getParser().parseString(addressJson);
		return address;
	}

	@Override
	public String getNewAddress(String account) throws FreecashException, CommunicationException {
		String addressJson = rpcClient.execute(Commands.GET_NEW_ADDRESS.getName(), account);
		String address = rpcClient.getParser().parseString(addressJson);
		return address;
	}
	
	@Override
	public List<PeerNode> getPeerInfo() throws FreecashException, CommunicationException {
		String peerInfoJson = rpcClient.execute(Commands.GET_PEER_INFO.getName());
		List<PeerNode> peerInfo = rpcClient.getMapper().mapToList(peerInfoJson, PeerNode.class);
		return peerInfo;
	}
	
	@Override
	public String getRawChangeAddress() throws FreecashException, CommunicationException {
		String addressJson = rpcClient.execute(Commands.GET_RAW_CHANGE_ADDRESS.getName());
		String address = rpcClient.getParser().parseString(addressJson);
		return address;
	}
	
	@Override
	public String getRawTransaction(String txId) throws FreecashException, CommunicationException {
		String hexTransactionJson = rpcClient.execute(Commands.GET_RAW_TRANSACTION.getName(), txId);
		String hexTransaction = rpcClient.getParser().parseString(hexTransactionJson);
		return hexTransaction;
	}

	@Override
	public Object getRawTransaction(String txId, Integer verbosity) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(txId, verbosity);
		String transactionJson = rpcClient.execute(Commands.GET_RAW_TRANSACTION.getName(), params);
		if(verbosity == DataFormats.HEX.getCode()) {
			String hexTransaction = rpcClient.getParser().parseString(transactionJson);
			return hexTransaction;
		} else {
			RawTransaction rawTransaction = rpcClient.getMapper().mapToEntity(transactionJson,
					RawTransaction.class);
			return rawTransaction;
		}
	}
	
	@Override
	public BigDecimal getReceivedByAccount(String account) throws FreecashException,
			CommunicationException {
		String totalReceivedJson = rpcClient.execute(Commands.GET_RECEIVED_BY_ACCOUNT.getName(),
				account);
		BigDecimal totalReceived = rpcClient.getParser().parseBigDecimal(totalReceivedJson);
		return totalReceived;
	}

	@Override
	public BigDecimal getReceivedByAccount(String account, Integer confirmations) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(account, confirmations);
		String totalReceivedJson = rpcClient.execute(Commands.GET_RECEIVED_BY_ACCOUNT.getName(), 
				params);
		BigDecimal totalReceived = rpcClient.getParser().parseBigDecimal(totalReceivedJson);
		return totalReceived;
	}
	
	@Override
	public BigDecimal getReceivedByAddress(String address) throws FreecashException,
			CommunicationException {
		String totalReceivedJson = rpcClient.execute(Commands.GET_RECEIVED_BY_ADDRESS.getName(),
				address);
		BigDecimal totalReceived = rpcClient.getParser().parseBigDecimal(totalReceivedJson);
		return totalReceived;
	}

	@Override
	public BigDecimal getReceivedByAddress(String address, Integer confirmations) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(address, confirmations);
		String totalReceivedJson = rpcClient.execute(Commands.GET_RECEIVED_BY_ADDRESS.getName(),
				params);
		BigDecimal totalReceived = rpcClient.getParser().parseBigDecimal(totalReceivedJson);
		return totalReceived;
	}
	
	@Override
	public Transaction getTransaction(String txId) throws FreecashException,
			CommunicationException {
		String transactionJson = rpcClient.execute(Commands.GET_TRANSACTION.getName(), txId);
		Transaction transaction = rpcClient.getMapper().mapToEntity(transactionJson,
				Transaction.class);
		return transaction;
	}

	@Override
	public Transaction getTransaction(String txId, Boolean withWatchOnly) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(txId, withWatchOnly);
		String transactionJson = rpcClient.execute(Commands.GET_TRANSACTION.getName(), params);
		Transaction transaction = rpcClient.getMapper().mapToEntity(transactionJson,
				Transaction.class);
		return transaction;
	}
	
	@Override
	public BigDecimal getUnconfirmedBalance() throws FreecashException, CommunicationException {
		String unconfirmedBalanceJson = rpcClient.execute(Commands.GET_UNCONFIRMED_BALANCE.getName());
		BigDecimal unconfirmedBalance = rpcClient.getParser().parseBigDecimal(unconfirmedBalanceJson);
		return unconfirmedBalance;
	}	

	@Override
	public WalletInfo getWalletInfo() throws FreecashException, CommunicationException {
		String walletInfoJson = rpcClient.execute(Commands.GET_WALLET_INFO.getName());
		WalletInfo walletInfo = rpcClient.getMapper().mapToEntity(walletInfoJson, WalletInfo.class);
		return walletInfo;
	}
	
	@Override
	public void importAddress(String address) throws FreecashException, CommunicationException {
		rpcClient.execute(Commands.IMPORT_ADDRESS.getName(), address);
	}

	@Override
	public void importAddress(String address, String account) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(address, account);
		rpcClient.execute(Commands.IMPORT_ADDRESS.getName(), params);
	}

	@Override
	public void importAddress(String address, String account, Boolean withRescan) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(address, account, withRescan);
		rpcClient.execute(Commands.IMPORT_ADDRESS.getName(), params);
	}
	
	@Override
	public void importPrivKey(String privateKey) throws FreecashException, CommunicationException {
		rpcClient.execute(Commands.IMPORT_PRIV_KEY.getName(), privateKey);
	}

	@Override
	public void importPrivKey(String privateKey, String account) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(privateKey, account);
		rpcClient.execute(Commands.IMPORT_PRIV_KEY.getName(), params);
	}

	@Override
	public void importPrivKey(String privateKey, String account, Boolean withRescan) 
			throws FreecashException, CommunicationException{
		List<Object> params = CollectionUtils.asList(privateKey, account, withRescan);
		rpcClient.execute(Commands.IMPORT_PRIV_KEY.getName(), params);
	}
	
	@Override
	public void importWallet(String filePath) throws FreecashException, CommunicationException {
		rpcClient.execute(Commands.IMPORT_WALLET.getName(), filePath);
	}
	
	@Override
	public void keyPoolRefill() throws FreecashException, CommunicationException {
		rpcClient.execute(Commands.KEY_POOL_REFILL.getName());
	}

	@Override
	public void keyPoolRefill(Integer keypoolSize) throws FreecashException,
			CommunicationException {
		rpcClient.execute(Commands.KEY_POOL_REFILL.getName(), keypoolSize);
	}
	
	@Override
	public Map<String, BigDecimal> listAccounts() throws FreecashException, CommunicationException {
		String accountsJson = rpcClient.execute(Commands.LIST_ACCOUNTS.getName());
		Map<String, BigDecimal> accounts = rpcClient.getMapper().mapToMap(accountsJson, 
				String.class, BigDecimal.class);
		accounts = NumberUtils.setValueScale(accounts, Defaults.DECIMAL_SCALE);
		return accounts;
	}
	
	@Override
	public Map<String, BigDecimal> listAccounts(Integer confirmations) throws FreecashException,
			CommunicationException {
		String accountsJson = rpcClient.execute(Commands.LIST_ACCOUNTS.getName(), confirmations);
		Map<String, BigDecimal> accounts = rpcClient.getMapper().mapToMap(accountsJson, 
				String.class, BigDecimal.class);
		accounts = NumberUtils.setValueScale(accounts, Defaults.DECIMAL_SCALE);
		return accounts;
	}

	@Override
	public Map<String, BigDecimal> listAccounts(Integer confirmations, Boolean withWatchOnly) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(confirmations, withWatchOnly);
		String accountsJson = rpcClient.execute(Commands.LIST_ACCOUNTS.getName(), params);
		Map<String, BigDecimal> accounts = rpcClient.getMapper().mapToMap(accountsJson, 
				String.class, BigDecimal.class);
		accounts = NumberUtils.setValueScale(accounts, Defaults.DECIMAL_SCALE);
		return accounts;
	}
	
	@Override
	public List<List<AddressOverview>> listAddressGroupings() throws FreecashException,
			CommunicationException {
		String groupingsJson = rpcClient.execute(Commands.LIST_ADDRESS_GROUPINGS.getName());
		List<List<AddressOverview>> groupings = rpcClient.getMapper().mapToNestedLists(1, 
				groupingsJson, AddressOverview.class);
		return groupings;
	}
	
	@Override
	public List<OutputOverview> listLockUnspent() throws FreecashException, CommunicationException {
		String lockedOutputsJson = rpcClient.execute(Commands.LIST_LOCK_UNSPENT.getName());
		List<OutputOverview> lockedOutputs = rpcClient.getMapper().mapToList(lockedOutputsJson, 
				OutputOverview.class);
		return lockedOutputs;
	}
	
	@Override
	public List<Account> listReceivedByAccount() throws FreecashException, CommunicationException {
		String accountsJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ACCOUNT.getName());
		List<Account> accounts = rpcClient.getMapper().mapToList(accountsJson, Account.class);
		return accounts;
	}

	@Override
	public List<Account> listReceivedByAccount(Integer confirmations) throws FreecashException,
			CommunicationException {
		String accountsJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ACCOUNT.getName(), 
				confirmations);
		List<Account> accounts = rpcClient.getMapper().mapToList(accountsJson, Account.class);
		return accounts;
	}

	@Override
	public List<Account> listReceivedByAccount(Integer confirmations, Boolean withUnused) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(confirmations, withUnused);
		String accountsJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ACCOUNT.getName(), 
				params);
		List<Account> accounts = rpcClient.getMapper().mapToList(accountsJson, Account.class);
		return accounts;
	}

	@Override
	public List<Account> listReceivedByAccount(Integer confirmations, Boolean withUnused, 
			Boolean withWatchOnly) throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(confirmations, withUnused, withWatchOnly);
		String accountsJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ACCOUNT.getName(), 
				params);
		List<Account> accounts = rpcClient.getMapper().mapToList(accountsJson, Account.class);
		return accounts;
	}
	

	@Override
	public List<Address> listReceivedByAddress() throws FreecashException, CommunicationException {
		String addressesJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ADDRESS.getName());
		List<Address> addresses = rpcClient.getMapper().mapToList(addressesJson, Address.class);
		return addresses;
	}

	@Override
	public List<Address> listReceivedByAddress(Integer confirmations) throws FreecashException,
			CommunicationException {
		String addressesJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ADDRESS.getName(),
				confirmations);
		List<Address> addresses = rpcClient.getMapper().mapToList(addressesJson, Address.class);
		return addresses;
	}

	@Override
	public List<Address> listReceivedByAddress(Integer confirmations, Boolean withUnused) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(confirmations, withUnused);
		String addressesJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ADDRESS.getName(),
				params);
		List<Address> addresses = rpcClient.getMapper().mapToList(addressesJson, Address.class);
		return addresses;
	}

	@Override
	public List<Address> listReceivedByAddress(Integer confirmations, Boolean withUnused, 
			Boolean withWatchOnly) throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(confirmations, withUnused, withWatchOnly);
		String addressesJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ADDRESS.getName(),
				params);
		List<Address> addresses = rpcClient.getMapper().mapToList(addressesJson, Address.class);
		return addresses;
	}
	
	@Override
	public SinceBlock listSinceBlock() throws FreecashException, CommunicationException {
		String sinceBlockJson = rpcClient.execute(Commands.LIST_SINCE_BLOCK.getName());
		SinceBlock sinceBlock = rpcClient.getMapper().mapToEntity(sinceBlockJson, SinceBlock.class);
		return sinceBlock;
	}

	@Override
	public SinceBlock listSinceBlock(String headerHash) throws FreecashException,
			CommunicationException {
		String sinceBlockJson = rpcClient.execute(Commands.LIST_SINCE_BLOCK.getName(), headerHash);
		SinceBlock sinceBlock = rpcClient.getMapper().mapToEntity(sinceBlockJson, SinceBlock.class);
		return sinceBlock;
	}

	@Override
	public SinceBlock listSinceBlock(String headerHash, Integer confirmations) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(headerHash, confirmations);
		String sinceBlockJson = rpcClient.execute(Commands.LIST_SINCE_BLOCK.getName(), params);
		SinceBlock sinceBlock = rpcClient.getMapper().mapToEntity(sinceBlockJson, SinceBlock.class);
		return sinceBlock;
	}

	@Override
	public SinceBlock listSinceBlock(String headerHash, Integer confirmations, 
			Boolean withWatchOnly) throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(headerHash, confirmations, withWatchOnly);
		String sinceBlockJson = rpcClient.execute(Commands.LIST_SINCE_BLOCK.getName(), params);
		SinceBlock sinceBlock = rpcClient.getMapper().mapToEntity(sinceBlockJson, SinceBlock.class);
		return sinceBlock;
	}
	
	@Override
	public List<Payment> listTransactions() throws FreecashException, CommunicationException {
		String paymentsJson = rpcClient.execute(Commands.LIST_TRANSACTIONS.getName());
		List<Payment> payments = rpcClient.getMapper().mapToList(paymentsJson, Payment.class);
		return payments;
	}

	@Override
	public List<Payment> listTransactions(String account) throws FreecashException,
			CommunicationException {
		String paymentsJson = rpcClient.execute(Commands.LIST_TRANSACTIONS.getName(), account);
		List<Payment> payments = rpcClient.getMapper().mapToList(paymentsJson, Payment.class);
		return payments;
	}

	@Override
	public List<Payment> listTransactions(String account, Integer count) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(account, count);
		String paymentsJson = rpcClient.execute(Commands.LIST_TRANSACTIONS.getName(), params);
		List<Payment> payments = rpcClient.getMapper().mapToList(paymentsJson, Payment.class);
		return payments;
	}

	@Override
	public List<Payment> listTransactions(String account, Integer count, Integer offset)
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(account, count, offset);
		String paymentsJson = rpcClient.execute(Commands.LIST_TRANSACTIONS.getName(), params);
		List<Payment> payments = rpcClient.getMapper().mapToList(paymentsJson, Payment.class);
		return payments;
	}

	@Override
	public List<Payment> listTransactions(String account, Integer count, Integer offset, 
			Boolean withWatchOnly) throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(account, count, offset, withWatchOnly);
		String paymentsJson = rpcClient.execute(Commands.LIST_TRANSACTIONS.getName(), params);
		List<Payment> payments = rpcClient.getMapper().mapToList(paymentsJson, Payment.class);
		return payments;
	}
	
	@Override
	public List<Output> listUnspent() throws FreecashException, CommunicationException {
		String unspentOutputsJson = rpcClient.execute(Commands.LIST_UNSPENT.getName());
		List<Output> unspentOutputs = rpcClient.getMapper().mapToList(unspentOutputsJson,
				Output.class);
		return unspentOutputs;
	}

	@Override
	public List<Output> listUnspent(Integer minConfirmations) throws FreecashException,
			CommunicationException {
		String unspentOutputsJson = rpcClient.execute(Commands.LIST_UNSPENT.getName(), 
				minConfirmations);
		List<Output> unspentOutputs = rpcClient.getMapper().mapToList(unspentOutputsJson,
				Output.class);
		return unspentOutputs;
	}

	@Override
	public List<Output> listUnspent(Integer minConfirmations, Integer maxConfirmations) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(minConfirmations, maxConfirmations);
		String unspentOutputsJson = rpcClient.execute(Commands.LIST_UNSPENT.getName(), params);
		List<Output> unspentOutputs = rpcClient.getMapper().mapToList(unspentOutputsJson,
				Output.class);
		return unspentOutputs;
	}

	@Override
	public List<Output> listUnspent(Integer minConfirmations, Integer maxConfirmations, 
			List<String> addresses) throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(minConfirmations, maxConfirmations, addresses);
		String unspentOutputsJson = rpcClient.execute(Commands.LIST_UNSPENT.getName(), params);
		List<Output> unspentOutputs = rpcClient.getMapper().mapToList(unspentOutputsJson,
				Output.class);
		return unspentOutputs;
	}
	
	@Override
	public Boolean lockUnspent(Boolean isUnlocked) throws FreecashException,
			CommunicationException {
		String isSuccessJson = rpcClient.execute(Commands.LOCK_UNSPENT.getName(), isUnlocked);
		Boolean isSuccess = rpcClient.getParser().parseBoolean(isSuccessJson);
		return isSuccess;
	}

	@Override
	public Boolean lockUnspent(Boolean isUnlocked, List<OutputOverview> outputs) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(isUnlocked, outputs);
		String isSuccessJson = rpcClient.execute(Commands.LOCK_UNSPENT.getName(), params);
		Boolean isSuccess = rpcClient.getParser().parseBoolean(isSuccessJson);
		return isSuccess;
	}
	
	@Override
	public Boolean move(String fromAccount, String toAccount, BigDecimal amount) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(fromAccount, toAccount, amount);
		String isSuccessJson = rpcClient.execute(Commands.MOVE.getName(), params);
		Boolean isSuccess = rpcClient.getParser().parseBoolean(isSuccessJson);
		return isSuccess;
	}

	@Override
	public Boolean move(String fromAccount, String toAccount, BigDecimal amount, Integer dummy,
			String comment) throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(fromAccount, toAccount, amount, dummy, comment);
		String isSuccessJson = rpcClient.execute(Commands.MOVE.getName(), params);
		Boolean isSuccess = rpcClient.getParser().parseBoolean(isSuccessJson);
		return isSuccess;
	}
	
	@Override
	public void ping() throws FreecashException, CommunicationException {
		rpcClient.execute(Commands.PING.getName());
	}
	
	@Override
	public String sendFrom(String fromAccount, String toAddress, BigDecimal amount) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(fromAccount, toAddress, amount);
		String transactionIdJson = rpcClient.execute(Commands.SEND_FROM.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendFrom(String fromAccount, String toAddress, BigDecimal amount, 
			Integer confirmations) throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(fromAccount, toAddress, amount, confirmations);
		String transactionIdJson = rpcClient.execute(Commands.SEND_FROM.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendFrom(String fromAccount, String toAddress, BigDecimal amount, 
			Integer confirmations, String comment) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(fromAccount, toAddress, amount, confirmations, 
				comment);
		String transactionIdJson = rpcClient.execute(Commands.SEND_FROM.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendFrom(String fromAccount, String toAddress, BigDecimal amount, 
			Integer confirmations, String comment, String commentTo) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(fromAccount, toAddress, amount, confirmations,
				comment, commentTo);
		String transactionIdJson = rpcClient.execute(Commands.SEND_FROM.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}
	
	@Override
	public String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(fromAccount, toAddresses);
		String transactionIdJson = rpcClient.execute(Commands.SEND_MANY.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses,	
			Integer confirmations) throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(fromAccount, toAddresses, confirmations);
		String transactionIdJson = rpcClient.execute(Commands.SEND_MANY.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses,
			Integer confirmations, String comment) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(fromAccount, toAddresses, confirmations,
				comment);
		String transactionIdJson = rpcClient.execute(Commands.SEND_MANY.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}
	
	@Override
	public String sendRawTransaction(String hexTransaction) throws FreecashException,
			CommunicationException {
		String transactionIdJson = rpcClient.execute(Commands.SEND_RAW_TRANSACTION.getName(), 
				hexTransaction);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendRawTransaction(String hexTransaction, Boolean withHighFees) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(hexTransaction, withHighFees);
		String transactionIdJson = rpcClient.execute(Commands.SEND_RAW_TRANSACTION.getName(), 
				params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}
	
	@Override
	public String sendToAddress(String toAddress, BigDecimal amount) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(toAddress, amount);
		String transactionIdJson = rpcClient.execute(Commands.SEND_TO_ADDRESS.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendToAddress(String toAddress, BigDecimal amount, String comment) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(toAddress, amount, comment);
		String transactionIdJson = rpcClient.execute(Commands.SEND_TO_ADDRESS.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendToAddress(String toAddress, BigDecimal amount, String comment, 
			String commentTo) throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(toAddress, amount, comment, commentTo);
		String transactionIdJson = rpcClient.execute(Commands.SEND_TO_ADDRESS.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}
	
	@Override
	public void setAccount(String address, String account) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(address, account);
		rpcClient.execute(Commands.SET_ACCOUNT.getName(), params);
	}
	
	@Override
	public void setGenerate(Boolean isGenerate) throws FreecashException, CommunicationException {
		rpcClient.execute(Commands.SET_GENERATE.getName(), isGenerate);		
	}
	
	@Override
	public void setGenerate(Boolean isGenerate, Integer processors) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(isGenerate, processors);
		rpcClient.execute(Commands.SET_GENERATE.getName(), params);
	}
	
	@Override
	public Boolean setTxFee(BigDecimal txFee) throws FreecashException, CommunicationException {
		String isSuccessJson = rpcClient.execute(Commands.SET_TX_FEE.getName(), txFee);
		Boolean isSuccess = rpcClient.getParser().parseBoolean(isSuccessJson);
		return isSuccess;
	}
	
	@Override
	public String signMessage(String address, String message) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(address, message);
		String signatureJson = rpcClient.execute(Commands.SIGN_MESSAGE.getName(), params);
		String signature = rpcClient.getParser().parseString(signatureJson);
		return signature;
	}
	
	@Override
	public SignatureResult signRawTransaction(String hexTransaction) throws FreecashException,
			CommunicationException {
		String signatureResultJson = rpcClient.execute(Commands.SIGN_RAW_TRANSACTION.getName(), 
				hexTransaction);
		SignatureResult signatureResult = rpcClient.getMapper().mapToEntity(signatureResultJson, 
				SignatureResult.class);
		return signatureResult;
	}

	@Override
	public SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(hexTransaction, outputs);
		String signatureResultJson = rpcClient.execute(Commands.SIGN_RAW_TRANSACTION.getName(), 
				params);
		SignatureResult signatureResult = rpcClient.getMapper().mapToEntity(signatureResultJson, 
				SignatureResult.class);
		return signatureResult;
	}

	@Override
	public SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs, 
			List<String> privateKeys) throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(hexTransaction, outputs, privateKeys);
		String signatureResultJson = rpcClient.execute(Commands.SIGN_RAW_TRANSACTION.getName(),
				params);
		SignatureResult signatureResult = rpcClient.getMapper().mapToEntity(signatureResultJson, 
				SignatureResult.class);
		return signatureResult;
	}

	@Override
	public SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs, 
			List<String> privateKeys, String sigHashType) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(hexTransaction, outputs, privateKeys, 
				sigHashType);
		String signatureResultJson = rpcClient.execute(Commands.SIGN_RAW_TRANSACTION.getName(), 
				params);
		SignatureResult signatureResult = rpcClient.getMapper().mapToEntity(signatureResultJson,
				SignatureResult.class);
		return signatureResult;
	}
	
	@Override
	public String stop() throws FreecashException, CommunicationException {
		String noticeMsgJson = rpcClient.execute(Commands.STOP.getName());
		String noticeMsg = rpcClient.getParser().parseString(noticeMsgJson);
		return noticeMsg;
	}
	
	@Override
	public AddressInfo validateAddress(String address) throws FreecashException,
			CommunicationException {
		String addressInfoJson = rpcClient.execute(Commands.VALIDATE_ADDRESS.getName(), address);
		AddressInfo addressInfo = rpcClient.getMapper().mapToEntity(addressInfoJson, 
				AddressInfo.class);
		return addressInfo;
	}
	
	@Override
	public Boolean verifyMessage(String address, String signature, String message) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(address, signature, message);
		String isSigValidJson = rpcClient.execute(Commands.VERIFY_MESSAGE.getName(), params);
		Boolean isSigValid = rpcClient.getParser().parseBoolean(isSigValidJson);
		return isSigValid;
	}
	
	@Override
	public void walletLock() throws FreecashException, CommunicationException {
		rpcClient.execute(Commands.WALLET_LOCK.getName());
	}
	
	@Override
	public void walletPassphrase(String passphrase, Integer authTimeout) throws FreecashException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(passphrase, authTimeout);
		rpcClient.execute(Commands.WALLET_PASSPHRASE.getName(), params);
	}

	@Override
	public void walletPassphraseChange(String curPassphrase, String newPassphrase) 
			throws FreecashException, CommunicationException {
		List<Object> params = CollectionUtils.asList(curPassphrase, newPassphrase);
		rpcClient.execute(Commands.WALLET_PASSPHRASE_CHANGE.getName(), params);
	}

	@Override
	public Properties getNodeConfig() {
		return configurator.getNodeConfig();
	}
	
	@Override
	public String getNodeVersion() {
		return configurator.getNodeVersion();
	}
	
	@Override
	public synchronized void close() {
		LOG.info(">> close(..): closing the 'freecash' core wrapper");
		rpcClient.close();
	}
	
	private void initialize() {
		LOG.info(">> initialize(..): initiating the 'freecash' core wrapper");
		configurator = new ClientConfigurator();
	}
}