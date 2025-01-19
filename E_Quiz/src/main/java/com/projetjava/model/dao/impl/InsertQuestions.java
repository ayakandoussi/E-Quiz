package com.projetjava.model.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertQuestions {

    public static void insertQuestions() {
        String url = "jdbc:mysql://localhost:3306/e_quiz";
        String username = "root";
        String password = "ABC@def123";

        String sql = "INSERT INTO question (enonce, choix1, choix2, choix3, choix4, bonneReponse, idQuiz) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Object[][] questions = {
            {"Quelle commande affiche les processus en cours d'exécution ?", "ps", "top", "kill", "process", "ps"},
            {"Quelle commande permet d'afficher une vue dynamique des processus ?", "top", "ps", "kill", "process", "top"},
            {"Comment tuer un processus à partir de son PID ?", "kill", "stop", "terminate", "end", "kill"},
            {"Comment envoyer un signal SIGKILL à un processus ?", "kill -9", "kill -15", "kill -s SIGKILL", "kill -kill", "kill -9"},
            {"Quelle commande permet de suspendre un processus en arrière-plan ?", "bg", "fg", "pause", "stop", "bg"},
            {"Quelle commande permet de reprendre un processus suspendu en avant-plan ?", "fg", "bg", "start", "resume", "fg"},
            {"Quel signal permet de suspendre un processus ?", "SIGSTOP", "SIGKILL", "SIGTERM", "SIGPAUSE", "SIGSTOP"},
            {"Quel signal permet d'arrêter un processus en cours d'exécution ?", "SIGTERM", "SIGSTOP", "SIGKILL", "SIGQUIT", "SIGTERM"},
            {"Quelle commande permet de vérifier les processus d'un utilisateur ?", "ps -u", "top -u", "ps -a", "userprocess", "ps -u"},
            {"Comment afficher les processus d'un utilisateur spécifique ?", "ps -u username", "top -u username", "ps username", "top username", "ps -u username"},
            {"Quelle commande permet de mettre un processus en arrière-plan ?", "bg", "fg", "pause", "start", "bg"},
            {"Quelle commande affiche l'ID du processus parent ?", "ps -f", "ps -p", "ps -e", "ps -a", "ps -f"},
            {"Quelle commande permet d'exécuter un processus en arrière-plan ?", "command &", "command bg", "command fg", "bg command", "command &"},
            {"Comment afficher un arbre des processus ?", "ps -ejH", "ps -ax", "top -tree", "ps -af", "ps -ejH"},
            {"Comment lister les processus associés à un terminal ?", "ps -t", "ps -a", "top -t", "listprocess", "ps -t"},
            {"Quel signal est utilisé pour redémarrer un processus ?", "SIGHUP", "SIGINT", "SIGTERM", "SIGSTOP", "SIGHUP"},
            {"Comment obtenir la liste des processus avec leurs ressources ?", "top -u", "ps aux", "ps -ef", "top", "ps aux"},
            {"Comment afficher les processus qui consomment le plus de ressources ?", "top", "ps -e", "ps -aux", "ps -r", "top"},
            {"Quelle commande permet de vérifier l'état d'un processus ?", "ps -l", "ps -e", "ps -s", "top -s", "ps -l"},
            {"Quelle commande permet de changer la priorité d'un processus ?", "renice", "nice", "priority", "top", "renice"},
            {"Comment envoyer un signal SIGINT à un processus ?", "kill -2", "kill -9", "kill -s SIGINT", "kill -int", "kill -2"}
        };

        int idQuiz = 24;

        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Object[] question : questions) {
                String enonce = (String) question[0];
                String choix1 = (String) question[1];
                String choix2 = (String) question[2];
                String choix3 = (String) question[3];
                String choix4 = (String) question[4];
                String bonneReponse = (String) question[5];
                statement.setString(1, enonce);
                statement.setString(2, choix1);
                statement.setString(3, choix2);
                statement.setString(4, choix3);
                statement.setString(5, choix4);
                statement.setString(6, bonneReponse);
                statement.setInt(7, idQuiz);

                statement.executeUpdate();
            }

            System.out.println("20 questions ont été insérées avec succès pour le quiz ID " + idQuiz + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        insertQuestions();
    }
}
