package api.service;

import api.domain.Account;

import java.util.Collection;
import java.util.List;

public interface AccountService {

    Account getAccount (String accountName);

    void createAccount (String accountName, String dbType, String dbUri, String dbName, String superadminUserName, String superadminPassword);

    void saveAccount(Account account);

    Long deleteAccount(String accountName);

    Long deleteAccounts(Collection<String> accountIds);

    List<? extends Account> getAccounts();
}
