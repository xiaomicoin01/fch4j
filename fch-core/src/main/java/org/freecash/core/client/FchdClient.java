package org.freecash.core.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.freecash.core.FreecashException;
import org.freecash.core.CommunicationException;
import org.freecash.core.domain.*;
import org.freecash.core.domain.*;

public interface FchdClient {
	
	String addMultiSigAddress(Integer minSignatures, List<String> addresses) 
			throws FreecashException, CommunicationException;

	String addMultiSigAddress(Integer minSignatures, List<String> addresses, String account) 
			throws FreecashException, CommunicationException;
	
	void backupWallet(String filePath) throws FreecashException, CommunicationException;
	
	MultiSigAddress createMultiSig(Integer minSignatures, List<String> addresses)
			throws FreecashException, CommunicationException;
	
	String createRawTransaction(List<OutputOverview> outputs, List<Map<String, Object>> params)
			throws FreecashException, CommunicationException;

	RawTransactionOverview decodeRawTransaction(String hexTransaction) throws FreecashException,
			CommunicationException;
	
	RedeemScript decodeScript(String hexRedeemScript) throws FreecashException,
			CommunicationException;
	
	String dumpPrivKey(String address) throws FreecashException, CommunicationException;
	
	void dumpWallet(String filePath) throws FreecashException, CommunicationException;
	
	String encryptWallet(String passphrase) throws FreecashException, CommunicationException;

	BigDecimal estimateFee(Integer maxBlocks) throws FreecashException, CommunicationException;
	
	String getAccount(String address) throws FreecashException, CommunicationException;
	
	String getAccountAddress(String account) throws FreecashException, CommunicationException;
	
	List<String> getAddressesByAccount(String account) throws FreecashException,
			CommunicationException;
	
	BigDecimal getBalance() throws FreecashException, CommunicationException;
	
	BigDecimal getBalance(String account) throws FreecashException, CommunicationException;
	
	BigDecimal getBalance(String account, Integer confirmations) throws FreecashException,
			CommunicationException;

	BigDecimal getBalance(String account, Integer confirmations, Boolean withWatchOnly) 
			throws FreecashException, CommunicationException;
	
	String getBestBlockHash() throws FreecashException, CommunicationException;
	
	Block getBlock(String headerHash) throws FreecashException, CommunicationException;

	Object getBlock(String headerHash, Boolean isDecoded) throws FreecashException,
			CommunicationException;

	Integer getBlockCount() throws FreecashException, CommunicationException;

	NetWorkInfo getNetWorkInfo() throws FreecashException, CommunicationException;
	
	String getBlockHash(Integer blockHeight) throws FreecashException, CommunicationException;
	
	BigDecimal getDifficulty() throws FreecashException, CommunicationException;
	
	Boolean getGenerate() throws FreecashException, CommunicationException;
	
	Long getHashesPerSec() throws FreecashException, CommunicationException;
	
	Info getInfo() throws FreecashException, CommunicationException;
	
	MemPoolInfo getMemPoolInfo() throws FreecashException, CommunicationException;
	
	MiningInfo getMiningInfo() throws FreecashException, CommunicationException;

	String getNewAddress() throws FreecashException, CommunicationException;
	
	String getNewAddress(String account) throws FreecashException, CommunicationException;
	
	List<PeerNode> getPeerInfo() throws FreecashException, CommunicationException;
	
	String getRawChangeAddress() throws FreecashException, CommunicationException;
	
	String getRawTransaction(String txId) throws FreecashException, CommunicationException;

	Object getRawTransaction(String txId, boolean verbosity) throws FreecashException,
			CommunicationException;
	
	BigDecimal getReceivedByAccount(String account) throws FreecashException,
			CommunicationException;

	BigDecimal getReceivedByAccount(String account, Integer confirmations) throws FreecashException,
			CommunicationException;

	BigDecimal getReceivedByAddress(String address) throws FreecashException, CommunicationException;

	BigDecimal getReceivedByAddress(String address, Integer confirmations) throws FreecashException,
			CommunicationException;
	
	Transaction getTransaction(String txId) throws FreecashException, CommunicationException;

	Transaction getTransaction(String txId, Boolean withWatchOnly) throws FreecashException,
			CommunicationException;
	
	BigDecimal getUnconfirmedBalance() throws FreecashException, CommunicationException;
	
	WalletInfo getWalletInfo() throws FreecashException, CommunicationException;
	
	void importAddress(String address) throws FreecashException, CommunicationException;
	
	void importAddress(String address, String account) throws FreecashException,
			CommunicationException;

	void importAddress(String address, String account, Boolean withRescan) throws FreecashException,
			CommunicationException;
	
	void importPrivKey(String privateKey) throws FreecashException, CommunicationException;

	void importPrivKey(String privateKey, String account) throws FreecashException,
			CommunicationException;

	void importPrivKey(String privateKey, String account, Boolean withRescan) 
			throws FreecashException, CommunicationException;
	
	void importWallet(String filePath) throws FreecashException, CommunicationException;
	
	void keyPoolRefill() throws FreecashException, CommunicationException;

	void keyPoolRefill(Integer keypoolSize) throws FreecashException, CommunicationException;
	
	Map<String, BigDecimal> listAccounts() throws FreecashException, CommunicationException;
	
	Map<String, BigDecimal> listAccounts(Integer confirmations) throws FreecashException,
			CommunicationException;
	
	Map<String, BigDecimal> listAccounts(Integer confirmations, Boolean withWatchOnly) 
			throws FreecashException, CommunicationException;
	
	List<List<AddressOverview>> listAddressGroupings() throws FreecashException,
			CommunicationException;

	List<OutputOverview> listLockUnspent() throws FreecashException, CommunicationException;
	
	List<Account> listReceivedByAccount() throws FreecashException, CommunicationException;

	List<Account> listReceivedByAccount(Integer confirmations) throws FreecashException,
			CommunicationException;

	List<Account> listReceivedByAccount(Integer confirmations, Boolean withUnused) 
			throws FreecashException, CommunicationException;

	List<Account> listReceivedByAccount(Integer confirmations, Boolean withUnused, 
			Boolean withWatchOnly) throws FreecashException, CommunicationException;
	
	List<Address> listReceivedByAddress() throws FreecashException, CommunicationException;

	List<Address> listReceivedByAddress(Integer confirmations) throws FreecashException,
			CommunicationException;

	List<Address> listReceivedByAddress(Integer confirmations, Boolean withUnused) 
			throws FreecashException, CommunicationException;

	List<Address> listReceivedByAddress(Integer confirmations, Boolean withUnused,
			Boolean withWatchOnly) throws FreecashException, CommunicationException;
	
	SinceBlock listSinceBlock() throws FreecashException, CommunicationException;

	SinceBlock listSinceBlock(String headerHash) throws FreecashException, CommunicationException;

	SinceBlock listSinceBlock(String headerHash, Integer confirmations) throws FreecashException,
			CommunicationException;

	SinceBlock listSinceBlock(String headerHash, Integer confirmations, Boolean withWatchOnly) 
			throws FreecashException, CommunicationException;
	
	List<Payment> listTransactions() throws FreecashException, CommunicationException;

	List<Payment> listTransactions(String account) throws FreecashException, CommunicationException;

	List<Payment> listTransactions(String account, Integer count) throws FreecashException,
			CommunicationException;

	List<Payment> listTransactions(String account, Integer count, Integer offset) 
			throws FreecashException, CommunicationException;

	List<Payment> listTransactions(String account, Integer count, Integer offset, 
			Boolean withWatchOnly) throws FreecashException, CommunicationException;
	
	List<Output> listUnspent() throws FreecashException, CommunicationException;

	List<Output> listUnspent(Integer minConfirmations) throws FreecashException,
			CommunicationException;

	List<Output> listUnspent(Integer minConfirmations, Integer maxConfirmations) 
			throws FreecashException, CommunicationException;

	List<Output> listUnspent(Integer minConfirmations, Integer maxConfirmations, 
			List<String> addresses) throws FreecashException, CommunicationException;
	
	Boolean lockUnspent(Boolean isUnlocked) throws FreecashException, CommunicationException;

	Boolean lockUnspent(Boolean isUnlocked, List<OutputOverview> outputs) throws FreecashException,
			CommunicationException;
	
	Boolean move(String fromAccount, String toAccount, BigDecimal amount) throws FreecashException,
			CommunicationException;

	Boolean move(String fromAccount, String toAccount, BigDecimal amount, Integer dummy, 
			String comment) throws FreecashException, CommunicationException;
	
	void ping() throws FreecashException, CommunicationException;
	
	String sendFrom(String fromAccount, String toAddress, BigDecimal amount) 
			throws FreecashException, CommunicationException;

	String sendFrom(String fromAccount, String toAddress, BigDecimal amount, Integer confirmations) 
			throws FreecashException, CommunicationException;

	String sendFrom(String fromAccount, String toAddress, BigDecimal amount, Integer confirmations,
			String comment) throws FreecashException, CommunicationException;

	String sendFrom(String fromAccount, String toAddress, BigDecimal amount, Integer confirmations,
			String comment, String commentTo) throws FreecashException, CommunicationException;
	
	String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses) 
			throws FreecashException, CommunicationException;

	String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses, Integer confirmations) 
			throws FreecashException, CommunicationException;

	String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses, Integer confirmations, 
			String comment) throws FreecashException, CommunicationException;
	
	String sendRawTransaction(String hexTransaction) throws FreecashException,
			CommunicationException;

	String sendRawTransaction(String hexTransaction, Boolean withHighFees) throws FreecashException,
			CommunicationException;
	
	String sendToAddress(String toAddress, BigDecimal amount) throws FreecashException,
			CommunicationException;

	String sendToAddress(String toAddress, BigDecimal amount, String comment) 
			throws FreecashException, CommunicationException;

	String sendToAddress(String toAddress, BigDecimal amount, String comment, String commentTo) 
			throws FreecashException, CommunicationException;
	
	void setAccount(String address, String account) throws FreecashException,
			CommunicationException;
	
	void setGenerate(Boolean isGenerate) throws FreecashException, CommunicationException;
	
	void setGenerate(Boolean isGenerate, Integer processors) throws FreecashException,
			CommunicationException;
	
	Boolean setTxFee(BigDecimal txFee) throws FreecashException, CommunicationException;

	String signMessage(String address, String message) throws FreecashException,
			CommunicationException;
	
	SignatureResult signRawTransactionWithKey(String hexTransaction,List<String> keys) throws FreecashException,
			CommunicationException;

	SignatureResult signRawTransaction(String hexTransaction) throws FreecashException,
			CommunicationException;

	SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs) 
			throws FreecashException, CommunicationException;

	SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs, 
			List<String> privateKeys) throws FreecashException, CommunicationException;

	SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs, 
			List<String> privateKeys, String sigHashType) throws FreecashException,
			CommunicationException;
	
	String stop() throws FreecashException, CommunicationException;
	
	AddressInfo validateAddress(String address) throws FreecashException, CommunicationException;
	
	Boolean verifyMessage(String address, String signature, String message) 
			throws FreecashException, CommunicationException;
	
	void walletLock() throws FreecashException, CommunicationException;

	void walletPassphrase(String passphrase, Integer authTimeout) throws FreecashException,
			CommunicationException;

	void walletPassphraseChange(String curPassphrase, String newPassphrase) 
			throws FreecashException, CommunicationException;

	Properties getNodeConfig();
	
	String getNodeVersion();
	
	void close();
}