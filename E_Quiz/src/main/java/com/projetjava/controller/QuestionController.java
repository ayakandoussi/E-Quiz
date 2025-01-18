package com.projetjava.controller;

import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import com.projetjava.domain.Etudiant;
import com.projetjava.domain.Professeur;
import com.projetjava.domain.Session;
import com.projetjava.domain.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.projetjava.domain.Question;
import com.projetjava.domain.Quiz;
import com.projetjava.exceptions.BonChoixException;
import com.projetjava.model.dao.impl.BDConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class QuestionController {

    @FXML
    private Label questionLabel;
    @FXML
    private RadioButton choix1;
    @FXML
    private RadioButton choix2;
    @FXML
    private RadioButton choix3;
    @FXML
    private RadioButton choix4;
    @FXML
    private ToggleGroup choix;
    @FXML
    private Button suivantButton;

    @FXML
    private Pane NavbarController;

    @FXML
    private MenuButton menu;
    @FXML
    private MenuItem Accueil;
    @FXML
    private MenuItem Profil;
    @FXML
    private MenuItem SeDeconnecter;
    @FXML
    private Button addQuizButton;
    @FXML
    private Label rolelabel;

    private BDConnexion bdConnexion;
    private List<Question> questions;
    private int currentQuestionIndex;
    private double score;
    private int idQuiz;
    private int idEtudiant;

    @FXML
    private void initialize() throws SQLException {
        this.bdConnexion = new BDConnexion();
        this.questions = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.score = 0;

        // Récupérer l'utilisateur connecté via la session
        Utilisateur etudiantConnecte = Session.getInstance().getUtilisateurConnecte();

        if (etudiantConnecte != null) {
            afficherRole(etudiantConnecte);

        } else {
            System.out.println("Aucun professeur connecté.");
        }

    }

    private void afficherRole(Utilisateur utilisateur) {
        String contenu;

        Etudiant etudiant = new Etudiant(utilisateur);

        contenu = etudiant.afficher();

        rolelabel.setText(contenu);
    }

    @FXML
    public void selectionnerQuiz(Quiz quizSelectionne) throws SQLException, BonChoixException {
        int idQuiz = quizSelectionne.getIdQuiz(); // ID du quiz sélectionné
        int idEtudiant = Session.getInstance().getUtilisateurConnecte().getId(); // Récupérer l'ID de l'étudiant

        chargerQuestions(idQuiz);

        // Afficher la première question
        afficherQuestion();
    }

    // Méthode pour définir l'ID du quiz et de l'étudiant dynamiquement
    public void setIdQuiz(int idQuiz, int idEtudiant) throws SQLException, BonChoixException {
        this.idQuiz = idQuiz;
        this.idEtudiant = idEtudiant;

        // Charger les questions pour ce quiz
        chargerQuestions(idQuiz);

        // Afficher la première question
        afficherQuestion();
    }

    // Méthode pour charger les questions du quiz à partir de la base de données
    private void chargerQuestions(int idQuiz) throws SQLException, BonChoixException {
        String query = "SELECT idQuestion, enonce, choix1, choix2, choix3, choix4, bonneReponse FROM Question WHERE idQuiz = ? ORDER BY idQuestion";
        PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, idQuiz);

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
            // Lorsque toutes les questions sont répondues, afficher la page des résultats
            afficherResultats();
        }
    }

    // Méthode pour obtenir la réponse choisie par l'utilisateur
    private String getReponseChoisie() {
        Toggle selectedToggle = choix.getSelectedToggle();
        if (selectedToggle != null) {
            RadioButton selectedRadioButton = (RadioButton) selectedToggle;
            return selectedRadioButton.getText();
        }
        return "";
    }

    // Méthode pour vérifier la réponse donnée par l'utilisateur
    public boolean verifierReponse(String reponse) {
        Question questionActuelle = questions.get(currentQuestionIndex);
        return reponse.equals(questionActuelle.getBonneReponse());
    }

    // Méthode pour générer un certain nombre de questions aléatoires
    public void genererQuestions(int nombreQuestions) throws SQLException {
        Collections.shuffle(questions);  // Mélange les questions
        questions = questions.subList(0, Math.min(nombreQuestions, questions.size()));  // Limite le nombre de questions
    }

    private void reinitialiserQuiz() throws SQLException, BonChoixException {
        // Réinitialiser le score
        score = 0;
        currentQuestionIndex = 0;  // Revenir à la première question

        // Recharger les questions pour le quiz (si elles ont été chargées précédemment)
        questions.clear();  // Vider la liste actuelle des questions
        chargerQuestions(idQuiz);  // Recharger les questions pour ce quiz

        // Afficher la première question
        afficherQuestion();
    }

    // Méthode pour passer à la question suivante
    @FXML
    private void questionSuivante() {
        // Vérifier si une réponse a été sélectionnée
        RadioButton selectedRadioButton = (RadioButton) choix.getSelectedToggle();
        if (selectedRadioButton != null) {
            String reponseChoisie = selectedRadioButton.getText();
            // Vérifier si la réponse est correcte
            if (verifierReponse(reponseChoisie)) {
                score++; // Incrémenter le score si la réponse est correcte
            }
        }

        // Passer à la question suivante
        currentQuestionIndex++;

        // Vérifier si c'est la dernière question
        if (currentQuestionIndex < questions.size()) {
            afficherQuestion();
        } else {
            // Lorsque toutes les questions sont répondues, afficher la page des résultats
            afficherResultats();
        }

        // Réinitialiser la sélection des réponses
        choix.selectToggle(null);
    }

    private void afficherResultats() {
        try {
            // Charger la scène des résultats
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/Resultat.fxml"));
            Parent root = loader.load();

            // Passer le score à la scène des résultats
            ResultatPageController resultatController = loader.getController();
            resultatController.afficherResultat(score);  // Passer le score à la méthode afficherResultat()

            // Charger la scène dans la fenêtre actuelle
            Scene scene = new Scene(root);
            Stage stage = (Stage) questionLabel.getScene().getWindow(); // Récupérer la fenêtre actuelle
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
