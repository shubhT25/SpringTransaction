package com.jdbc.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionDaoImpl implements TransactionDao{
    private JdbcTemplate jdbcTemplate;
    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String transferFunds(String fromAccount, String toAccount, int Amount) {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = dataSourceTransactionManager.getTransaction(txDef);
        String status = "";
        try {
            int debitRowCount = debit(fromAccount, Amount);
            float f = 100/0;
            int creditRowCount = credit(toAccount, Amount);
            if(debitRowCount == 1 && creditRowCount == 1) {
                dataSourceTransactionManager.commit(txStatus);
                status = "Transaction Success";
            } else {
                dataSourceTransactionManager.rollback(txStatus);
                status = "Transaction Failed";
            }
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(txStatus);
            status = "Transaction Failed";
            e.printStackTrace();
        }
        return status;
    }

    public int debit(String fromAccount, int Amount) {
        int rowCount = jdbcTemplate.update("update ACCOUNT set ABALANCE = ABALANCE - " + Amount + " where AID = '" + fromAccount + "'");
        System.out.println(Amount + " debited from " + fromAccount);
        return rowCount;
    }

    public int credit(String toAccount, int Amount) {
        int rowCount = jdbcTemplate.update("update ACCOUNT set ABALANCE = ABALANCE + " + Amount + " where AID = '" + toAccount + "'");
        System.out.println(Amount + " credited to " + toAccount);
        return rowCount;
    }
}
