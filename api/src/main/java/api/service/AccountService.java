package api.service;

import api.domain.Account;

import java.util.Collection;
import java.util.List;

public interface AccountService {

    Account getAccount (String providerName, String accountName);

    List<Account> getAccountsByAccountName (String accountName);

    Account getAccountById (String providerName, String accountId);

    Account createAccount (String providerName, String accountName, String dbType, String dbUri, String dbName, String superadminUserName, String superadminPassword);

    Account saveAccount(String providerName, Account account);

    Long deleteAccount(String providerName, String accountName);

    Long deleteAccounts(String providerName, Collection<String> accountIds);

    List<? extends Account> getAccounts(String providerName);

    void refreshTenantSpringContexts(Account account);

    void refreshProviderSpringContexts(String providerName);
}
