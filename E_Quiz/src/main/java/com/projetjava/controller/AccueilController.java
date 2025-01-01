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

                Platform.runLater(() -> {
                    for (Utilisateur professeur : professeurs) {
                        addProfesseur(professeur);
                    }
                });
            } catch (Exception e) {
            }
        });
    }

    private Label previousLabel = null;

    private void addProfesseur(Utilisateur professeur) {
        VBox professeurBox = new VBox(15);
        professeurBox.setPadding(new Insets(15));
        professeurBox.setStyle(
                "-fx-background-color: blue; "
                + "-fx-border-color: white; "
                + "-fx-border-width: 2px; "
                + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.5, 0, 4);"
        );
        professeurBox.setAlignment(Pos.CENTER);
        professeurBox.setMaxWidth(Double.MAX_VALUE);

        // Label pour afficher le nom du professeur avec un effet de survol
        Label label = new Label(professeur.getNom() + " " + professeur.getPrenom());
        label.getStyleClass().add("clickable-label");
        label.setStyle(
                "-fx-text-fill: white; "
                + "-fx-font-size: 16px; "
                + "-fx-font-weight: bold; "
                + "-fx-font-family: 'Arial', sans-serif; "
                + "-fx-opacity: 0.9;"
                + "-fx-cursor: hand;" // Indique que c'est un élément cliquable
        );

        // Changer le style au survol et le réinitialiser quand la souris quitte
        label.setOnMouseEntered(event -> {
            label.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-opacity: 1;"); // Couleur dorée au survol
            professeurBox.setStyle("-fx-background-color: #0056b3; -fx-border-color: black;"); // Bleu foncé avec bordure dorée
        });
        label.setOnMouseExited(event -> {
            label.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-opacity: 0.9;");
            professeurBox.setStyle("-fx-background-color: #007BFF; -fx-border-color: white;"); // Bleu original avec bordure blanche
        });

        label.setOnMouseClicked(event -> {
            if (previousLabel != null) {
                previousLabel.setStyle("-fx-text-fill: lightblue;");
            }

            label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
            loadQuizByProfesseur(professeur);
            previousLabel = label;
        });

        professeurBox.getChildren().add(label);
        profList.getChildren().add(professeurBox);
    }

    private void loadQuizByProfesseur(Utilisateur professeur) {
        executorService.submit(() -> {
            try {
                ArrayList<Quiz> quizzes = quizDao.getQuizzesByProfesseur(professeur.getId());

                Platform.runLater(() -> {
                    quizTilePane.getChildren().clear();
                    quizTilePane.setHgap(15);
                    quizTilePane.setVgap(15);

                    if (quizzes.isEmpty()) {
                        Label emptyLabel = new Label("Aucun quiz disponible.");
                        emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red; -fx-font-weight: bold;");
                        quizTilePane.getChildren().add(emptyLabel);
                    } else {
                        for (Quiz quiz : quizzes) {
                            VBox quizBox = new VBox(10);
                            quizBox.setPadding(new Insets(10));
                            quizBox.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: #007BFF; -fx-padding: 10; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.5, 0, 4);");
                            quizBox.setAlignment(Pos.CENTER);

                            Label labelTitre = new Label(quiz.getTitre());
                            labelTitre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

                            Label labelDescription = new Label(quiz.getDescription());
                            labelDescription.setWrapText(true);
                            labelDescription.setPrefWidth(200);
                            labelDescription.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
                            labelDescription.setAlignment(Pos.CENTER);

                            Button button = new Button("Passer");
                            button.setOnAction(event -> startQuiz(quiz));
                            button.setStyle("-fx-background-color: #007BFF; -fx-border-radius: 20; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 10;");
                            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #0056b3; -fx-border-radius: 20; -fx-text-fill: white; -fx-font-weight: bold;"));
                            button.setOnMouseExited(e -> button.setStyle("-fx-background-radius: 25px;-fx-background-color: #007BFF; -fx-border-radius: 20; -fx-text-fill: white; -fx-font-weight: bold;"));
                            button.setCursor(Cursor.HAND);
                            quizBox.getChildren().addAll(labelTitre, labelDescription, button);

                            quizTilePane.getChildren().add(quizBox);
                        }
                    }
                });
            } catch (Exception e) {
                // Gérer les erreurs
            }
        });
    }

    private void startQuiz(Quiz quiz) {

        System.out.println("Démarrer le quiz: " + quiz.getTitre());
        //rediriger vers  page quiz

    }

}
