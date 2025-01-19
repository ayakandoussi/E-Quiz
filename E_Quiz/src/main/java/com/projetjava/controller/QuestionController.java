package com.projetjava.controller;

import javafx.scene.control.ToggleGroup;
import com.projetjava.domain.Etudiant;
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
        idQuiz = quizSelectionne.getIdQuiz();
        int idEtudiant = Session.getInstance().getUtilisateurConnecte().getId();
        chargerQuestions(idQuiz);
        afficherQuestion();
    }

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

    private void afficherQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionLabel.setText(question.getEnonce());
            choix1.setText(question.getChoix1());
            choix2.setText(question.getChoix2());
            choix3.setText(question.getChoix3());
            choix4.setText(question.getChoix4());
        } else {
            afficherResultats();
        }
    }

    public boolean verifierReponse(String reponse) {
        Question questionActuelle = questions.get(currentQuestionIndex);
        return reponse.equals(questionActuelle.getBonneReponse());
    }

    public void genererQuestions(int nombreQuestions) throws SQLException {
        Collections.shuffle(questions);
        questions = questions.subList(0, Math.min(nombreQuestions, questions.size()));
    }

    @FXML
    private void questionSuivante() {
        RadioButton selectedRadioButton = (RadioButton) choix.getSelectedToggle();
        if (selectedRadioButton != null) {
            String reponseChoisie = selectedRadioButton.getText();
            if (verifierReponse(reponseChoisie)) {
                score++;
            }
        }
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            afficherQuestion();
        } else {
            afficherResultats();
        }

        choix.selectToggle(null);
    }

    private void afficherResultats() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/Resultat.fxml"));
            Parent root = loader.load();
            ResultatPageController resultatController = loader.getController();
            resultatController.afficherResultat(score, idQuiz);
            Scene scene = new Scene(root);
            Stage stage = (Stage) questionLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
