package com.jdbc.dao;

public interface TransactionDao {
    public String transferFunds(String fromAccount, String toAccount, int Amount);
}
