package com.projetjava.model.dao.impl;

import com.projetjava.domain.Quiz;

import com.projetjava.model.dao.Dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizDao implements Dao<Quiz> {

    @Override
    public void add(Quiz quiz) {
        try {
            BDConnexion bd = new BDConnexion();
            String query = "INSERT INTO quiz (titre, theme, id_enseignant) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, quiz.getTitre());
            preparedStatement.setString(2, quiz.getTheme());
            preparedStatement.setInt(3, quiz.getIdEnseignant());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

        }
    }

    @Override
    public Quiz getById(int id) {
        Quiz quiz = null;
        try {
            BDConnexion bd = new BDConnexion();
            String query = "SELECT * FROM quiz WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                quiz = new Quiz();
                quiz.setIdQuiz(resultSet.getInt("id"));
                quiz.setTitre(resultSet.getString("titre"));
                quiz.setTheme(resultSet.getString("theme"));
                quiz.setIdEnseignant(resultSet.getInt("id_enseignant"));
            }

            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {

        }
        return quiz;
    }

    @Override

    public ArrayList<Quiz> getAll() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quiz";

        try {
            BDConnexion bd = new BDConnexion();
            Statement statement = bd.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setIdQuiz(resultSet.getInt("id"));
                quiz.setTitre(resultSet.getString("titre"));
                quiz.setTheme(resultSet.getString("theme"));
                quiz.setIdEnseignant(resultSet.getInt("id_enseignant"));

                // Ajout du quiz à la liste
                quizzes.add(quiz);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // Utilisez un logger dans un projet réel
        }

        return quizzes;
    }

    @Override
    public void update(Quiz quiz) {
        try {
            BDConnexion bd = new BDConnexion();
            String query = "UPDATE quiz SET titre = ?,theme = ?, id_enseignant = ? WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setString(1, quiz.getTitre());
            preparedStatement.setString(2, quiz.getTheme());
            preparedStatement.setInt(3, quiz.getIdEnseignant());
            preparedStatement.setInt(4, quiz.getIdQuiz());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {

        }
    }

    @Override
    public void delete(int id) {
        try {
            BDConnexion bd = new BDConnexion();

            // Supprimer le quiz
            String query = "DELETE FROM quiz WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {

        }
    }

    public ArrayList<Quiz> getQuizzesByProfesseur(int professeurId) {
        ArrayList<Quiz> quizzes = new ArrayList<>();

        try {
            BDConnexion bdConnexion = new BDConnexion();
            String query = "SELECT * FROM quiz WHERE professeur.id=?";
            PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, professeurId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setIdQuiz(resultSet.getInt("id"));
                quiz.setTitre(resultSet.getString("titre"));
                quiz.setTheme(resultSet.getString("theme"));

                quizzes.add(quiz);
            }
            preparedStatement.close();
            bdConnexion.close();
        } catch (SQLException ex) {

        }

        return quizzes;
    }
}
