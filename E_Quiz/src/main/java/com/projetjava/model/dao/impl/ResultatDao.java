package com.projetjava.model.dao.impl;

import com.projetjava.domain.Resultat;
import com.projetjava.model.dao.Dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultatDao implements Dao<Resultat> {

    @Override
    public void add(Resultat resultat) {
        try {
            BDConnexion bdConnexion = new BDConnexion();
            String query = "INSERT INTO `resultat` (`idResultatQuiz`, `etudiantId`, `quizId`, `score`) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query);

            preparedStatement.setInt(1, resultat.getIdResultatQuiz());
            preparedStatement.setInt(2, resultat.getEtudiant().getIdEtudiant());
            preparedStatement.setInt(3, resultat.getQuiz().getIdQuiz());
            preparedStatement.setDouble(4, resultat.getScore());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            bdConnexion.close();
        } catch (SQLException ex) {
        }
    }

    @Override
    public void update(Resultat resultat) {
        try {
            BDConnexion bdConnexion = new BDConnexion();
            String query = "UPDATE `resultat` SET `etudiantId`=?, `quizId`=?, `score`=? WHERE `idResultatQuiz`=?";
            PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query);

            preparedStatement.setInt(1, resultat.getEtudiant().getIdEtudiant());
            preparedStatement.setInt(2, resultat.getQuiz().getIdQuiz());
            preparedStatement.setDouble(3, resultat.getScore());
            preparedStatement.setInt(4, resultat.getIdResultatQuiz());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            bdConnexion.close();
        } catch (SQLException ex) {

        }
    }

    @Override
    public ArrayList<Resultat> getAll() {
        ArrayList<Resultat> resultats = new ArrayList<Resultat>();
        try {
            BDConnexion bdConnexion = new BDConnexion();
            String query = "SELECT * FROM `resultat`";
            PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Resultat resultat = new Resultat();
                resultat.setIdResultatQuiz(resultSet.getInt("idResultatQuiz"));
                resultat.setScore(resultSet.getDouble("score"));

                Etudiant etudiant = new Etudiant();
                etudiant.setIdEtudiant(resultSet.getInt("etudiantId"));
                resultat.setEtudiant(etudiant);

                Quiz quiz = new Quiz();
                quiz.setIdQuiz(resultSet.getInt("quizId"));
                resultat.setQuiz(quiz);

                resultats.add(resultat);
            }

            preparedStatement.close();
            bdConnexion.close();
        } catch (SQLException ex) {
        }
        return resultats;
    }

    @Override
    public Resultat getById(int idResultatQuiz) {
        Resultat resultat = null;
        try {
            BDConnexion bdConnexion = new BDConnexion();
            String query = "SELECT * FROM `resultat` WHERE `idResultatQuiz`=?";
            PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query);

            preparedStatement.setInt(1, idResultatQuiz);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultat = new Resultat();
                resultat.setIdResultatQuiz(resultSet.getInt("idResultatQuiz"));
                resultat.setScore(resultSet.getDouble("score"));

                Etudiant etudiant = new Etudiant();
                etudiant.setIdEtudiant(resultSet.getInt("etudiantId"));
                resultat.setEtudiant(etudiant);

                Quiz quiz = new Quiz();
                quiz.setIdQuiz(resultSet.getInt("quizId"));
                resultat.setQuiz(quiz);
            }

            preparedStatement.close();
            bdConnexion.close();
        } catch (SQLException ex) {
        }
        return resultat;
    }

    @Override
    public void delete(int idResultatQuiz) {
        try {
            BDConnexion bdConnexion = new BDConnexion();
            String query = "DELETE FROM `resultat` WHERE `idResultatQuiz`=?";
            PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query);

            preparedStatement.setInt(1, idResultatQuiz);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            bdConnexion.close();
        } catch (SQLException ex) {

        }
    }

}
