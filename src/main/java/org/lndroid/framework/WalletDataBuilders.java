package org.lndroid.framework;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class WalletDataBuilders {

    public interface IBuilder<T> {
        T build();
    }

    interface ErrorBuilder<Builder> {
        Builder setCode(String code);

        Builder setMessage(String message);
    }

    interface WalletStateBuilder<Builder> {
        Builder setState(int state);

        Builder setCode(String code);

        Builder setMessage(String message);
    }

    interface UserIdentityBuilder<Builder> {
        Builder setUserId(int id);

        Builder setAppPackageName(String packageName);

        Builder setAppPubkey(String pubkey);
    }

    interface UserBuilder<Builder> {
        Builder setId(int id);

        Builder setAuthUserId(int authUserId);

        Builder setCreateTime(long createTime);

        Builder setRole(String role);

        Builder setPubkey(String pubkey);

        Builder setAppPubkey(String appPubkey);

        Builder setAppPackageName(String appPackageName);

        Builder setAppLabel(String appLabel);
    }

    interface AuthResponseBuilder<Builder> {
        Builder setAuthId(int authId);
        Builder setAuthUserId(int authUserId);
        Builder setAuthorized(boolean authorized);
        Builder setData(Object data);
    }

    interface AuthRequestBuilder<Builder> {
        Builder setId(int id);
        Builder setUserId(int userId);
        Builder setCreateTime(long createTime);
        Builder setBackground(boolean background);
        Builder setPluginId(String pluginId);
        Builder setTxId(String txId);
        Builder setType(String type);
        Builder setComponentClassName(String componentClassName);
        Builder setComponentPackageName(String componentPackageName);
    }

    interface AddUserRequestBuilder<Builder> {
        Builder setRole(String role);

        Builder setAppPubkey(String appPubkey);

        Builder setAppPackageName(String appPackageName);

        Builder setAppLabel(String appLabel);
    }

    interface NewAddressRequestBuilder<Builder> {
        Builder setType(int type);
    }

    interface NewAddressBuilder<Builder> {
        Builder setAddress(String address);
    }

    interface GetRequestLongBuilder<Builder> {
        Builder setId(Long id);
        Builder setNoAuth(boolean noAuth);
        Builder setSubscribe(boolean subscribe);
    }

    interface GetRequestStringBuilder<Builder> {
        Builder setId(String id);
        Builder setNoAuth(boolean noAuth);
        Builder setSubscribe(boolean subscribe);
    }

    interface ContactBuilder<RouteHint, Builder> {
        Builder setId(long id);
        Builder setUserId(int userId);
        Builder setAuthUserId(int authUserId);
        Builder setCreateTime(long createTime);

        Builder setPubkey(String pubkey);
        Builder setName(String name);
        Builder setDescription(String description);
        Builder setUrl(String url);
        Builder setRouteHints(ImmutableList<RouteHint> routeHints);
        Builder setFeatures(ImmutableList<Integer> features);
    }

    interface AddContactRequestBuilder<Builder>{
        Builder setPubkey(String pubkey);
        Builder setName(String name);
        Builder setDescription(String description);
        Builder setUrl(String url);
    }

    interface AddAppContactRequestBuilder<Builder> {
    }

    interface ListPageBuilder<Builder> {
        Builder setCount(int count);
        Builder setAfterId(long afterId);
        Builder setBeforeId(long beforeId);
        Builder setAroundId(long aroundId);
    }

    interface ListInvoicesRequestBuilder<Builder> {
        // concrete ListPage setter is declared in the WalletData
        // Builder setPage(WalletDataDecl.ListPage page);

        Builder setOnlyOwn(boolean onlyOwn);
        Builder setNoAuth(boolean noAuth);
        Builder setEnablePaging(boolean enablePaging);

        Builder setUserId(int userId);
        Builder setInvoiceId(long invoiceId);
        Builder setTxId(String txId);
        Builder setPreimageHex(String preimageHex);
        Builder setPreimageHashHex(String preimageHashHex);
        Builder setAuthUserId(int authUserId);
        Builder setCreateFrom(long createFrom);
        Builder setCreateTill(long createTill);
        Builder setSettleFrom(long settleFrom);
        Builder setSettleTill(long settleTill);
        Builder setStates(ImmutableList<Integer> states);
        Builder setDescription(String description);
        Builder setPurpose(String purpose);
        Builder setSort(String sort);
        Builder setSortDesc(boolean sortDesc);
    }

    interface ListPaymentsRequestBuilder<Builder> {
        Builder setOnlyOwn(boolean onlyOwn);
        Builder setNoAuth(boolean noAuth);
        Builder setEnablePaging(boolean enablePaging);

        Builder setUserId(int userId);
        Builder setType(int type);
        Builder setContactId(long contactId);
        Builder setSourceId(long sourceId);
        Builder setTimeFrom(long timeFrom);
        Builder setTimeTill(long timeTill);
        Builder setOnlyMessages(boolean onlyMessages);

        Builder setSort(String sort);
        Builder setSortDesc(boolean sortDesc);
    }

    interface ListContactsRequestBuilder<Builder> {
        Builder setOnlyOwn(boolean onlyOwn);
        Builder setNoAuth(boolean noAuth);
        Builder setEnablePaging(boolean enablePaging);

        Builder setUserId(int userId);
        Builder setSort(String sort);
        Builder setSortDesc(boolean sortDesc);
    }

    interface ListResultTmplBuilder<Data, Builder> {
        Builder setItems(ImmutableList<Data> items);
        Builder setCount(int count);
        Builder setPosition(int position);
    }

    interface ListContactsPrivilegeBuilder<Builder> {
        Builder setId(long id);
        Builder setUserId(int userId);
        Builder setAuthUserId(int authUserId);
        Builder setCreateTime(long createTime);
    }

    interface ContactPaymentsPrivilegeBuilder<Builder> {
        Builder setId(long id);
        Builder setUserId(int userId);
        Builder setAuthUserId(int authUserId);
        Builder setCreateTime(long createTime);
        Builder setContactId(long contactId);
    }

    interface SubscribeRequestBuilder<Builder> {
        Builder setNoAuth(boolean noAuth);
        Builder setOnlyOwn(boolean onlyOwn);
    }

    interface WalletBalanceBuilder<Builder> {
        Builder setTotalBalance(long totalBalance);
        Builder setConfirmedBalance(long confirmedBalance);
        Builder setUnconfirmedBalance(long unconfirmedBalance);
    }

    interface ChannelBalanceBuilder<Builder> {
        Builder setBalance(long balance);
        Builder setPendingOpenBalance(long pendingOpenBalance);
    }

    interface WalletInfoBuilder<Builder> {
        Builder setIdentityPubkey(String identityPubkey);
        Builder setAlias(String alias);
        Builder setNumPendingChannels(int numPendingChannels);
        Builder setNumActiveChannels(int numActiveChannels);
        Builder setNumPeers(int numPeers);
        Builder setBlockHeight(int blockHeight);
        Builder setBlockHash(String blockHash);
        Builder setSyncedToChain(boolean syncedToChain);
        Builder setUris(ImmutableList<String> uris);
        Builder setBestHeaderTimestamp(long bestHeaderTimestamp);
        Builder setLndVersion(String lndVersion);
        Builder setNumInactiveChannels(int numInactiveChannels);
        Builder setColor(String color);
        Builder setSyncedToGraph(boolean syncedToGraph);
    }

    interface AddInvoiceRequestBuilder<Builder> {
        Builder setPreimageHex(String preimageHex);
        Builder setValueSat(long valueSat);
        Builder setDescription(String description);
        Builder setDescriptionHashHex(String descriptionHashHex);
        Builder setFallbackAddr(String fallbackAddr);
        Builder setExpiry(long expiry);
        Builder setPurpose(String purpose);
    }

    interface InvoiceBuilder<Builder> {
        Builder setId(long id);
        Builder setTxId(String txId);
        Builder setUserId(int userId);
        Builder setAuthUserId(int authUserId);
        Builder setPurpose(String purpose);
        Builder setDescription(String description);
        Builder setPreimageHex(String preimageHex);
        Builder setPreimageHashHex(String preimageHashHex);
        Builder setValueSat(long valueSat);
        Builder setCreateTime(long createTime);
        Builder setSettleTime(long settleTime);
        Builder setPaymentRequest(String paymentRequest);
        Builder setDescriptionHashHex(String descriptionHashHex);
        Builder setExpiry(long expiry);
        Builder setFallbackAddr(String fallbackAddr);
        Builder setCltvExpiry(long cltvExpiry);
        Builder setIsPrivate(boolean isPrivate);
        Builder setAddIndex(long addIndex);
        Builder setSettleIndex(long settleIndex);
        Builder setAmountPaidMsat(long amountPaidMsat);
        Builder setState(int state);
        Builder setHtlcsCount(int htlcsCount);
        Builder setIsKeysend(boolean isKeysend);
        Builder setFeatures(ImmutableList<Integer> features);
    }

    interface InvoiceHTLCBuilder<Builder> {
        Builder setId(long id);
        Builder setInvoiceId(long invoiceId);
        Builder setChanId(long chanId);
        Builder setHtlcIndex(long htlcIndex);
        Builder setAmountMsat(long amountMsat);
        Builder setAcceptHeight(int acceptHeight);
        Builder setAcceptTime(long acceptTime);
        Builder setResolveTime(long resolveTime);
        Builder setExpiryHeight(int expiryHeight);
        Builder setState(int state);
        Builder setMessage(String message);
        Builder setSenderPubkey(String senderPubkey);
        Builder setSenderTime(long senderTime);
        Builder setCustomRecords(ImmutableMap<Long, byte[]> records);
    }

    interface ChannelBuilder<Builder> {
        Builder setId(long id);
        Builder setUserId(int userId);
        Builder setTxId(String txId);
        Builder setAuthUserId(int authUserId);
        Builder setDescription(String description);
        Builder setTargetConf(int targetConf);
        Builder setSatPerByte(long satPerByte);
        Builder setMinHtlcMsat(long minHtlcMsat);
        Builder setMinConfs(int minConfs);
        Builder setSpendUnconfirmed(boolean spendUnconfirmed);
        Builder setChainHashHex(String chainHashHex);
        Builder setClosingTxHashHex(String closingTxHashHex);
        Builder setCloseHeight(int closeHeight);
        Builder setSettledBalance(long settledBalance);
        Builder setTimeLockedBalance(long timeLockedBalance);
        Builder setCloseType(int closeType);
        Builder setTries(int tries);
        Builder setMaxTries(int maxTries);
        Builder setMaxTryTime(long maxTryTime);
        Builder setLastTryTime(long lastTryTime);
        Builder setNextTryTime(long nextTryTime);
        Builder setState(int state);
        Builder setErrorCode(String errorCode);
        Builder setErrorMessage(String errorMessage);
        Builder setCreateTime(long createTime);
        Builder setOpenTime(long openTime);
        Builder setCloseTime(long closeTime);
        Builder setActive(boolean active);
        Builder setRemotePubkey(String remotePubkeyHex);
        Builder setChannelPoint(String channelPoint);
        Builder setChanId(long chanId);
        Builder setCapacity(long capacity);
        Builder setLocalBalance(long localBalance);
        Builder setRemoteBalance(long remoteBalance);
        Builder setCommitFee(long commitFee);
        Builder setCommitWeight(long commitWeight);
        Builder setFeePerKw(long feePerKw);
        Builder setUnsettledBalance(long unsettledBalance);
        Builder setTotalSatoshisSent(long totalSatoshisSent);
        Builder setTotalSatoshisReceived(long totalSatoshisReceived);
        Builder setNumUpdates(long numUpdates);
        Builder setCsvDelay(int csvDelay);
        Builder setIsPrivate(boolean isPrivate);
        Builder setInitiator(boolean initiator);
        Builder setChanStatusFlags(String chanStatusFlags);
        Builder setLocalChanReserveSat(long localChanReserveSat);
        Builder setRemoteChanReserveSat(long remoteChanReserveSat);
        Builder setStaticRemoteKey(boolean staticRemoteKey);
        Builder setLifetime(long lifetime);
        Builder setUptime(long uptime);
    }

    interface OpenChannelRequestBuilder<Builder> {
        Builder setDescription(String description);
        Builder setNodePubkey(String nodePubkeyHex);
        Builder setLocalFundingAmount(long localFundingAmount);
        Builder setPushSat(long pushSat);
        Builder setTargetConf(int targetConf);
        Builder setSatPerByte(long satPerByte);
        Builder setIsPrivate(boolean isPrivate);
        Builder setMinHtlcMsat(long minHtlcMsat);
        Builder setRemoteCsvDelay(int remoteCsvDelay);
        Builder setMinConfs(int minConfs);
        Builder setSpendUnconfirmed(boolean spendUnconfirmed);
    }

    interface SendPaymentRequestBuilder<RouteHint, Builder> {
        Builder setPurpose(String purpose);
        Builder setInvoiceDescription(String invoiceDescription);
        Builder setInvoiceDescriptionHashHex(String invoiceDescriptionHashHex);
        Builder setInvoiceFallbackAddr(String invoiceFallbackAddr);
        Builder setInvoiceTimestamp(long invoiceTimestamp);
        Builder setExpiry(long expiry);
        Builder setMaxTries(int maxTries);
        Builder setDestPubkey(String destPubkeyHex);
        Builder setValueSat(long valueSat);
        Builder setPaymentHashHex(String paymentHashHex);
        Builder setPaymentRequest(String paymentRequest);
        Builder setFinalCltvDelta(int finalCltvDelta);
        Builder setFeeLimitFixedMsat(long feeLimitFixedMsat);
        Builder setFeeLimitPercent(long feeLimitPercent);
        Builder setOutgoingChanId(long outgoingChanId);
        Builder setCltvLimit(int cltvLimit);
//        Builder setDestTlv(ImmutableMap<Long, byte[]> destTlv);
        Builder setRouteHints(ImmutableList<RouteHint> routeHints);
        Builder setFeatures(ImmutableList<Integer> features);
        Builder setMessage(String message);
        Builder setIncludeSenderPubkey(boolean inc);
        Builder setContactId(long cid);
        Builder setIsKeysend(boolean isKeysend);
        Builder setNoAuth(boolean noAuth);
    }

    interface HTLCAttemptBuilder<Builder> {
        Builder setId(long id);
        Builder setSendPaymentId(long sendPaymentId);
        Builder setState(int state);
        Builder setAttemptTime(long time);
        Builder setResolveTime(long time);
        Builder setTotalAmountMsat(long msat);
        Builder setTotalFeeMsat(long msat);
        Builder setTotalTimeLock(int totalTimeLock);
        Builder setDestCustomRecords(ImmutableMap<Long,byte[]> records);
    }

    interface PaymentBuilder<SendPayment, HTLCAttempt, Invoice, InvoiceHTLC, Builder> {
        Builder setId(long id);
        Builder setType(int type);
        Builder setSourceId(long sourceId);
        Builder setSourceHTLCId(long sourceHTLCId);

        Builder setSendPayments(ImmutableMap<Long,SendPayment> sendPayments);
        Builder setHTLCAttempts(ImmutableMap<Long,HTLCAttempt> attempts);
        Builder setInvoices(ImmutableMap<Long,Invoice> invoices);
        Builder setInvoiceHTLCs(ImmutableMap<Long,InvoiceHTLC> htlcs);

        Builder setUserId(int userId);
        Builder setTime(long time);
        Builder setPeerPubkey(String peerPubkey);
        Builder setMessage(String message);
    }

    interface SendPaymentBuilder<RouteHint, Builder> {
        Builder setId(long id);
        Builder setTxId(String txId);
        Builder setUserId(int userId);
        Builder setAuthUserId(int authUserId);
        Builder setPurpose(String purpose);
        Builder setTries(int tries);
        Builder setMaxTries(int maxTries);
        Builder setMaxTryTime(long maxTryTime);
        Builder setLastTryTime(long lastTryTime);
        Builder setNextTryTime(long nextTryTime);
        Builder setState(int state);
        Builder setErrorCode(String errorCode);
        Builder setErrorMessage(String errorMessage);
        Builder setInvoiceDescription(String invoiceDescription);
        Builder setInvoiceDescriptionHashHex(String invoiceDescriptionHashHex);
        Builder setInvoiceFallbackAddr(String invoiceFallbackAddr);
        Builder setPaymentAddrHex(String paymentAddrHex);
        Builder setInvoiceTimestamp(long invoiceTimestamp);
        Builder setInvoiceExpiry(long invoiceExpiry);
        Builder setDestPubkey(String destPubkeyHex);
        Builder setValueMsat(long valueMsat);
        Builder setTotalValueMsat(long totalValueMsat);
        Builder setPaymentHashHex(String paymentHashHex);
        Builder setPaymentRequest(String paymentRequest);
        Builder setFinalCltvDelta(int finalCltvDelta);
        Builder setFeeLimitFixedMsat(long feeLimitFixedMsat);
        Builder setFeeLimitPercent(long feeLimitPercent);
        Builder setOutgoingChanId(long outgoingChanId);
        Builder setCltvLimit(int cltvLimit);
        Builder setDestCustomRecords(ImmutableMap<Long, byte[]> records);
        Builder setPaymentError(String paymentError);
        Builder setPaymentPreimageHex(String paymentPreimageHex);
        Builder setRouteHints(ImmutableList<RouteHint> routeHints);
        Builder setFeatures(ImmutableList<Integer> features);
        Builder setCreateTime(long createTime);
        Builder setSendTime(long sendTime);
        Builder setFeeMsat(long feeMsat);
        Builder setContactPubkey(String pubkey);
        Builder setMessage(String message);
        Builder setSenderPubkey(String pubkey);
        Builder setIsKeysend(boolean isKeysend);
    }

    interface ConnectPeerRequestBuilder<Builder>{
        Builder setPubkey(String pubkey);
        Builder setHost(String host);
        Builder setPerm(boolean perm);
    }

    interface AddContactInvoiceResponseBuilder<Builder>{
        Builder setPaymentRequest(String paymentRequest);
    }

    interface RouteHintBuilder<HopHint, Builder> {
        Builder setId(long id);
        Builder setParentId(String parentId);
        Builder setHopHints(ImmutableList<HopHint> hopHints);
    }

    interface HopHintBuilder<Builder> {
        Builder setId(long id);
        Builder setRouteHintId(long routeHintId);
        Builder setIndex(int index);
        Builder setNodeId(String nodeId);
        Builder setChanId(long chanId);
        Builder setFeeBaseMsat(int feeBaseMsat);
        Builder setFeeProportionalMillionths(int feeProportionalMillionths);
        Builder setCltvExpiryDelta(int cltvExpiryDelta);
    }

    interface RoutingPolicyBuilder<Builder> {
        Builder setId(long id);
        Builder setChannelId(long channelId);
        Builder setReverse(boolean reverse);
        Builder setTimeLockDelta(int timeLockDelta);
        Builder setMinHtlc(long minHtlc);
        Builder setFeeBaseMsat(long feeBaseMsat);
        Builder setFeeRateMilliMsat(long feeRateMilliMsat);
        Builder setDisabled(boolean disabled);
        Builder setMaxHtlcMsat(long maxHtlcMsat);
        Builder setLastUpdate(int lastUpdate);
    }

    public interface ChannelEdgeBuilder<RoutingPolicy,Builder> {
        Builder setId(long id);
        Builder setChannelId(long channelId);
        Builder setChanPoint(String chanPoint);
        Builder setNode1Pubkey(String node1Pubkey);
        Builder setNode2Pubkey(String node2Pubkey);
        Builder setCapacity(long capacity);
        Builder setNode1Policy(RoutingPolicy node1Policy);
        Builder setNode2Policy(RoutingPolicy node2Policy);
    }

    public interface LightningNodeBuilder<Builder> {
        Builder setId(long id);
        Builder setLastUpdate(int lastUpdate);
        Builder setPubkey(String pubKey);
        Builder setAlias(String alias);
        Builder setColor(String color);
        Builder setFeatures(ImmutableList<Integer> features);
    }

}
