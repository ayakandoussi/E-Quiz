package com.projetjava.model.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

public class TestBDConnexion {
    public static void main(String[] args) {
        BDConnexion bdConnexion = null;
        try {

            bdConnexion = new BDConnexion();
            Connection connection = bdConnexion.getConnection();

            if (connection != null) {
                System.out.println("Connexion réussie à la base de données !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        } finally {
            if (bdConnexion != null) {
                try {
                    bdConnexion.close();
                    System.out.println("Connexion fermée !");
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la fermeture : " + e.getMessage());
                }
            }
        }
    }
}
