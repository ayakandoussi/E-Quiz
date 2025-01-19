package com.projetjava.model.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertQuiz {

    public static void insertQuizzes() {
        String url = "jdbc:mysql://localhost:3306/e_quiz";
        String username = "root";
        String password = "ABC@def123";

        String sql = "INSERT INTO quiz (titre, description, idEnseignant) VALUES (?, ?, ?)";

        String[][] quizzes = {
            {"Les commandes de base Unix", "Quiz teste la connaissance des commandes fondamentales de l'interface en ligne de commande Unix, comme `ls`, `cd`, `mkdir`, et `rm`. Les utilisateurs seront évalués sur leur capacité à naviguer dans le système de fichiers, manipuler des fichiers et des répertoires."},
            {"Gestion des permissions et des utilisateurs sous Unix", "Quiz évalue la compréhension de la gestion des permissions dans Unix. Les questions portent sur les commandes comme `chmod`, `chown`, `chgrp`, et les concepts relatifs aux utilisateurs, groupes et leurs permissions d'accès."},
            {"Gestion des processus Unix", "Quiz teste les connaissances sur la gestion des processus dans un environnement Unix. Il couvre des commandes comme `ps`, `top`, `kill`, `bg`, `fg`, et l'utilisation des signaux pour contrôler les processus en cours d'exécution."},
            {"Scripting shell sous Unix", "Quiz évalue la maîtrise des scripts shell Unix, y compris les boucles, les conditions, les variables et les fonctions. Il teste également la capacité à automatiser des tâches avec des scripts shell."}
        };

        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            for (String[] quiz : quizzes) {
                String titre = quiz[0];
                String description = quiz[1];
                int idEnseignant = 7;
                statement.setString(1, titre);
                statement.setString(2, description);
                statement.setInt(3, idEnseignant);
                statement.executeUpdate();
            }

            System.out.println("10 quizs ont été insérés avec succès pour le professeur ID 2.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        insertQuizzes();
    }
}
