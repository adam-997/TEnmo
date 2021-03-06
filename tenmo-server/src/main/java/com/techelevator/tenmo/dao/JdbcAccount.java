package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class JdbcAccount implements AccountDao {
    private final JdbcTemplate jdbcTemplate;


    public JdbcAccount (DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        Account account = new Account();
        if(results.next()){
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public void updateAccount(Account accountUpdated) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?;";
        jdbcTemplate.update(sql, accountUpdated.getBalance().getBalance(), accountUpdated.getAccountId());
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, accountId);
        Account account = null;
        if(rs.next()){
            account = mapRowToAccount(rs);
        }
        return account;
    }




    @Override
    public Balance getBalanceByUsername(String username) {
        String sql = "SELECT balance FROM accounts JOIN users ON accounts.user_id = users.user_id WHERE username = ? ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        Balance balance = new Balance();
        if(results.next()){
            String userBalance = results.getString("balance");
            balance.setBalance(Double.parseDouble(userBalance));
        }
        return balance;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        int accountId = rs.getInt("account_id");
        int userAccountId = rs.getInt("user_id");

        Balance balance = new Balance();
        String accountBalance = rs.getString("balance");
        balance.setBalance( Double.parseDouble(accountBalance));
        return new Account(accountId, userAccountId, balance);
    }
}
