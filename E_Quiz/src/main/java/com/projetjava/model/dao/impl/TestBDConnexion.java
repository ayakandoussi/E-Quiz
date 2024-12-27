package com.projetjava.model.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

public class TestBDConnexion {

    public static void main(String[] args) {
        try {
            // Test de la connexion
            Connection conn = BDConnexion.getConnection();
            if (conn != null) {
                System.out.println("Connexion reussie !");
            } else {
                System.out.println("Connexion echouee.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
