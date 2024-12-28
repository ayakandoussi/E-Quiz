/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projetjava.model.dao.impl;

/**
 *
 * @author Bouchama
 */
import java.sql.ResultSet;

import com.projetjava.domain.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private Connection connection;

    // Constructeur pour établir la connexion à la base de données
    public QuestionDAO(Connection connection) {
        try {
            this.connection = BDConnexion.getConnection();
        } catch (SQLException e) {
            // Gérer l'exception ici, par exemple en la loguant ou en la relançant
            e.printStackTrace();
        }
    }

    // Ajouter une nouvelle question
    public void ajouterQuestion(Question question) throws SQLException {
        String sql = "INSERT INTO Question (enonce, choix1, choix2, choix3, choix4, bonneReponse, idQuiz) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, question.getEnonce());
            stmt.setString(2, question.getChoix1());
            stmt.setString(3, question.getChoix2());
            stmt.setString(4, question.getChoix3());
            stmt.setString(5, question.getChoix4());
            stmt.setString(6, question.getBonneReponse());
            stmt.setInt(7, question.getIdQuiz());
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Gérer l'exception
            e.printStackTrace();
        }
    }

    // Récupérer toutes les questions d'un quiz
    public List<Question> getQuestionsByQuizId(int idQuiz) throws SQLException {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM Question WHERE idQuiz = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idQuiz);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Question question = new Question(
                        rs.getInt("idQuestion"),
                        rs.getString("enonce"),
                        rs.getString("choix1"),
                        rs.getString("choix2"),
                        rs.getString("choix3"),
                        rs.getString("choix4"),
                        rs.getString("bonneReponse"),
                        rs.getInt("idQuiz")
                );
                questions.add(question);
            }
        }
        return questions;
    }

    // Récupérer une question par son ID
    public Question getQuestionById(int idQuestion) throws SQLException {
        Question question = null;
        String sql = "SELECT * FROM Question WHERE idQuestion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idQuestion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                question = new Question(
                        rs.getInt("idQuestion"),
                        rs.getString("enonce"),
                        rs.getString("choix1"),
                        rs.getString("choix2"),
                        rs.getString("choix3"),
                        rs.getString("choix4"),
                        rs.getString("bonneReponse"),
                        rs.getInt("idQuiz")
                );
            }
        }
        return question;
    }

    // Supprimer une question
    public void supprimerQuestion(int idQuestion) throws SQLException {
        String sql = "DELETE FROM Question WHERE idQuestion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idQuestion);
            stmt.executeUpdate();
        }
    }

    // Mettre à jour une question
    public void mettreAJourQuestion(Question question) throws SQLException {
        String sql = "UPDATE Question SET enonce = ?, choix1 = ?, choix2 = ?, choix3 = ?, choix4 = ?, bonneReponse = ? WHERE idQuestion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, question.getEnonce());
            stmt.setString(2, question.getChoix1());
            stmt.setString(3, question.getChoix2());
            stmt.setString(4, question.getChoix3());
            stmt.setString(5, question.getChoix4());
            stmt.setString(6, question.getBonneReponse());
            stmt.setInt(7, question.getIdQuestion());
            stmt.executeUpdate();
        }
    }
}

