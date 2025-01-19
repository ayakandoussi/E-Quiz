package com.projetjava.model.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class InsertUtilisateurs {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static void insertUsers() {
        String url = "jdbc:mysql://localhost:3306/e-quiz";  
        String username = "root";  
        String password = "";  

        String sql = "INSERT INTO Utilisateur (nom, prenom, email, mot_de_passe, role) VALUES (?, ?, ?, ?, ?)";

        String[][] utilisateurs = {
            {"Toulni", "Hamza"},
            {"Zaydi", "Hayat"},
            {"Zrira", "Nabila"},
            {"Assoul", "Saliha"},
            {"Ouazzani", "Khadija"},
            {"Tajini", "Reda"},
            {"Tikito", "Kawtar"},
            {"Gallab", "Maryam"},
            {"El Moukhi", "Nawfal"},
            {"Hanani", "Fatima"}
        };

        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            for (String[] utilisateur : utilisateurs) {
                String nom = utilisateur[0];
                String prenom = utilisateur[1];
                String email = nom.toLowerCase() + "@enim.ac.ma";
                String motDePasse = hashPassword("123");
                String role = "professeur";

                statement.setString(1, nom);
                statement.setString(2, prenom);
                statement.setString(3, email);
                statement.setString(4, motDePasse);
                statement.setString(5, role);

                statement.executeUpdate();
            }

            System.out.println("Les utilisateurs ont été insérés avec succès.");

        } catch (SQLException e) {

        }
    }

    public static void main(String[] args) {
        insertUsers();
    }
}
