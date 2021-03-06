package org.freecash.core.param;

import org.bitcoinj.core.Utils;
import org.bitcoinj.net.discovery.HttpDiscovery;
import org.bitcoinj.params.MainNetParams;


public class FchNetWorkParams extends MainNetParams {
    public static final int MAINNET_MAJORITY_WINDOW = 1000;
    public static final int MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED = 950;
    public static final int MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 750;

    public FchNetWorkParams() {
        super();
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        maxTarget = Utils.decodeCompactBits(0x1d00ffffL);
        dumpedPrivateKeyHeader = 128;
        addressHeader = 35;
        p2shHeader = 5;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        port = 8333;
        packetMagic = 0xe8f4f3e5L;
        bip32HeaderPub = 0x0488B21E; //The 4 byte header that serializes in base58 to "xpub".
        bip32HeaderPriv = 0x0488ADE4; //The 4 byte header that serializes in base58 to "xprv"

        majorityEnforceBlockUpgrade = MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
        majorityRejectBlockOutdated = MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
        majorityWindow = MAINNET_MAJORITY_WINDOW;

        genesisBlock.setDifficultyTarget(0x1d00ffffL);
        genesisBlock.setTime(1577836802L);
        genesisBlock.setNonce(223904032);
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = 210000;
        spendableCoinbaseDepth = 100;
//        String genesisHash = genesisBlock.getHashAsString();
//        checkState(genesisHash.equals("000000000019d6689c085ae165831e934ff763ae46a2a6c172b3f1b60a8ce26f"),
//                genesisHash);

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.
//        checkpoints.put(91722, Sha256Hash.wrap("00000000000271a2dc26e7667f8419f2e15416dc6955e5a6c6cdf3f2574dd08e"));
//        checkpoints.put(91812, Sha256Hash.wrap("00000000000af0aed4792b1acee3d966af36cf5def14935db8de83d6f9306f2f"));
//        checkpoints.put(91842, Sha256Hash.wrap("00000000000a4d0a398161ffc163c503763b1f4360639393e0e4c8e300e0caec"));
//        checkpoints.put(91880, Sha256Hash.wrap("00000000000743f190a18c5577a3c2d2a1f610ae9601ac046a38084ccb7cd721"));
//        checkpoints.put(200000, Sha256Hash.wrap("000000000000034a7dedef4a161fa058a2d67a173a90155f3a2fe6fc132e0ebf"));

        dnsSeeds = new String[] {
                "seed.freecash.info",
                "seed1.freecash.info",
                "seed3.freecash.info",
                "seed1.freecash.org",
                "seed2.freecash.org",
                "seed3.freecash.org",
        };
        httpSeeds = new HttpDiscovery.Details[] {

        };

        addrSeeds = new int[] {};
    }

    private static FchNetWorkParams instance;
    public static synchronized FchNetWorkParams get() {
        if (instance == null) {
            instance = new FchNetWorkParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_MAINNET;
    }
}
