package com.projetjava.controller;

import com.projetjava.domain.Professeur;
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
                ArrayList<Professeur> professeurs = utilisateurDao.getAllProfesseurs();
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
        label.getStyleClass().add("clickable-label");
        label.setStyle("-fx-text-fill: blue; -fx-underline: true;");
        label.setOnMouseClicked(event -> {
            label.setStyle("-fx-background-color: #0056b3; -fx-text-fill: red;");
            loadQuizByProfesseur(professeur);
        });
        profList.getChildren().add(label);
    }
    
    private void loadQuizByProfesseur(Utilisateur professeur) {
        executorService.submit(() -> {
            try {
                ArrayList<Quiz> quizzes = quizDao.getQuizzesByProfesseur(professeur.getId());
                System.out.println(professeur.getId());
                Platform.runLater(() -> {
                    quizGrid.getChildren().clear();
                    if (quizzes.isEmpty()) {
                        System.out.println("vide");
                        Label emptyLabel = new Label("Aucun quiz disponible.");
                        emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red; -fx-font-weight: bold;"); // Style
                        quizGrid.add(emptyLabel, 0, 0);
                    } else {
                        for (Quiz quiz : quizzes) {
                            addQuiz(quiz);
                        }
                    }
               
                });
            } catch (Exception e) {
            }
        });
    }
    
    private void addQuiz(Quiz quiz) {
        int row = quizGrid.getChildren().size() / 3;
        int column = quizGrid.getChildren().size() % 2;
        
        VBox quizBox = new VBox(5);
        Label label = new Label(quiz.getTitre() + "\n" + quiz.getDescription());
        Button button = new Button("Passer");
        button.setOnAction(event -> startQuiz(quiz));
        quizBox.getChildren().addAll(label, button);
    }
    
    private void startQuiz(Quiz quiz) {
        
        System.out.println("Démarrer le quiz: " + quiz.getTitre());
        //rediriger vers  page quiz

    }
    
}
