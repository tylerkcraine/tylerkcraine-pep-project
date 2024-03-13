package DAO;

import Model.Account;

public interface AccountDAO {
    public Account insertAccount(Account newAccount);
    public Account findAccountByUserName(String username);
}
