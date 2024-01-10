package account.service;

import java.util.HashMap;
import java.util.Map;

public class AccountRepo {
    Map<String, Account> accounts = new HashMap<>();

    public void storeAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }
    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }
    public void deleteAccount(String accountId) {
        accounts.remove(accountId);
    }

    public boolean accountExists(String cpr) {
        return accounts.values().stream().anyMatch(a -> a.getCpr().equals(cpr));
    }
}
