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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

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

    @FXML
    private TilePane quizTilePane;
    @FXML
    private AnchorPane rootPane;

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
        /*Liaison dynamique pour ScrollPane
        quizScrollPane.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.8));
        quizScrollPane.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.8));

        // Configuration du TilePane
        quizTilePane.prefColumnsProperty().set(3); // Colonnes fixes
        quizTilePane.setPadding(new Insets(10)); // Marges autour du contenu
        quizTilePane.setHgap(20); // Espacement horizontal
        quizTilePane.setVgap(20); // Espacement vertical

        // Ajustement des éléments dans le TilePane
        quizTilePane.getChildren().forEach(node -> {
            node.setStyle("-fx-pref-width: 150; -fx-pref-height: 150;"); // Dimensions uniformes
        });*/
        loadProfesseurs();
    }

    private void loadProfesseurs() {
        executorService.submit(() -> {
            try {
                ArrayList<Professeur> professeurs = utilisateurDao.getAllProfesseurs();
                for (Professeur prof : professeurs) {
                    System.out.println("Professeur: " + prof.getId() + ", " + prof.getNom() + " " + prof.getPrenom());
                }
                Platform.runLater(() -> {
                    for (Utilisateur professeur : professeurs) {
                        addProfesseur(professeur);
                    }
                });
            } catch (Exception e) {
            }
        });
    }

    private Label previousLabel = null; // Référence pour le label précédemment sélectionné

    private void addProfesseur(Utilisateur professeur) {
        // Crée une nouvelle VBox pour le professeur
        VBox professeurBox = new VBox(10);
        professeurBox.setPadding(new Insets(10));
        professeurBox.setStyle("-fx-background-color: blue; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-border-color: white; -fx-border-width: 1px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 5, 0.5, 0, 5);");
        professeurBox.setAlignment(Pos.CENTER);

        // Label pour afficher le nom du professeur
        Label label = new Label(professeur.getNom() + " " + professeur.getPrenom());
        label.getStyleClass().add("clickable-label");
        label.setStyle("-fx-text-fill: white;  -fx-font-size: 16px; -fx-font-weight: bold;");
        label.setOnMouseClicked(event -> {
            // Réinitialiser le style du précédent label sélectionné
            if (previousLabel != null) {
                previousLabel.setStyle("-fx-text-fill: lightblue;"); // style de base
            }

            // Mettre le style du label cliqué en rouge
            label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

            // Charger les quiz du professeur sélectionné
            loadQuizByProfesseur(professeur);

            // Mettre à jour le label précédent
            previousLabel = label;
        });

        // Ajouter le label au VBox
        professeurBox.getChildren().add(label);

        // Ajouter le VBox (avec le nom du professeur) à la liste des professeurs
        profList.getChildren().add(professeurBox);
    }

    private void loadQuizByProfesseur(Utilisateur professeur) {
        executorService.submit(() -> {
            try {
                ArrayList<Quiz> quizzes = quizDao.getQuizzesByProfesseur(professeur.getId());

                Platform.runLater(() -> {
                    quizTilePane.getChildren().clear();
                    quizTilePane.setHgap(15); // Espace horizontal
                    quizTilePane.setVgap(15); // Espace vertical

                    if (quizzes.isEmpty()) {
                        Label emptyLabel = new Label("Aucun quiz disponible.");
                        emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red; -fx-font-weight: bold;");
                        quizTilePane.getChildren().add(emptyLabel);
                    } else {
                        for (Quiz quiz : quizzes) {
                            VBox quizBox = new VBox(10);
                            quizBox.setPadding(new Insets(10));
                            quizBox.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: blue; -fx-padding: 10; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.5, 0, 4);");
                            quizBox.setAlignment(Pos.CENTER);

                            // Titre du quiz
                            Label labelTitre = new Label(quiz.getTitre());
                            labelTitre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                            labelTitre.setAlignment(Pos.CENTER);

                            // Description du quiz
                            Label labelDescription = new Label(quiz.getDescription());
                            labelDescription.setWrapText(true);
                            labelDescription.setPrefWidth(200);
                            labelDescription.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
                            labelDescription.setAlignment(Pos.CENTER);

                            // Bouton "Passer"
                            Button button = new Button("Passer");
                            button.setOnAction(event -> startQuiz(quiz));
                            button.setStyle("-fx-background-color: blue;-fx-border-radius: 20; -fx-text-fill: white;-fx-font-weight: bold; -fx-padding: 5 10;");
                            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: black; -fx-border-radius: 20; -fx-text-fill: white; -fx-font-weight: bold;"));
                            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: blue; -fx-border-radius: 20; -fx-text-fill: white; -fx-font-weight: bold;"));
                            button.setCursor(Cursor.HAND);
                            // Ajouter les éléments au VBox
                            quizBox.getChildren().addAll(labelTitre, labelDescription, button);

                            // Ajouter le VBox au TilePane
                            quizTilePane.getChildren().add(quizBox);
                        }
                    }
                });
            } catch (Exception e) {

            }
        });
    }

    private void startQuiz(Quiz quiz) {

        System.out.println("Démarrer le quiz: " + quiz.getTitre());
        //rediriger vers  page quiz

    }

}
