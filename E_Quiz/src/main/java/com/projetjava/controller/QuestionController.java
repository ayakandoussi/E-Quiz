package com.projetjava.controller;


import java.util.ArrayList;

import com.projetjava.domain.Question;
import com.projetjava.model.dao.impl.QuestionDAO;
import com.projetjava.model.dao.impl.QuizDao;
import com.projetjava.domain.Quiz;
import com.projetjava.model.dao.impl.BDConnexion;
import com.projetjava.domain.Utilisateur;
import com.projetjava.model.dao.impl.UtilisateurDao;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.sql.*;
import java.util.List;



public class QuestionController {

    // FXML pour les composants de l'interface
    @FXML private Label questionLabel; // Label pour la question
    @FXML private Label labelChoix1;   // Label pour le choix 1
    @FXML private Label labelChoix2;   // Label pour le choix 2
    @FXML private Label labelChoix3;   // Label pour le choix 3
    @FXML private Label labelChoix4;   // Label pour le choix 4

    @FXML private CheckBox choix1;     // CheckBox pour le choix 1
    @FXML private CheckBox choix2;     // CheckBox pour le choix 2
    @FXML private CheckBox choix3;     // CheckBox pour le choix 3
    @FXML private CheckBox choix4;     // CheckBox pour le choix 4

    @FXML private Button suivantButton; // Bouton pour passer à la question suivante
     
    
    
    private BDConnexion bdConnexion;
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private int idEtudiant; // Vous récupérerez l'ID de l'étudiant une fois qu'il est connecté
    private int idQuiz; // L'ID du quiz sélectionné
    
    
    @FXML
    private void initialize() throws SQLException {
        this.bdConnexion = new BDConnexion();
        this.questions = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.score = 0;

        // Charger les questions du quiz (idQuiz devrait être récupéré dynamiquement)
        chargerQuestions(idQuiz);
        afficherQuestion();
    }

    // Méthode pour charger les questions du quiz
    public void chargerQuestions(int idQuiz) throws SQLException {
        this.idQuiz = idQuiz;
        String query = "SELECT idQuestion, enonce, choix1, choix2, choix3, choix4, bonneReponse FROM Question WHERE idQuiz = ? ORDER BY idQuestion";
        PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, idQuiz);  // On passe l'ID du quiz

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int idQuestion = resultSet.getInt("idQuestion");
            String enonce = resultSet.getString("enonce");
            String choix1 = resultSet.getString("choix1");
            String choix2 = resultSet.getString("choix2");
            String choix3 = resultSet.getString("choix3");
            String choix4 = resultSet.getString("choix4");
            String bonneReponse = resultSet.getString("bonneReponse");

            Question question = new Question(idQuestion, enonce, choix1, choix2, choix3, choix4, bonneReponse, idQuiz);
            questions.add(question);
        }
    }
    
    // Méthode pour afficher la question actuelle
    private void afficherQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionLabel.setText(question.getEnonce());
            choix1.setText(question.getChoix1());
            choix2.setText(question.getChoix2());
            choix3.setText(question.getChoix3());
            choix4.setText(question.getChoix4());
        } else {
            // Fin du quiz
            // Afficher le score ou passer à la page suivante
        }
    }
   
    @FXML
    private void handleChoice() {
        // Si une case est cochée, on désélectionne les autres
        if (choix1.isSelected()) {
            choix2.setSelected(false);
            choix3.setSelected(false);
            choix4.setSelected(false);
        } else if (choix2.isSelected()) {
            choix1.setSelected(false);
            choix3.setSelected(false);
            choix4.setSelected(false);
        } else if (choix3.isSelected()) {
            choix1.setSelected(false);
            choix2.setSelected(false);
            choix4.setSelected(false);
        } else if (choix4.isSelected()) {
            choix1.setSelected(false);
            choix2.setSelected(false);
            choix3.setSelected(false);
        }
    }
    
    // Méthode pour gérer le clic sur le bouton "Suivant"
    @FXML
    private void questionSuivante() throws SQLException {
        // Enregistrer la réponse de l'étudiant
        enregistrerReponse();

        // Passer à la question suivante
        currentQuestionIndex++;

        // Afficher la nouvelle question
        afficherQuestion();
    }
    
    // Méthode pour enregistrer la réponse de l'étudiant
    private void enregistrerReponse() throws SQLException {
        Question question = questions.get(currentQuestionIndex);
        String reponse = getReponseChoisie();

        // Enregistrer la réponse dans la base de données
        String query = "INSERT INTO ResultatEtudiantQuestion (idEtudiant, idQuestion, reponse, estCorrect) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, idEtudiant);
        preparedStatement.setInt(2, question.getIdQuestion());
        preparedStatement.setString(3, reponse);
        preparedStatement.setBoolean(4, reponse.equals(question.getBonneReponse()));
        preparedStatement.executeUpdate();

        // Mettre à jour le score si la réponse est correcte
        if (reponse.equals(question.getBonneReponse())) {
            score++;
        }
    }

    // Méthode pour obtenir la réponse choisie par l'utilisateur
    private String getReponseChoisie() {
        if (choix1.isSelected()) {
            return choix1.getText();
        } else if (choix2.isSelected()) {
            return choix2.getText();
        } else if (choix3.isSelected()) {
            return choix3.getText();
        } else if (choix4.isSelected()) {
            return choix4.getText();
        }
        return ""; // Pas de réponse choisie
    }
}
    
    
    
    
    
    
    
    
    
    
