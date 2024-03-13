package DAO;

import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAOImpl implements AccountDAO {

    /**
     * Attempts to insert a new user into the account table
     * @param newAccount Account object representing the account to be added
     * @return Account object representing the inserted account, return null if unsuccessful
     */
    @Override
    public Account insertAccount(Account newAccount) {
        Connection con = Util.ConnectionUtil.getConnection();
        String sql = "INSERT INTO account (username, password) VALUES (?,?);";

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newAccount.getUsername());
            ps.setString(2, newAccount.getPassword());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()) {
                int id = rs.getInt("account_id");
                return new Account(id, newAccount.getUsername(), newAccount.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * retrieves an Account from the database using a username
     * @param username
     * @return Account object representing the pulled account, returns null if not found or errored
     */
    @Override
    public Account findAccountByUserName(String username) {
        Connection con = Util.ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Account WHERE username = ?;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ps.executeQuery();
            ResultSet result = ps.getResultSet();
            while (result.next()) {
                int account_id = result.getInt("account_id");
                String fetch_username = result.getString("username");
                String password = result.getString("password");
                return new Account(account_id, fetch_username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Find account using the account id
     * @param user_id account's user id
     * @return returns Account representing the fetched account, null if not found or errored
     */
    @Override
    public Account findAccountByUserId(int user_id) {
        Connection con = Util.ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Account WHERE account_id = ?;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);

            ps.executeQuery();
            ResultSet result = ps.getResultSet();
            while (result.next()) {
                int account_id = result.getInt("account_id");
                String fetch_username = result.getString("username");
                String password = result.getString("password");
                return new Account(account_id, fetch_username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
