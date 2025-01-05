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
        String query = "INSERT INTO quiz (titre, description, idEnseignant) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, quiz.getTitre());
        preparedStatement.setString(2, quiz.getDescription());
        preparedStatement.setInt(3, quiz.getIdEnseignant());

        // Exécutez la requête
        int affectedRows = preparedStatement.executeUpdate();

        // Si une ligne est affectée, récupérez l'ID généré
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    quiz.setIdQuiz(generatedKeys.getInt(1)); // Assignez l'ID généré
                } else {
                    System.out.println("Aucune clé générée !");
                }
            }
        } else {
            System.out.println("Aucune ligne affectée !");
        }

    } catch (SQLException ex) {
        ex.printStackTrace(); // Affichez les erreurs SQL pour déboguer
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
                quiz.setDescription(resultSet.getString("description"));
                quiz.setIdEnseignant(resultSet.getInt("idEnseignant"));
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
                quiz.setDescription(resultSet.getString("description"));
                quiz.setIdEnseignant(resultSet.getInt("idEnseignant"));

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
            String query = "UPDATE quiz SET titre = ?,description = ?, idEnseignant = ? WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setString(1, quiz.getTitre());
            preparedStatement.setString(2, quiz.getDescription());
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
    BDConnexion bdConnexion = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        bdConnexion = new BDConnexion();
        String query = "SELECT * FROM Quiz WHERE idEnseignant=?";
        preparedStatement = bdConnexion.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, professeurId);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Quiz quiz = new Quiz();
            quiz.setIdQuiz(resultSet.getInt("idQuiz"));
            quiz.setTitre(resultSet.getString("titre"));
            quiz.setDescription(resultSet.getString("description"));

            quizzes.add(quiz);
        }
    } catch (SQLException ex) {
        // Log l'erreur pour faciliter le débogage
        ex.printStackTrace();
        // Vous pouvez aussi afficher un message d'erreur à l'utilisateur si nécessaire
    } finally {
        // Fermeture des ressources dans le bloc finally pour garantir leur fermeture
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (bdConnexion != null) bdConnexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return quizzes;
}

}
