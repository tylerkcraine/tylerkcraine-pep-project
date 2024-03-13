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
     * @param username
     * @param password
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
}
