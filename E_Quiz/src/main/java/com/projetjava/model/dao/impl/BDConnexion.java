package com.projetjava.model.dao.impl;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConnexion {
    private static final String URL = "jdbc:mysql://localhost:3306/EQuizDB";
    private static final String USER = "root";

    private static final String PASSWORD = "Bouchamaaya2004.";

    private Connection connection;


    public BDConnexion() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        connection.close();
    }


}
