package com.webapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

class DBHandler {
    public Connection connection;
    public Statement statement;

    DBHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryDB", "root", "");
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("unable to setup db");
            System.out.println(e.getMessage().toString());
        }

    }

}