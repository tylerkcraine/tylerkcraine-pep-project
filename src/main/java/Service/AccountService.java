package Service;

import DAO.AccountDAOImpl;
import Model.Account;

public class AccountService {
    private AccountDAOImpl accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAOImpl();
    }

    /**
     * Attempts to create a new user checking if the input is valid and the user doesn't exist
     * @param account from user json (parsed in handler)
     * @return Account representing the new account, return null if checks fail or account already exists
     */
    public Account createAccount(Account account) {
        // Checks
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return null;
        }
        Account probeAccount = accountDAO.findAccountByUserName(account.getUsername());
        if (probeAccount != null) {
            return null;
        }

        return this.accountDAO.insertAccount(account);
    }

    /**
     * Uses user provided account to authenticate and then return an account with a filled in account_id
     * @param account
     * @return account representing the fetched account, null if authentication fails
     */
    public Account authenticateAccount(Account account) {
        Account probeAccount = accountDAO.findAccountByUserName(account.getUsername());
        if (probeAccount != null && 
        account.getPassword().contentEquals(probeAccount.getPassword())) {
            return probeAccount;
        }
        return null;
    }
}
