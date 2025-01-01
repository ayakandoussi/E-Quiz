package com.projetjava.controller;

import com.projetjava.domain.Quiz;
import com.projetjava.domain.Resultat;
import com.projetjava.domain.Session;
import com.projetjava.domain.Utilisateur;
import com.projetjava.model.dao.impl.QuizDao;
import com.projetjava.model.dao.impl.ResultatDao;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class ProfAccueilController {

    @FXML
    private VBox quizListVBox;

    @FXML
    private VBox resultsVBox;

    private QuizDao quizDao;  // DAO pour les quiz
    private ResultatDao resultatDao;  // DAO pour les résultats

    public ProfAccueilController() {
        quizDao = new QuizDao();  // Initialisation du DAO des quiz
        resultatDao = new ResultatDao();  // Initialisation du DAO des résultats
    }

    @FXML
    public void initialize() {
        // Récupérer l'utilisateur connecté depuis la session
        Utilisateur professeurConnecte = Session.getInstance().getUtilisateurConnecte();

        if (professeurConnecte != null) {
            // Charger les quiz associés au professeur
            loadQuiz(professeurConnecte.getId());
        } else {
            System.out.println("Aucun professeur connecté.");
        }
    }

    // Charger les quiz du professeur
    private void loadQuiz(int idProfesseur) {
        // Récupérer la liste des quiz pour ce professeur
        List<Quiz> quizList = quizDao.getQuizzesByProfesseur(idProfesseur);

        // Ajouter les quiz à la VBox
        Platform.runLater(() -> {
            quizListVBox.getChildren().clear();  // Réinitialiser la VBox
            for (Quiz quiz : quizList) {
                addQuizToList(quiz);  // Ajouter chaque quiz à la liste
            }
        });
    }

    // Ajouter un quiz à la VBox
    private void addQuizToList(Quiz quiz) {
        Label quizLabel = new Label(quiz.getTitre() + " - " + quiz.getDescription());
        Button detailsButton = new Button("Voir les résultats");

        detailsButton.setOnAction(event -> {
            showQuizResults(quiz);
        });

        VBox quizBox = new VBox(quizLabel, detailsButton);
        quizListVBox.getChildren().add(quizBox);
    }

    // Afficher les résultats pour un quiz
    private void showQuizResults(Quiz quiz) {
        System.out.println("Afficher les résultats pour le quiz : " + quiz.getTitre());

        // Récupérer les résultats des étudiants pour ce quiz
        List<Resultat> resultats = resultatDao.getResultatsByQuiz(quiz.getIdQuiz());
        for (Resultat resultat : resultats) {
            System.out.println(resultat.getEtudiant().afficher());
            addResultToList(resultat);
        }
        // Afficher les résultats dans la VBox
        Platform.runLater(() -> {
            resultsVBox.getChildren().clear();  // Réinitialiser la VBox des résultats

            if (resultats.isEmpty()) {
                Label emptyLabel = new Label("Aucun résultat disponible.");
                resultsVBox.getChildren().add(emptyLabel);
            } else {
                for (Resultat resultat : resultats) {
                    System.out.println(resultat.getEtudiant().afficher());
                    addResultToList(resultat);
                }
            }
        });
    }

    // Ajouter un résultat à la VBox
    private void addResultToList(Resultat resultat) {
        System.out.println(resultat.getEtudiant().getNom() + " " + resultat.getEtudiant().getPrenom());
        Label resultLabel = new Label(resultat.getEtudiant().getNom() + " " + resultat.getEtudiant().getPrenom() + " " + resultat.getScore());
        resultsVBox.getChildren().add(resultLabel);

    }
}
