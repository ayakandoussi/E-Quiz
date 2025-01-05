package com.projetjava.model.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertQuiz {

    // Fonction pour insérer des quizs dans la BDD
    public static void insertQuizzes() {
        String url = "jdbc:mysql://localhost:3306/e_quiz"; // Remplacez par l'URL de votre base de données
        String username = "root"; // Remplacez par votre utilisateur MySQL
        String password = "ABC@def123"; // Remplacez par votre mot de passe MySQL

        String sql = "INSERT INTO quiz (titre, description, idEnseignant) VALUES (?, ?, ?)";

        // Liste des titres et descriptions des quizs
        String[][] quizzes = {
            {"Introduction à Java", "Quiz pour tester les bases du langage Java, y compris les variables, types de données, et syntaxe."},
            {"Les boucles en Java", "Quiz pour évaluer la compréhension des structures de contrôle comme for, while et do-while."},
            {"Les classes et objets", "Quiz pour vérifier les connaissances sur les concepts POO en Java, comme les classes, objets, et méthodes."},
            {"Les collections en Java", "Quiz pour tester les connaissances sur les listes, ensembles, et maps en Java."},
            {"Gestion des exceptions", "Quiz sur la gestion des erreurs avec try-catch-finally et les exceptions personnalisées."},
            {"Introduction à JavaFX", "Quiz sur les bases de JavaFX, comme les scènes, les stages, et les layouts."},
            {"Les contrôles JavaFX", "Quiz sur les éléments d'interface comme les boutons, labels, et text fields en JavaFX."},
            {"CSS avec JavaFX", "Quiz sur la personnalisation des interfaces JavaFX avec des fichiers CSS."},
            {"Les événements en JavaFX", "Quiz pour évaluer la compréhension des événements et des écouteurs en JavaFX."},
            {"Les bases de données avec JavaFX", "Quiz sur l'intégration de bases de données MySQL dans une application JavaFX."}
        };

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Boucle pour insérer chaque quiz
            for (String[] quiz : quizzes) {
                String titre = quiz[0];
                String description = quiz[1];
                int idEnseignant = 6; // ID du professeur

                // Remplir la requête préparée
                statement.setString(1, titre);
                statement.setString(2, description);
                statement.setInt(3, idEnseignant);

                // Exécuter la requête
                statement.executeUpdate();
            }

            System.out.println("10 quizs ont été insérés avec succès pour le professeur ID 2.");
        } catch (SQLException e) {
            e.printStackTrace(); // Afficher l'erreur SQL
        }
    }

    public static void main(String[] args) {
        insertQuizzes();
    }
}
