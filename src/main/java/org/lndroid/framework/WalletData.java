package org.lndroid.framework;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.lndroid.framework.common.AutoValueClass;

import java.util.List;

public final class WalletData {

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_Error.class)
    public static abstract class Error implements WalletDataDecl.Error {

        public static Error create(String c, String m) {
            return builder()
                    .setCode(c)
                    .setMessage(m)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_Error.Builder();
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.Error,
                WalletDataBuilders.IBuilder<Error>,
                WalletDataBuilders.ErrorBuilder<Builder> {
        }
    }

    public static final int WALLET_STATE_OK = 1;
    public static final int WALLET_STATE_INIT = 2;
    public static final int WALLET_STATE_AUTH = 3;
    public static final int WALLET_STATE_ERROR = 4;

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_WalletState.class)
    public static abstract class WalletState implements WalletDataDecl.WalletState {

        public static Builder builder() {
            return new AutoValue_WalletData_WalletState.Builder()
                    .setState(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.WalletState,
                WalletDataBuilders.IBuilder<WalletState>,
                WalletDataBuilders.WalletStateBuilder<Builder> {
        }
    }

    // NOTE: the following struct aren't auto-values for these reasons:
    // 1. byte[] is mutable, and using it in immutable type is not good, and
    //    there is no good immutable byte array in Java (am I right?)
    // 2. considering workarounds of the 1. are complex, and these
    //    structs are only used rarely and only by in-process clients
    //    and thus don't need serialization and are not stored in db,
    //    for now I'm leaving them as is.
    public static class GenSeedRequest {
        public byte[] aezeedPassphrase;
        public byte[] seedEntropy;
    }

    public static class GenSeedResponse {
        public List<String> cipherSeedMnemonic;
    }

    public static class UnlockWalletRequest {
        public byte[] walletPassword;
    }

    public static class UnlockWalletResponse {
    }

    public static class InitWalletRequest {
        public byte[] aezeedPassphrase;
        public List<String> cipherSeedMnemonic;
        public byte[] walletPassword;
    }

    public static class InitWalletResponse {
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_UserIdentity.class)
    public static abstract class UserIdentity implements WalletDataDecl.UserIdentity {

        public final boolean isValid() {
            return userId() != 0
                    || (appPackageName() != null && !appPackageName().isEmpty()
                    && appPubkey() != null && !appPubkey().isEmpty()
            );
        }

        public static UserIdentity create(
                int userId,
                String appPackageName,
                String appPubkey
        ) {
            return builder()
                    .setUserId(userId)
                    .setAppPackageName(appPackageName)
                    .setAppPubkey(appPubkey)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_UserIdentity.Builder()
                    .setUserId(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.UserIdentity,
                WalletDataBuilders.IBuilder<UserIdentity>,
                WalletDataBuilders.UserIdentityBuilder<Builder> {

            abstract UserIdentity autoBuild();

            @Override
            public UserIdentity build() {
                UserIdentity ui = autoBuild();
                if (!ui.isValid())
                    throw new IllegalStateException("Bad user identity");
                return ui;
            }
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_AuthResponse.class)
    public static abstract class AuthResponse implements WalletDataDecl.AuthResponse {

        public static AuthResponse create(
                int authId,
                int authUserId,
                boolean authorized,
                Object data
        ) {
            return builder()
                    .setAuthId(authId)
                    .setAuthUserId(authUserId)
                    .setAuthorized(authorized)
                    .setData(data)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_AuthResponse.Builder()
                    .setAuthId(0)
                    .setAuthUserId(0)
                    .setAuthorized(false);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.AuthResponse,
                WalletDataBuilders.IBuilder<AuthResponse>,
                WalletDataBuilders.AuthResponseBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_AuthRequest.class)
    public static abstract class AuthRequest implements WalletDataDecl.AuthRequest {

        public static AuthRequest create(
                int id,
                int userId,
                long createTime,
                boolean background,
                String pluginId,
                String txId,
                String type,
                String componentClassName,
                String componentPackageName
        ) {
            return builder()
                    .setId(id)
                    .setUserId(userId)
                    .setCreateTime(createTime)
                    .setBackground(background)
                    .setPluginId(pluginId)
                    .setTxId(txId)
                    .setType(type)
                    .setComponentClassName(componentClassName)
                    .setComponentPackageName(componentPackageName)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_AuthRequest.Builder()
                    .setId(0)
                    .setUserId(0)
                    .setCreateTime(0)
                    .setBackground(false);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.AuthRequest,
                WalletDataBuilders.IBuilder<AuthRequest>,
                WalletDataBuilders.AuthRequestBuilder<Builder> {
        }
    }

    public static final int ROOT_USER_ID = 1;

    public static final String USER_ROLE_ROOT = "root";
    public static final String USER_ROLE_USER = "user";
    public static final String USER_ROLE_APP = "app";
    public static final String USER_ROLE_GUEST = "guest";

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_User.class)
    public static abstract class User implements WalletDataDecl.User {

        public boolean isValid() {
            return role() != null && !role().equals("")
                    && pubkey() != null && !pubkey().equals("")
                    && (!role().equals(USER_ROLE_APP)
                    || (appPubkey() != null && !appPubkey().equals("")
                    && appPackageName() != null && !appPackageName().equals("")
                    && appLabel() != null && !appLabel().equals("")
            )
            );
        }

        // some helpers
        public boolean isRoot() {
            return role().equals(USER_ROLE_ROOT);
        }

        public boolean isApp() {
            return role().equals(USER_ROLE_APP);
        }

        public static User create(
                int id, int authUserId, long createTime, String role, String pubkey, String appPubkey, String appPackageName, String appLabel) {
            return builder()
                    .setId(id)
                    .setAuthUserId(authUserId)
                    .setCreateTime(createTime)
                    .setRole(role)
                    .setPubkey(pubkey)
                    .setAppPubkey(appPubkey)
                    .setAppPackageName(appPackageName)
                    .setAppLabel(appLabel)
                    .autoBuild();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_User.Builder()
                    .setId(0)
                    .setAuthUserId(0)
                    .setCreateTime(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder
                implements WalletDataBuilders.IBuilder<User>, WalletDataBuilders.UserBuilder<Builder> {
            public abstract User autoBuild();

            @Override
            public User build() {
                User u = autoBuild();
                if (!u.isValid())
                    throw new IllegalArgumentException("Bad user");
                return u;
            }
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_AddUserRequest.class)
    public static abstract class AddUserRequest implements WalletDataDecl.AddUserRequest {

        public final boolean isValid() {
            return role() != null && !role().equals("")
                    && (!role().equals(USER_ROLE_APP)
                    || (appPubkey() != null && !appPubkey().equals("")
                    && appPackageName() != null && !appPackageName().equals("")
                    && appLabel() != null && !appLabel().equals("")
            )
            );
        }

        // Room uses this factory method to create objects, as it looks like
        // it doesn't support auto value builders.
        public static AddUserRequest create(
                String role, String appPubkey, String appPackageName, String appLabel) {
            return builder()
                    .setRole(role)
                    .setAppPubkey(appPubkey)
                    .setAppPackageName(appPackageName)
                    .setAppLabel(appLabel)
                    .autoBuild();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_AddUserRequest.Builder();
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder
                implements WalletDataBuilders.IBuilder<AddUserRequest>, WalletDataBuilders.AddUserRequestBuilder<Builder> {
            public abstract AddUserRequest autoBuild();

            @Override
            public AddUserRequest build() {
                AddUserRequest r = autoBuild();
                if (!r.isValid())
                    throw new IllegalArgumentException("Bad add user request");
                return r;
            }
        }
    }

    public static final int NEW_ADDRESS_WITNESS_PUBKEY_HASH = 0;
    public static final int NEW_ADDRESS_NESTED_PUBKEY_HASH = 1;

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_NewAddressRequest.class)
    public static abstract class NewAddressRequest implements WalletDataDecl.NewAddressRequest {

        public static NewAddressRequest create(int type) {
            return builder().setType(type).build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_NewAddressRequest.Builder()
                    .setType(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder
                implements WalletDataBuilders.IBuilder<NewAddressRequest>, WalletDataBuilders.NewAddressRequestBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_NewAddress.class)
    public static abstract class NewAddress implements WalletDataDecl.NewAddress {

        public static NewAddress create(String address) {
            return builder().setAddress(address).build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_NewAddress.Builder();
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder
                implements WalletDataBuilders.IBuilder<NewAddress>, WalletDataBuilders.NewAddressBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_GetRequestLong.class)
    public static abstract class GetRequestLong implements WalletDataDecl.GetRequestTmpl<Long> {

        public static Builder builder() {
            return new AutoValue_WalletData_GetRequestLong.Builder()
                    // defaults
                    .setNoAuth(false)
                    .setSubscribe(false);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder
                implements WalletDataBuilders.IBuilder<GetRequestLong>, WalletDataBuilders.GetRequestLongBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_GetRequestString.class)
    public static abstract class GetRequestString implements WalletDataDecl.GetRequestTmpl<String> {

        public static Builder builder() {
            return new AutoValue_WalletData_GetRequestString.Builder()
                    // defaults
                    .setNoAuth(false)
                    .setSubscribe(false);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder
                implements WalletDataBuilders.IBuilder<GetRequestString>, WalletDataBuilders.GetRequestStringBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_SubscribeRequest.class)
    public static abstract class SubscribeRequest implements WalletDataDecl.SubscribeRequest {

        public static Builder builder() {
            return new AutoValue_WalletData_SubscribeRequest.Builder()
                    // defaults
                    .setNoAuth(false)
                    .setOnlyOwn(false);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.SubscribeRequest,
                WalletDataBuilders.IBuilder<SubscribeRequest>,
                WalletDataBuilders.SubscribeRequestBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_WalletBalance.class)
    public static abstract class WalletBalance implements WalletDataDecl.WalletBalance {

        public static WalletBalance create(
                long totalBalance, long confirmedBalance, long unconfirmedBalance) {
            return builder()
                    .setTotalBalance(totalBalance)
                    .setConfirmedBalance(confirmedBalance)
                    .setUnconfirmedBalance(unconfirmedBalance)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_WalletBalance.Builder()
                    .setTotalBalance(0)
                    .setConfirmedBalance(0)
                    .setUnconfirmedBalance(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.WalletBalance,
                WalletDataBuilders.IBuilder<WalletBalance>,
                WalletDataBuilders.WalletBalanceBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_WalletBalance.class)
    public static abstract class ChannelBalance implements WalletDataDecl.ChannelBalance {

        public static ChannelBalance create(
                long balance, long pendingOpenBalance) {
            return builder()
                    .setBalance(balance)
                    .setPendingOpenBalance(pendingOpenBalance)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_ChannelBalance.Builder()
                    .setBalance(0)
                    .setPendingOpenBalance(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.ChannelBalance,
                WalletDataBuilders.IBuilder<ChannelBalance>,
                WalletDataBuilders.ChannelBalanceBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_WalletInfo.class)
    public static abstract class WalletInfo implements WalletDataDecl.WalletInfo {

        public static WalletInfo create(
                String identityPubkey,
                String alias,
                int numPendingChannels,
                int numActiveChannels,
                int numPeers,
                int blockHeight,
                String blockHash,
                boolean syncedToChain,
                ImmutableList<String> uris,
                long bestHeaderTimestamp,
                String lndVersion,
                int numInactiveChannels,
                String color,
                boolean syncedToGraph
        ) {
            return builder()
                    .setIdentityPubkey(identityPubkey)
                    .setAlias(alias)
                    .setNumPendingChannels(numPendingChannels)
                    .setNumActiveChannels(numActiveChannels)
                    .setNumPeers(numPeers)
                    .setBlockHeight(blockHeight)
                    .setBlockHash(blockHash)
                    .setSyncedToChain(syncedToChain)
                    .setUris(uris)
                    .setBestHeaderTimestamp(bestHeaderTimestamp)
                    .setLndVersion(lndVersion)
                    .setNumInactiveChannels(numInactiveChannels)
                    .setColor(color)
                    .setSyncedToGraph(syncedToGraph)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_WalletInfo.Builder()
                    .setNumPendingChannels(0)
                    .setNumActiveChannels(0)
                    .setNumPeers(0)
                    .setBlockHeight(0)
                    .setSyncedToChain(false)
                    .setBestHeaderTimestamp(0)
                    .setNumInactiveChannels(0)
                    .setSyncedToGraph(false);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.WalletInfo,
                WalletDataBuilders.IBuilder<WalletInfo>,
                WalletDataBuilders.WalletInfoBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_AddInvoiceRequest.class)
    public static abstract class AddInvoiceRequest implements WalletDataDecl.AddInvoiceRequest {

        public static AddInvoiceRequest create(
                String preimageHex,
                long valueSat,
                String description,
                String descriptionHashHex,
                String fallbackAddr,
                long expiry,
                String purpose
        ) {
            return builder()
                    .setPreimageHex(preimageHex)
                    .setValueSat(valueSat)
                    .setDescription(description)
                    .setDescriptionHashHex(descriptionHashHex)
                    .setFallbackAddr(fallbackAddr)
                    .setExpiry(expiry)
                    .setPurpose(purpose)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_AddInvoiceRequest.Builder()
                    .setValueSat(0)
                    .setExpiry(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.AddInvoiceRequest,
                WalletDataBuilders.IBuilder<AddInvoiceRequest>,
                WalletDataBuilders.AddInvoiceRequestBuilder<Builder> {
        }
    }

    // used for paging through List* method results
    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ListPage.class)
    public static abstract class ListPage implements WalletDataDecl.ListPage {

        public static ListPage create(
                int count,
                long afterId,
                long beforeId,
                long aroundId
        ) {
            return builder()
                    .setCount(count)
                    .setAfterId(afterId)
                    .setBeforeId(beforeId)
                    .setAroundId(aroundId)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_ListPage.Builder()
                    .setCount(0)
                    .setAfterId(0)
                    .setBeforeId(0)
                    .setAroundId(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.ListPage,
                WalletDataBuilders.IBuilder<ListPage>,
                WalletDataBuilders.ListPageBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ListInvoicesResult.class)
    public static abstract class ListInvoicesResult implements WalletDataDecl.ListResultTmpl<Invoice> {

        public static ListInvoicesResult create(
                ImmutableList<Invoice> items,
                int count,
                int position
        ) {
            return builder()
                    .setItems(items)
                    .setCount(count)
                    .setPosition(position)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_ListInvoicesResult.Builder()
                    .setCount(0)
                    .setPosition(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.ListResultTmpl<Invoice>,
                WalletDataBuilders.IBuilder<ListInvoicesResult>,
                WalletDataBuilders.ListResultTmplBuilder<Invoice, Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ListPaymentsResult.class)
    public static abstract class ListPaymentsResult implements WalletDataDecl.ListResultTmpl<Payment> {

        public static ListPaymentsResult create(
                ImmutableList<Payment> items,
                int count,
                int position
        ) {
            return builder()
                    .setItems(items)
                    .setCount(count)
                    .setPosition(position)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_ListPaymentsResult.Builder()
                    .setCount(0)
                    .setPosition(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.ListResultTmpl<Payment>,
                WalletDataBuilders.IBuilder<ListPaymentsResult>,
                WalletDataBuilders.ListResultTmplBuilder<Payment, Builder> {
        }
    }

    // base ListRequest for a generic plugin implementation
    public static abstract class ListRequestBase implements WalletDataDecl.ListRequestBase {

        // required
        @Override
        @Nullable
        public abstract ListPage page();

        public abstract ListRequestBase withPage(ListPage page);
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ListInvoicesRequest.class)
    public static abstract class ListInvoicesRequest extends ListRequestBase
            implements WalletDataDecl.ListInvoicesRequest {

        // FIXME add static factory method

        public static Builder builder() {
            return new AutoValue_WalletData_ListInvoicesRequest.Builder()
                    .setUserId(0)
                    .setInvoiceId(0)
                    .setAuthUserId(0)
                    .setCreateFrom(0)
                    .setCreateTill(0)
                    .setSettleFrom(0)
                    .setSettleTill(0)
                    .setOnlyOwn(false)
                    .setNoAuth(false)
                    .setEnablePaging(false)
                    .setSortDesc(false)
                    ;
        }

        public abstract Builder toBuilder();

        @Override
        public ListInvoicesRequest withPage(ListPage page) {
            return toBuilder().setPage(page).build();
        }

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.ListInvoicesRequest,
                WalletDataBuilders.IBuilder<ListInvoicesRequest>,
                WalletDataBuilders.ListInvoicesRequestBuilder<Builder> {
            public abstract Builder setPage(ListPage page);
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ListPaymentsRequest.class)
    public static abstract class ListPaymentsRequest extends ListRequestBase implements WalletDataDecl.ListPaymentsRequest {

        // FIXME add static factory method

        public static Builder builder() {
            return new AutoValue_WalletData_ListPaymentsRequest.Builder()
                    .setUserId(0)
                    .setType(0)
                    .setContactId(0)
                    .setSourceId(0)
                    .setTimeFrom(0)
                    .setTimeTill(0)
                    .setOnlyMessages(false)
                    .setOnlyOwn(false)
                    .setNoAuth(false)
                    .setEnablePaging(false)
                    .setSortDesc(false);
        }

        public abstract Builder toBuilder();

        public ListPaymentsRequest withPage(ListPage page) {
            return toBuilder().setPage(page).build();
        }

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.ListPaymentsRequest,
                WalletDataBuilders.IBuilder<ListPaymentsRequest>,
                WalletDataBuilders.ListPaymentsRequestBuilder<Builder> {
            public abstract Builder setPage(ListPage page);
        }
    }

    public static final int INVOICE_STATE_OPEN = 0;
    public static final int INVOICE_STATE_SETTLED = 1;
    public static final int INVOICE_STATE_CANCELED = 2;
    public static final int INVOICE_STATE_ACCEPTED = 3;

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_Invoice.class)
    public static abstract class Invoice implements WalletDataDecl.EntityBase, WalletDataDecl.Invoice {

        public static Invoice create(
                long id,
                String txId,
                int userId,
                int authUserId,
                String purpose,
                String description,
                String preimageHex,
                String preimageHashHex,
                long valueSat,
                long createTime,
                long settleTime,
                String paymentRequest,
                String descriptionHashHex,
                long expiry,
                String fallbackAddr,
                long cltvExpiry,
                boolean isPrivate,
                long addIndex,
                long settleIndex,
                long amountPaidMsat,
                int state,
                int htlcsCount,
                boolean isKeysend,
                ImmutableList<Integer> features
        ) {
            return builder()
                    .setId(id)
                    .setTxId(txId)
                    .setUserId(userId)
                    .setAuthUserId(authUserId)
                    .setPurpose(purpose)
                    .setDescription(description)
                    .setPreimageHex(preimageHex)
                    .setPreimageHashHex(preimageHashHex)
                    .setValueSat(valueSat)
                    .setCreateTime(createTime)
                    .setSettleTime(settleTime)
                    .setPaymentRequest(paymentRequest)
                    .setDescriptionHashHex(descriptionHashHex)
                    .setExpiry(expiry)
                    .setFallbackAddr(fallbackAddr)
                    .setCltvExpiry(cltvExpiry)
                    .setIsPrivate(isPrivate)
                    .setAddIndex(addIndex)
                    .setSettleIndex(settleIndex)
                    .setAmountPaidMsat(amountPaidMsat)
                    .setState(state)
                    .setHtlcsCount(htlcsCount)
                    .setIsKeysend(isKeysend)
                    .setFeatures(features)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_Invoice.Builder()
                    .setId(0)
                    .setUserId(0)
                    .setAuthUserId(0)
                    .setValueSat(0)
                    .setCreateTime(0)
                    .setSettleTime(0)
                    .setExpiry(0)
                    .setCltvExpiry(0)
                    .setIsPrivate(false)
                    .setAddIndex(0)
                    .setSettleIndex(0)
                    .setAmountPaidMsat(0)
                    .setState(INVOICE_STATE_OPEN)
                    .setHtlcsCount(0)
                    .setIsKeysend(false)
                    ;
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.Invoice,
                WalletDataBuilders.IBuilder<Invoice>,
                WalletDataBuilders.InvoiceBuilder<Builder> {
        }
    }

    public static final int INVOICE_HTLC_STATE_ACCEPTED = 0;
    public static final int INVOICE_HTLC_STATE_SETTLED = 1;
    public static final int INVOICE_HTLC_STATE_CANCELED = 2;

    /// Details of an HTLC that paid to an invoice
    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_InvoiceHTLC.class)
    public static abstract class InvoiceHTLC implements WalletDataDecl.EntityBase, WalletDataDecl.InvoiceHTLC {

        public static InvoiceHTLC create(
                long id,
                long invoiceId,
                long chanId,
                long htlcIndex,
                long amountMsat,
                int acceptHeight,
                long acceptTime,
                long resolveTime,
                int expiryHeight,
                int state,
                String message,
                String senderPubkey,
                long senderTime,
                ImmutableMap<Long, byte[]> customRecords
        ) {
            return builder()
                    .setId(id)
                    .setInvoiceId(invoiceId)
                    .setChanId(chanId)
                    .setHtlcIndex(htlcIndex)
                    .setAmountMsat(amountMsat)
                    .setAcceptHeight(acceptHeight)
                    .setAcceptTime(acceptTime)
                    .setResolveTime(resolveTime)
                    .setExpiryHeight(expiryHeight)
                    .setState(state)
                    .setMessage(message)
                    .setSenderPubkey(senderPubkey)
                    .setSenderTime(senderTime)
                    .setCustomRecords(customRecords)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_InvoiceHTLC.Builder()
                    .setId(0)
                    .setInvoiceId(0)
                    .setChanId(0)
                    .setHtlcIndex(0)
                    .setAmountMsat(0)
                    .setAcceptHeight(0)
                    .setAcceptTime(0)
                    .setResolveTime(0)
                    .setExpiryHeight(0)
                    .setState(INVOICE_HTLC_STATE_ACCEPTED)
                    .setSenderTime(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.InvoiceHTLC,
                WalletDataBuilders.IBuilder<InvoiceHTLC>,
                WalletDataBuilders.InvoiceHTLCBuilder<Builder> {
        }
    }

    public static final int HTLC_ATTEMPT_STATE_INFLIGHT = 0;
    public static final int HTLC_ATTEMPT_STATE_SUCCEEDED = 1;
    public static final int HTLC_ATTEMPT_STATE_FAILED = 2;

    /// Details of an HTLC that paid to an invoice
    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_HTLCAttempt.class)
    public static abstract class HTLCAttempt implements WalletDataDecl.EntityBase, WalletDataDecl.HTLCAttempt {

        public static HTLCAttempt create(
                long id,
                long sendPaymentId,
                int state,
                long attemptTime,
                long resolveTime,
                long totalAmountMsat,
                long totalFeeMsat,
                int totalTimeLock,
                ImmutableMap<Long, byte[]> destCustomRecords
        ) {
            return builder()
                    .setId(id)
                    .setSendPaymentId(sendPaymentId)
                    .setState(state)
                    .setAttemptTime(attemptTime)
                    .setResolveTime(resolveTime)
                    .setTotalAmountMsat(totalAmountMsat)
                    .setTotalFeeMsat(totalFeeMsat)
                    .setTotalTimeLock(totalTimeLock)
                    .setDestCustomRecords(destCustomRecords)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_HTLCAttempt.Builder()
                    .setId(0)
                    .setSendPaymentId(0)
                    .setState(HTLC_ATTEMPT_STATE_INFLIGHT)
                    .setAttemptTime(0)
                    .setResolveTime(0)
                    .setTotalAmountMsat(0)
                    .setTotalFeeMsat(0)
                    .setTotalTimeLock(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.HTLCAttempt,
                WalletDataBuilders.IBuilder<HTLCAttempt>,
                WalletDataBuilders.HTLCAttemptBuilder<Builder> {
        }
    }

    // 0 - new, 1 - open, 2 - failed, 3 - opening, 4 - rejected by user, 5 - pending (unconfirmed), 6 - Lost, 7 - Retry
    public static final int CHANNEL_STATE_NEW = 0;
    public static final int CHANNEL_STATE_OPEN = 1;
    public static final int CHANNEL_STATE_CLOSED = 2;
    public static final int CHANNEL_STATE_FAILED = 3; // open failed
    public static final int CHANNEL_STATE_OPENING = 4; // maybe sent OpenChannel to lnd, waiting for reply
    public static final int CHANNEL_STATE_REJECTED = 5;
    public static final int CHANNEL_STATE_PENDING_OPEN = 6; // opened, waiting tx confs
    public static final int CHANNEL_STATE_LOST = 7; // OpenWorker was interrupted and needs StateWorker to sync
    public static final int CHANNEL_STATE_RETRY = 8; // StateWorker didn't find LOST channel, OpenWorker should retry
    public static final int CHANNEL_STATE_PENDING_CLOSE = 9; // closing
    public static final int CHANNEL_STATE_PENDING_FORCE_CLOSE = 10; // force closing
    public static final int CHANNEL_STATE_WAITING_CLOSE = 11; // waiting for close tx confs

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_Channel.class)
    public static abstract class Channel implements WalletDataDecl.EntityBase, WalletDataDecl.Channel {

        public static Channel create(
                long id,
                int userId,
                String txId,
                int authUserId,
                String description,
                int targetConf,
                long satPerByte,
                long minHtlcMsat,
                int minConfs,
                boolean spendUnconfirmed,
                String chainHashHex,
                String closingTxHashHex,
                int closeHeight,
                long settledBalance,
                long timeLockedBalance,
                int closeType,
                int tries,
                int maxTries,
                long maxTryTime,
                long lastTryTime,
                long nextTryTime,
                int state,
                String errorCode,
                String errorMessage,
                long createTime,
                long openTime,
                long closeTime,
                boolean active,
                String remotePubkey,
                String channelPoint,
                long chanId,
                long capacity,
                long localBalance,
                long remoteBalance,
                long commitFee,
                long commitWeight,
                long feePerKw,
                long unsettledBalance,
                long totalSatoshisSent,
                long totalSatoshisReceived,
                long numUpdates,
                int csvDelay,
                boolean isPrivate,
                boolean initiator,
                String chanStatusFlags,
                long localChanReserveSat,
                long remoteChanReserveSat,
                boolean staticRemoteKey,
                long lifetime,
                long uptime
        ) {
            return builder()
                    .setId(id)
                    .setUserId(userId)
                    .setTxId(txId)
                    .setAuthUserId(authUserId)
                    .setDescription(description)
                    .setTargetConf(targetConf)
                    .setSatPerByte(satPerByte)
                    .setMinHtlcMsat(minHtlcMsat)
                    .setMinConfs(minConfs)
                    .setSpendUnconfirmed(spendUnconfirmed)
                    .setChainHashHex(chainHashHex)
                    .setClosingTxHashHex(closingTxHashHex)
                    .setCloseHeight(closeHeight)
                    .setSettledBalance(settledBalance)
                    .setTimeLockedBalance(timeLockedBalance)
                    .setCloseType(closeType)
                    .setTries(tries)
                    .setMaxTries(maxTries)
                    .setMaxTryTime(maxTryTime)
                    .setLastTryTime(lastTryTime)
                    .setNextTryTime(nextTryTime)
                    .setState(state)
                    .setErrorCode(errorCode)
                    .setErrorMessage(errorMessage)
                    .setCreateTime(createTime)
                    .setOpenTime(openTime)
                    .setCloseTime(closeTime)
                    .setActive(active)
                    .setRemotePubkey(remotePubkey)
                    .setChannelPoint(channelPoint)
                    .setChanId(chanId)
                    .setCapacity(capacity)
                    .setLocalBalance(localBalance)
                    .setRemoteBalance(remoteBalance)
                    .setCommitFee(commitFee)
                    .setCommitWeight(commitWeight)
                    .setFeePerKw(feePerKw)
                    .setUnsettledBalance(unsettledBalance)
                    .setTotalSatoshisSent(totalSatoshisSent)
                    .setTotalSatoshisReceived(totalSatoshisReceived)
                    .setNumUpdates(numUpdates)
                    .setCsvDelay(csvDelay)
                    .setIsPrivate(isPrivate)
                    .setInitiator(initiator)
                    .setChanStatusFlags(chanStatusFlags)
                    .setLocalChanReserveSat(localChanReserveSat)
                    .setRemoteChanReserveSat(remoteChanReserveSat)
                    .setStaticRemoteKey(staticRemoteKey)
                    .setLifetime(lifetime)
                    .setUptime(uptime)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_Channel.Builder()
                    .setId(0)
                    .setUserId(0)
                    .setAuthUserId(0)
                    .setTargetConf(0)
                    .setSatPerByte(0)
                    .setMinHtlcMsat(0)
                    .setMinConfs(0)
                    .setSpendUnconfirmed(false)
                    .setCloseHeight(0)
                    .setSettledBalance(0)
                    .setTimeLockedBalance(0)
                    .setCloseType(0)
                    .setTries(0)
                    .setMaxTries(0)
                    .setMaxTryTime(0)
                    .setLastTryTime(0)
                    .setNextTryTime(0)
                    .setState(0)
                    .setCreateTime(0)
                    .setOpenTime(0)
                    .setCloseTime(0)
                    .setActive(false)
                    .setChanId(0)
                    .setCapacity(0)
                    .setLocalBalance(0)
                    .setRemoteBalance(0)
                    .setCommitFee(0)
                    .setCommitWeight(0)
                    .setFeePerKw(0)
                    .setUnsettledBalance(0)
                    .setTotalSatoshisReceived(0)
                    .setTotalSatoshisSent(0)
                    .setNumUpdates(0)
                    .setCsvDelay(0)
                    .setIsPrivate(false)
                    .setInitiator(false)
                    .setLocalChanReserveSat(0)
                    .setRemoteChanReserveSat(0)
                    .setStaticRemoteKey(false)
                    .setLifetime(0)
                    .setUptime(0)
                    ;
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.Channel,
                WalletDataBuilders.IBuilder<Channel>,
                WalletDataBuilders.ChannelBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_OpenChannelRequest.class)
    public static abstract class OpenChannelRequest implements WalletDataDecl.OpenChannelRequest {

        public static OpenChannelRequest create(
                String description,
                String nodePubkey,
                long localFundingAmount,
                long pushSat,
                int targetConf,
                long satPerByte,
                boolean isPrivate,
                long minHtlcMsat,
                int remoteCsvDelay,
                int minConfs,
                boolean spendUnconfirmed
        ) {
            return builder()
                    .setDescription(description)
                    .setNodePubkey(nodePubkey)
                    .setLocalFundingAmount(localFundingAmount)
                    .setPushSat(pushSat)
                    .setTargetConf(targetConf)
                    .setSatPerByte(satPerByte)
                    .setIsPrivate(isPrivate)
                    .setMinHtlcMsat(minHtlcMsat)
                    .setRemoteCsvDelay(remoteCsvDelay)
                    .setMinConfs(minConfs)
                    .setSpendUnconfirmed(spendUnconfirmed)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_OpenChannelRequest.Builder()
                    .setLocalFundingAmount(0)
                    .setPushSat(0)
                    .setTargetConf(0)
                    .setSatPerByte(0)
                    .setIsPrivate(false)
                    .setMinHtlcMsat(0)
                    .setRemoteCsvDelay(0)
                    .setMinConfs(0)
                    .setSpendUnconfirmed(false)
                    ;
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.OpenChannelRequest,
                WalletDataBuilders.IBuilder<OpenChannelRequest>,
                WalletDataBuilders.OpenChannelRequestBuilder<Builder> {
        }
    }

    public static final class OnChainTransaction {
        // primary key
        /// The transaction hash
        public String txHash;

        /// The transaction amount, denominated in satoshis
        public long amount;

        /// The number of confirmations
        public int numConfirmations;

        /// The hash of the block this transaction was included in
        public String blockHash;

        /// The height of the block this transaction was included in
        public int blockHeight;

        /// Timestamp of this transaction
        public long timeStamp;

        /// Fees paid for this transaction
        public long totalFees;

        // will be serialized to db using a type-converter
        /// Addresses that received funds for this transaction
        public List<String> destAddresses;

        /// The raw transaction hex.
        public String rawTxHex;
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_HopHint.class)
    public static abstract class HopHint
            implements WalletDataDecl.EntityBase, WalletDataDecl.HopHint {

        public static HopHint create(
                long id,
                long routeHintId,
                int index,
                String nodeId,
                long chanId,
                int feeBaseMsat,
                int feeProportionalMillionths,
                int cltvExpiryDelta
        ) {
            return builder()
                    .setId(id)
                    .setRouteHintId(routeHintId)
                    .setIndex(index)
                    .setNodeId(nodeId)
                    .setChanId(chanId)
                    .setFeeBaseMsat(feeBaseMsat)
                    .setFeeProportionalMillionths(feeProportionalMillionths)
                    .setCltvExpiryDelta(cltvExpiryDelta)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_HopHint.Builder()
                    .setId(0)
                    .setRouteHintId(0)
                    .setIndex(0)
                    .setChanId(0)
                    .setFeeBaseMsat(0)
                    .setFeeProportionalMillionths(0)
                    .setCltvExpiryDelta(0)
                    ;
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.HopHint,
                WalletDataBuilders.IBuilder<HopHint>,
                WalletDataBuilders.HopHintBuilder<Builder>
        {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_RouteHint.class)
    public static abstract class RouteHint
            implements WalletDataDecl.EntityBase, WalletDataDecl.RouteHint<HopHint> {

        public static RouteHint create(
                long id,
                String parentId,
                ImmutableList<HopHint> hopHints
        ) {
            return builder()
                    .setId(id)
                    .setParentId(parentId)
                    .setHopHints(hopHints)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_RouteHint.Builder()
                    .setId(0)
                    ;
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.RouteHint<HopHint>,
                WalletDataBuilders.IBuilder<RouteHint>,
                WalletDataBuilders.RouteHintBuilder<HopHint, Builder>
        {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_SendPaymentRequest.class)
    public static abstract class SendPaymentRequest implements WalletDataDecl.SendPaymentRequest<RouteHint> {

        public static SendPaymentRequest create(
                String purpose,
                String invoiceDescription,
                String invoiceDescriptionHashHex,
                String invoiceFallbackAddr,
                long invoiceTimestamp,
                long expiry,
                int maxTries,
                String destPubkey,
                long valueSat,
                String paymentHashHex,
                String paymentRequest,
                int finalCltvDelta,
                long feeLimitFixedMsat,
                long feeLimitPercent,
                long outgoingChanId,
                int cltvLimit,
                ImmutableList<RouteHint> routeHints,
                ImmutableList<Integer> features,
                long contactId,
                String message,
                boolean includeSenderPubkey,
                boolean isKeysend,
                boolean noAuth
        ) {
            return builder()
                    .setPurpose(purpose)
                    .setInvoiceDescription(invoiceDescription)
                    .setInvoiceDescriptionHashHex(invoiceDescriptionHashHex)
                    .setInvoiceFallbackAddr(invoiceFallbackAddr)
                    .setInvoiceTimestamp(invoiceTimestamp)
                    .setExpiry(expiry)
                    .setMaxTries(maxTries)
                    .setDestPubkey(destPubkey)
                    .setValueSat(valueSat)
                    .setPaymentHashHex(paymentHashHex)
                    .setPaymentRequest(paymentRequest)
                    .setFinalCltvDelta(finalCltvDelta)
                    .setFeeLimitFixedMsat(feeLimitFixedMsat)
                    .setFeeLimitPercent(feeLimitPercent)
                    .setOutgoingChanId(outgoingChanId)
                    .setCltvLimit(cltvLimit)
                    .setRouteHints(routeHints)
                    .setFeatures(features)
                    .setContactId(contactId)
                    .setMessage(message)
                    .setIncludeSenderPubkey(includeSenderPubkey)
                    .setIsKeysend(isKeysend)
                    .setNoAuth(noAuth)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_SendPaymentRequest.Builder()
                    .setInvoiceTimestamp(0)
                    .setExpiry(0)
                    .setMaxTries(0)
                    .setValueSat(0)
                    .setFinalCltvDelta(0)
                    .setFeeLimitFixedMsat(0)
                    .setFeeLimitPercent(0)
                    .setOutgoingChanId(0)
                    .setCltvLimit(0)
                    .setContactId(0)
                    .setIncludeSenderPubkey(false)
                    .setIsKeysend(false)
                    .setNoAuth(false)
                    ;
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.SendPaymentRequest<RouteHint>,
                WalletDataBuilders.IBuilder<SendPaymentRequest>,
                WalletDataBuilders.SendPaymentRequestBuilder<RouteHint, Builder> {
        }
    }

    public static final class Hop {
        /**
         * The unique channel ID for the channel. The first 3 bytes are the block
         * height, the next 3 the index within the block, and the last 2 bytes are the
         * output index for the channel.
         */
        public long chanId;
        public long chanCapacity;
        public int expiry;
        public long amtToForwardMsat;
        public long feeMsat;

        /**
         * An optional public key of the hop. If the public key is given, the payment
         * can be executed without relying on a copy of the channel graph.
         */
        public String pubKey;

        /**
         * If set to true, then this hop will be encoded using the new variable length
         * TLV format.
         */
        public boolean tlvPayload;

        /**
         * An optional TLV record tha singals the use of an MPP payment. If present,
         * the receiver will enforce that that the same mpp_record is included in the
         * final hop payload of all non-zero payments in the HTLC set. If empty, a
         * regular single-shot payment is or was attempted.
         */
        /**
         * A unique, random identifier used to authenticate the sender as the intended
         * payer of a multi-path payment. The payment_addr must be the same for all
         * subpayments, and match the payment_addr provided in the receiver's invoice.
         * The same payment_addr must be used on all subpayments.
         */
        public byte[] mppPaymentAddr;

        /**
         * The total amount in milli-satoshis being sent as part of a larger multi-path
         * payment. The caller is responsible for ensuring subpayments to the same node
         * and payment_hash sum exactly to total_amt_msat. The same
         * total_amt_msat must be used on all subpayments.
         */
        public long mppTotalAmtMsat;
    }

    public static final class Route {

        /**
         * The cumulative (final) time lock across the entire route.  This is the CLTV
         * value that should be extended to the first hop in the route. All other hops
         * will  the time-lock as advertised, leaving enough time for all
         * hops to wait for or present the payment preimage to complete the payment.
         */
        public int totalTimeLock;

        /**
         * Contains details concerning the specific forwarding details at each hop.
         */
        public List<Hop> hops;

        /**
         * The total fees in millisatoshis.
         */
        public long totalFeesMsat;

        /**
         * The total amount in millisatoshis.
         */
        public long totalAmtMsat;
    }

    // payment object source types
    public static final int PAYMENT_TYPE_INVOICE = 1;
    public static final int PAYMENT_TYPE_SENDPAYMENT = 2;

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_Payment.class)
    public static abstract class Payment implements WalletDataDecl.EntityBase,
            WalletDataDecl.Payment<SendPayment, HTLCAttempt, Invoice, InvoiceHTLC> {

        public static Payment create(
                long id,
                int type,
                long sourceId,
                long sourceHTLCId,
                ImmutableMap<Long, SendPayment> sendPayments,
                ImmutableMap<Long, HTLCAttempt> HTLCAttempts,
                ImmutableMap<Long, Invoice> invoices,
                ImmutableMap<Long, InvoiceHTLC> invoiceHTLCs,
                int userId,
                long time,
                String peerPubkey,
                String message
        ) {
            return builder()
                    .setId(id)
                    .setType(type)
                    .setSourceId(sourceId)
                    .setSourceHTLCId(sourceHTLCId)
                    .setSendPayments(sendPayments)
                    .setHTLCAttempts(HTLCAttempts)
                    .setInvoices(invoices)
                    .setInvoiceHTLCs(invoiceHTLCs)
                    .setUserId(userId)
                    .setTime(time)
                    .setPeerPubkey(peerPubkey)
                    .setMessage(message)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_Payment.Builder()
                    .setId(0)
                    .setSourceId(0)
                    .setSourceHTLCId(0)
                    .setUserId(0)
                    .setTime(0)
                    ;
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.Payment<SendPayment, HTLCAttempt, Invoice, InvoiceHTLC>,
                WalletDataBuilders.IBuilder<Payment>,
                WalletDataBuilders.PaymentBuilder<SendPayment, HTLCAttempt, Invoice, InvoiceHTLC, Builder> {
        }
    }

    public static final int SEND_PAYMENT_STATE_PENDING = 0;
    public static final int SEND_PAYMENT_STATE_OK = 1;
    public static final int SEND_PAYMENT_STATE_FAILED = 2;
    public static final int SEND_PAYMENT_STATE_SENDING = 3;

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_SendPayment.class)
    public static abstract class SendPayment implements
            WalletDataDecl.EntityBase, WalletDataDecl.SendPayment<RouteHint> {

        public static SendPayment create(
                long id,
                String txId,
                int userId,
                int authUserId,
                String purpose,
                int tries,
                int maxTries,
                long maxTryTime,
                long lastTryTime,
                long nextTryTime,
                int state,
                String errorCode,
                String errorMessage,
                String invoiceDescription,
                String invoiceDescriptionHashHex,
                String invoiceFallbackAddr,
                String paymentAddrHex,
                long invoiceTimestamp,
                long invoiceExpiry,
                String destPubkey,
                long valueMsat,
                long totalValueMsat,
                String paymentHashHex,
                String paymentRequest,
                int finalCltvDelta,
                long feeLimitFixedMsat,
                long feeLimitPercent,
                long outgoingChanId,
                int cltvLimit,
                ImmutableMap<Long, byte[]> destCustomRecords,
                String paymentError,
                String paymentPreimageHex,
                ImmutableList<RouteHint> routeHints,
                ImmutableList<Integer> features,
                long createTime,
                long sendTime,
                long feeMsat,
                String contactPubkey,
                String message,
                String senderPubkey,
                boolean isKeysend
        ) {
            return builder()
                    .setId(id)
                    .setTxId(txId)
                    .setUserId(userId)
                    .setAuthUserId(authUserId)
                    .setPurpose(purpose)
                    .setTries(tries)
                    .setMaxTries(maxTries)
                    .setMaxTryTime(maxTryTime)
                    .setLastTryTime(lastTryTime)
                    .setNextTryTime(nextTryTime)
                    .setState(state)
                    .setErrorCode(errorCode)
                    .setErrorMessage(errorMessage)
                    .setInvoiceDescription(invoiceDescription)
                    .setInvoiceDescriptionHashHex(invoiceDescriptionHashHex)
                    .setInvoiceFallbackAddr(invoiceFallbackAddr)
                    .setInvoiceTimestamp(invoiceTimestamp)
                    .setInvoiceExpiry(invoiceExpiry)
                    .setDestPubkey(destPubkey)
                    .setValueMsat(valueMsat)
                    .setTotalValueMsat(totalValueMsat)
                    .setPaymentHashHex(paymentHashHex)
                    .setPaymentRequest(paymentRequest)
                    .setFinalCltvDelta(finalCltvDelta)
                    .setFeeLimitFixedMsat(feeLimitFixedMsat)
                    .setFeeLimitPercent(feeLimitPercent)
                    .setOutgoingChanId(outgoingChanId)
                    .setCltvLimit(cltvLimit)
                    .setDestCustomRecords(destCustomRecords)
                    .setPaymentError(paymentError)
                    .setPaymentPreimageHex(paymentPreimageHex)
                    .setRouteHints(routeHints)
                    .setFeatures(features)
                    .setCreateTime(createTime)
                    .setSendTime(sendTime)
                    .setFeeMsat(feeMsat)
                    .setContactPubkey(contactPubkey)
                    .setMessage(message)
                    .setSenderPubkey(senderPubkey)
                    .setIsKeysend(isKeysend)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_SendPayment.Builder()
                    .setId(0)
                    .setUserId(0)
                    .setAuthUserId(0)
                    .setTries(0)
                    .setMaxTries(0)
                    .setMaxTryTime(0)
                    .setLastTryTime(0)
                    .setNextTryTime(0)
                    .setState(SEND_PAYMENT_STATE_PENDING)
                    .setInvoiceTimestamp(0)
                    .setInvoiceExpiry(0)
                    .setValueMsat(0)
                    .setTotalValueMsat(0)
                    .setFinalCltvDelta(0)
                    .setFeeLimitFixedMsat(0)
                    .setFeeLimitPercent(0)
                    .setOutgoingChanId(0)
                    .setCltvLimit(0)
                    .setCreateTime(0)
                    .setSendTime(0)
                    .setFeeMsat(0)
                    .setIsKeysend(false)
                    ;
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.SendPayment<RouteHint>,
                WalletDataBuilders.IBuilder<SendPayment>,
                WalletDataBuilders.SendPaymentBuilder<RouteHint, Builder> {
        }
    }


    public static final class ChannelHTLC {
        // primary key, synthetic, currently chanId+hashLock are the key
        public long id;

        // foreigh key
        public long chanId;

        public String hashLockHex;
        public boolean incoming;
        public long amount;
        public int expirationHeight;
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_Contact.class)
    public static abstract class Contact implements WalletDataDecl.EntityBase, WalletDataDecl.Contact<RouteHint> {

        public static Contact create(
                long id,
                int userId,
                int authUserId,
                long createTime,
                String pubkey,
                String name,
                String description,
                String url,
                ImmutableList<RouteHint> routeHints,
                ImmutableList<Integer> features
        ) {
            return builder()
                    .setId(id)
                    .setUserId(userId)
                    .setAuthUserId(authUserId)
                    .setCreateTime(createTime)
                    .setPubkey(pubkey)
                    .setName(name)
                    .setDescription(description)
                    .setUrl(url)
                    .setRouteHints(routeHints)
                    .setFeatures(features)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_Contact.Builder()
                    .setId(0)
                    .setUserId(0)
                    .setAuthUserId(0)
                    .setCreateTime(0)
                    ;
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.Contact<RouteHint>,
                WalletDataBuilders.IBuilder<Contact>,
                WalletDataBuilders.ContactBuilder<RouteHint, Builder>
        {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_AddContactRequest.class)
    public static abstract class AddContactRequest implements WalletDataDecl.AddContactRequest {

        public static AddContactRequest create(
                String pubkey,
                String name,
                String description,
                String url
        ) {
            return builder()
                    .setPubkey(pubkey)
                    .setName(name)
                    .setDescription(description)
                    .setUrl(url)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_AddContactRequest.Builder();
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.AddContactRequest,
                WalletDataBuilders.IBuilder<AddContactRequest>,
                WalletDataBuilders.AddContactRequestBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_AddAppContactRequest.class)
    public static abstract class AddAppContactRequest implements WalletDataDecl.AddAppContactRequest {

        public static AddAppContactRequest create() {
            return builder().build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_AddAppContactRequest.Builder();
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.AddAppContactRequest,
                WalletDataBuilders.IBuilder<AddAppContactRequest>,
                WalletDataBuilders.AddAppContactRequestBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ListContactsRequest.class)
    public static abstract class ListContactsRequest extends ListRequestBase implements WalletDataDecl.ListContactsRequest {

        // FIXME add static factory method

        public static Builder builder() {
            return new AutoValue_WalletData_ListContactsRequest.Builder()
                    .setUserId(0)
                    .setOnlyOwn(false)
                    .setNoAuth(false)
                    .setEnablePaging(false)
                    .setSortDesc(false);
        }

        public abstract Builder toBuilder();

        public ListContactsRequest withPage(ListPage page) {
            return toBuilder().setPage(page).build();
        }

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.ListContactsRequest,
                WalletDataBuilders.IBuilder<ListContactsRequest>,
                WalletDataBuilders.ListContactsRequestBuilder<Builder> {
            public abstract Builder setPage(ListPage page);
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ListContactsResult.class)
    public static abstract class ListContactsResult implements WalletDataDecl.ListResultTmpl<Contact> {

        public static ListContactsResult create(
                ImmutableList<Contact> items,
                int count,
                int position
        ) {
            return builder()
                    .setItems(items)
                    .setCount(count)
                    .setPosition(position)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_ListContactsResult.Builder()
                    .setCount(0)
                    .setPosition(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.ListResultTmpl<Contact>,
                WalletDataBuilders.IBuilder<ListContactsResult>,
                WalletDataBuilders.ListResultTmplBuilder<Contact, Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ListContactsPrivilege.class)
    public static abstract class ListContactsPrivilege
            implements WalletDataDecl.EntityBase, WalletDataDecl.ListContactsPrivilege {

        public static ListContactsPrivilege create(
                long id,
                int userId,
                int authUserId,
                long createTime
        ) {
            return builder()
                    .setId(id)
                    .setUserId(userId)
                    .setAuthUserId(authUserId)
                    .setCreateTime(createTime)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_ListContactsPrivilege.Builder()
                    .setId(0)
                    .setUserId(0)
                    .setAuthUserId(0)
                    .setCreateTime(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.ListContactsPrivilege,
                WalletDataBuilders.IBuilder<ListContactsPrivilege>,
                WalletDataBuilders.ListContactsPrivilegeBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ContactPaymentsPrivilege.class)
    public static abstract class ContactPaymentsPrivilege
            implements WalletDataDecl.EntityBase, WalletDataDecl.ContactPaymentsPrivilege {

        public static ContactPaymentsPrivilege create(
                long id,
                int userId,
                int authUserId,
                long createTime,
                long contactId
        ) {
            return builder()
                    .setId(id)
                    .setUserId(userId)
                    .setAuthUserId(authUserId)
                    .setCreateTime(createTime)
                    .setContactId(contactId)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_ContactPaymentsPrivilege.Builder()
                    .setId(0)
                    .setUserId(0)
                    .setAuthUserId(0)
                    .setCreateTime(0)
                    .setContactId(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.ContactPaymentsPrivilege,
                WalletDataBuilders.IBuilder<ContactPaymentsPrivilege>,
                WalletDataBuilders.ContactPaymentsPrivilegeBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ConnectPeerRequest.class)
    public static abstract class ConnectPeerRequest
            implements WalletDataDecl.ConnectPeerRequest {

        public static ConnectPeerRequest create(
                String pubkey,
                String host,
                boolean perm
        ) {
            return builder()
                    .setPubkey(pubkey)
                    .setHost(host)
                    .setPerm(perm)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_ConnectPeerRequest.Builder()
                    .setPerm(false);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.ConnectPeerRequest,
                WalletDataBuilders.IBuilder<ConnectPeerRequest>,
                WalletDataBuilders.ConnectPeerRequestBuilder<Builder> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ConnectPeerResponse.class)
    public static abstract class ConnectPeerResponse {

        public static ConnectPeerResponse create() {
            return builder()
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_ConnectPeerResponse.Builder();
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataBuilders.IBuilder<ConnectPeerResponse> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ShareContactRequest.class)
    public static abstract class ShareContactRequest {

        public static ShareContactRequest create() {
            return builder()
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_ShareContactRequest.Builder();
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataBuilders.IBuilder<ShareContactRequest> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ShareContactResponse.class)
    public static abstract class ShareContactResponse {

        public static ShareContactResponse create() {
            return builder()
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_ShareContactResponse.Builder();
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataBuilders.IBuilder<ShareContactResponse> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_AddContactInvoiceRequest.class)
    public static abstract class AddContactInvoiceRequest {

        public static AddContactInvoiceRequest create() {
            return builder()
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_AddContactInvoiceRequest.Builder();
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataBuilders.IBuilder<AddContactInvoiceRequest> {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_AddContactInvoiceResponse.class)
    public static abstract class AddContactInvoiceResponse
            implements WalletDataDecl.AddContactInvoiceResponse {

        public static AddContactInvoiceResponse create(
                String paymentRequest
        ) {
            return builder()
                    .setPaymentRequest(paymentRequest)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_AddContactInvoiceResponse.Builder();
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.AddContactInvoiceResponse,
                WalletDataBuilders.IBuilder<AddContactInvoiceResponse>,
                WalletDataBuilders.AddContactInvoiceResponseBuilder<Builder>
        {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_RoutingPolicy.class)
    public static abstract class RoutingPolicy
            implements WalletDataDecl.EntityBase, WalletDataDecl.RoutingPolicy {

        public static RoutingPolicy create(
                long id,
                long channelId,
                boolean reverse,
                int timeLockDelta,
                long minHtlc,
                long feeBaseMsat,
                long feeRateMilliMsat,
                boolean disabled,
                long maxHtlcMsat,
                int lastUpdate
        ) {
            return builder()
                    .setId(id)
                    .setChannelId(channelId)
                    .setReverse(reverse)
                    .setTimeLockDelta(timeLockDelta)
                    .setMinHtlc(minHtlc)
                    .setFeeBaseMsat(feeBaseMsat)
                    .setFeeRateMilliMsat(feeRateMilliMsat)
                    .setDisabled(disabled)
                    .setMaxHtlcMsat(maxHtlcMsat)
                    .setLastUpdate(lastUpdate)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_RoutingPolicy.Builder()
                    .setId(0)
                    .setChannelId(0)
                    .setReverse(false)
                    .setTimeLockDelta(0)
                    .setMinHtlc(0)
                    .setFeeBaseMsat(0)
                    .setFeeRateMilliMsat(0)
                    .setDisabled(false)
                    .setMaxHtlcMsat(0)
                    .setLastUpdate(0);
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.RoutingPolicy,
                WalletDataBuilders.IBuilder<RoutingPolicy>,
                WalletDataBuilders.RoutingPolicyBuilder<Builder>
        {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_ChannelEdge.class)
    public static abstract class ChannelEdge
            implements WalletDataDecl.EntityBase, WalletDataDecl.ChannelEdge<RoutingPolicy> {

        public static ChannelEdge create(
                long id,
                long channelId,
                String chanPoint,
                String node1Pubkey,
                String node2Pubkey,
                long capacity,
                RoutingPolicy node1Policy,
                RoutingPolicy node2Policy
        ) {
            return builder()
                    .setId(id)
                    .setChannelId(channelId)
                    .setChanPoint(chanPoint)
                    .setNode1Pubkey(node1Pubkey)
                    .setNode2Pubkey(node2Pubkey)
                    .setCapacity(capacity)
                    .setNode1Policy(node1Policy)
                    .setNode2Policy(node2Policy)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_ChannelEdge.Builder()
                    .setId(0)
                    .setChannelId(0)
                    .setCapacity(0)
                    ;
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.ChannelEdge<RoutingPolicy>,
                WalletDataBuilders.IBuilder<ChannelEdge>,
                WalletDataBuilders.ChannelEdgeBuilder<RoutingPolicy,Builder>
        {
        }
    }

    @AutoValue
    @AutoValueClass(className = AutoValue_WalletData_LightningNode.class)
    public static abstract class LightningNode
            implements WalletDataDecl.EntityBase, WalletDataDecl.LightningNode {

        public static LightningNode create(
                long id,
                int lastUpdate,
                String pubkey,
                String alias,
                String color,
                ImmutableList<Integer> features
        ) {
            return builder()
                    .setId(id)
                    .setLastUpdate(lastUpdate)
                    .setPubkey(pubkey)
                    .setAlias(alias)
                    .setColor(color)
                    .setFeatures(features)
                    .build();
        }

        public static Builder builder() {
            return new AutoValue_WalletData_LightningNode.Builder()
                    .setId(0)
                    .setLastUpdate(0)
                    ;
        }

        public abstract Builder toBuilder();

        @AutoValue.Builder
        public abstract static class Builder implements
                WalletDataDecl.EntityBase,
                WalletDataDecl.LightningNode,
                WalletDataBuilders.IBuilder<LightningNode>,
                WalletDataBuilders.LightningNodeBuilder<Builder>
        {
        }
    }

}
