package com.projetjava.controller;

import com.projetjava.domain.Quiz;
import com.projetjava.domain.Utilisateur;
import com.projetjava.model.dao.impl.QuizDao;
import com.projetjava.model.dao.impl.UtilisateurDao;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.control.Button;

public class AccueilController {

    @FXML
    private Pane AccueilPane;

    @FXML
    private ImageView img_Bienvenue;

    @FXML
    private Label label_Bienvenue;

    @FXML
    private VBox profList;

    @FXML
    private ScrollPane profScrollPane;

    @FXML
    private GridPane quizGrid;

    @FXML
    private ScrollPane quizScrollPane;

    private UtilisateurDao utilisateurDao;
    private QuizDao quizDao;

    private ExecutorService executorService;

    public AccueilController() {
        this.utilisateurDao = new UtilisateurDao();
        this.quizDao = new QuizDao();
        this.executorService = Executors.newFixedThreadPool(2); // Création d'un pool de 2 threads
    }

    @FXML
    public void initialize() {
        loadProfesseurs();
    }

    private void loadProfesseurs() {
        executorService.submit(() -> {
            try {
                ArrayList<Utilisateur> professeurs = utilisateurDao.getAll();
                Platform.runLater(() -> {
                    for (Utilisateur professeur : professeurs) {
                        addProfesseur(professeur);
                    }
                });
            } catch (Exception e) {
            }
        });
    }

    private void addProfesseur(Utilisateur professeur) {
        Label label = new Label(professeur.getNom() + " " + professeur.getPrenom());
        label.setOnMouseClicked(event -> loadQuizByProfesseur(professeur));
        profList.getChildren().add(label);
    }

    private void loadQuizByProfesseur(Utilisateur professeur) {
        executorService.submit(() -> {
            try {
                ArrayList<Quiz> quizzes = quizDao.getQuizzesByProfesseur(professeur.getId());
                Platform.runLater(() -> {
                    quizGrid.getChildren().clear();
                    for (Quiz quiz : quizzes) {
                        addQuiz(quiz);
                    }
                });
            } catch (Exception e) {
            }
        });
    }

    private void addQuiz(Quiz quiz) {
        int row = quizGrid.getChildren().size() / 3;
        int column = quizGrid.getChildren().size() % 2;

        Label label = new Label(quiz.getTitre() + "\n" + quiz.getDescription());
        quizGrid.add(label, column, row);
        Button button = new Button("Passer");
        button.setOnAction(event -> startQuiz(quiz));
        quizGrid.add(button, column, row + 1);
    }

    private void startQuiz(Quiz quiz) {

        System.out.println("Démarrer le quiz: " + quiz.getTitre());
        //rediriger vers  page quiz
        
    }

}
