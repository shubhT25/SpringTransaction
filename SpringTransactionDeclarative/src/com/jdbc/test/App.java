package com.jdbc.test;

import com.jdbc.dao.TransactionDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/com/jdbc/resources/applicationContext.xml");
        TransactionDao txDao = (TransactionDao) context.getBean("transactionDao");
        String status = txDao.transferFunds("A222", "A111", 5000);
        System.out.println(status);
    }
}
