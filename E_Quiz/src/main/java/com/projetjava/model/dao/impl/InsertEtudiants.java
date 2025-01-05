package com.projetjava.model.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class InsertEtudiants {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static void insertUsers() {
        String url = "jdbc:mysql://localhost:3306/e_quiz";
        String username = "root";
        String password = "ABC@def123";

        String sql = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, role) VALUES (?, ?, ?, ?, ?)";

        String[][] utilisateurs = {
            {"Lachhab", "Zakariae"},
            {"Kandoussi", "Aya"},
            {"Bouchama", "Aya"},
            {"Jabbade", "Fatima-ezzahra"},
            {"Ouardi", "Salma"},
            {"Alaoui", "Zakaria"},
            {"Baqili", "Zineb"},
            {"Elouarrat", "Khadija"},
            {"Tachafine", "Othmane"},
            {"Elaouni", "Hafssa"}
        };

        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            for (String[] utilisateur : utilisateurs) {
                String nom = utilisateur[0];
                String prenom = utilisateur[1];
                String email = nom.toLowerCase() + "@enim.ac.ma";
                String motDePasse = hashPassword("123");
                String role = "etudiant";

                statement.setString(1, nom);
                statement.setString(2, prenom);
                statement.setString(3, email);
                statement.setString(4, motDePasse);
                statement.setString(5, role);

                statement.executeUpdate();
            }

            System.out.println("10 utilisateurs ont été insérés avec succès avec BCrypt.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        insertUsers();
    }
}
