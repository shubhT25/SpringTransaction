package com.jdbc.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class TransactionDaoImpl implements TransactionDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional  // This method will be transactional
    public String transferFunds(String fromAccount, String toAccount, int Amount) {
        String status = "";
        int debitRowCount = debit(fromAccount, Amount);
        // This line will throw an exception, simulating a failure
        float f = 100 / 0;
        int creditRowCount = credit(toAccount, Amount);
        if (debitRowCount == 1 && creditRowCount == 1) {
            status = "Transaction Success";
        } else {
            status = "Transaction Failed";
        }
        return status;
    }

    public int debit(String fromAccount, int Amount) {
        int rowCount = jdbcTemplate.update("UPDATE ACCOUNT SET ABALANCE = ABALANCE - " + Amount + " WHERE AID = '" + fromAccount + "'");
        System.out.println(Amount + " debited from " + fromAccount);
        return rowCount;
    }

    public int credit(String toAccount, int Amount) {
        int rowCount = jdbcTemplate.update("UPDATE ACCOUNT SET ABALANCE = ABALANCE + " + Amount + " WHERE AID = '" + toAccount + "'");
        System.out.println(Amount + " credited to " + toAccount);
        return rowCount;
    }
}
