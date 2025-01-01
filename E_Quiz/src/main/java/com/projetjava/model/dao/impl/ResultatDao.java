package com.projetjava.model.dao.impl;

import com.projetjava.domain.Etudiant;
import com.projetjava.domain.Quiz;
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
            preparedStatement.setInt(2, resultat.getEtudiant().getId());
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

            preparedStatement.setInt(1, resultat.getEtudiant().getId());
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
                etudiant.setId(resultSet.getInt("etudiantId"));
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
                etudiant.setId(resultSet.getInt("etudiantId"));
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
// Récupérer les résultats d'un quiz donné

    public ArrayList<Resultat> getResultatsByQuiz(int quizId) {
        ArrayList<Resultat> resultats = new ArrayList<>();

        try {
            // Création de la connexion à la base de données
            BDConnexion bdConnexion = new BDConnexion();

            // Requête SQL corrigée
            String query = "SELECT r.idResultatQuiz, r.score, r.idEtudiant, q.idQuiz, e.nom, e.prenom "
                    + "FROM resultatquiz r "
                    + "JOIN utilisateur e ON r.idEtudiant = e.id "
                    + "JOIN quiz q ON r.idQuiz = q.idQuiz "
                    + "WHERE r.idQuiz = ?";
            PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query);

            // Remplacement du paramètre dans la requête
            preparedStatement.setInt(1, quizId);

            // Exécution de la requête
            ResultSet resultSet = preparedStatement.executeQuery();

            // Traitement des résultats
            while (resultSet.next()) {
                Resultat resultat = new Resultat();
                resultat.setIdResultatQuiz(resultSet.getInt("idResultatQuiz"));
                resultat.setScore(resultSet.getDouble("score"));

                // Récupérer l'étudiant associé
                Etudiant etudiant = new Etudiant();
                etudiant.setId(resultSet.getInt("idEtudiant"));
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));
                resultat.setEtudiant(etudiant);

                // Récupérer le quiz associé
                Quiz quiz = new Quiz();
                quiz.setIdQuiz(resultSet.getInt("idQuiz"));
                resultat.setQuiz(quiz);

                // Ajouter le résultat à la liste
                resultats.add(resultat);
            }

            // Fermeture des ressources
            preparedStatement.close();
            bdConnexion.close();
        } catch (SQLException ex) {
            // Gestion et affichage des erreurs
            System.err.println("Erreur lors de la récupération des résultats : " + ex.getMessage());
            ex.printStackTrace();
        }

        return resultats;
    }

}
