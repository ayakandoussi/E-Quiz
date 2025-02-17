package com.projetjava.controller;

import com.projetjava.domain.Etudiant;
import com.projetjava.domain.Professeur;
import com.projetjava.domain.Quiz;
import com.projetjava.domain.Session;
import com.projetjava.domain.Utilisateur;
import com.projetjava.model.dao.impl.QuizDao;
import com.projetjava.model.dao.impl.UtilisateurDao;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class EtudiantAccueilController {

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
    private Label rolelabel;

    private boolean quizVisible = true;
    private UtilisateurDao utilisateurDao;
    private QuizDao quizDao;
    private ExecutorService executorService;

    public EtudiantAccueilController() {
        this.utilisateurDao = new UtilisateurDao();
        this.quizDao = new QuizDao();
        this.executorService = Executors.newFixedThreadPool(2);
    }

    @FXML
    public void initialize() {
        Utilisateur utilisateurConnecte = Session.getInstance().getUtilisateurConnecte();

        if (utilisateurConnecte != null) {
            afficherRole(utilisateurConnecte);
            loadProfesseurs();
        Profil.setOnAction(event -> afficherProfil());
        Accueil.setOnAction(event -> afficherAccueil());
        SeDeconnecter.setOnAction(event -> seDeconnecter());
        } else {
            System.out.println("Aucun utilisateur connecté.");
        }

        if (AccueilPane != null) {
            AccueilPane.setVisible(true);
        }
        if (quizTilePane != null) {
            quizTilePane.setVisible(false);
        }

        

    }

    private void afficherRole(Utilisateur utilisateur) {
        String contenu;

        Etudiant etudiant = new Etudiant(utilisateur);

        contenu = etudiant.afficher();

        rolelabel.setText(contenu);
    }

    private void loadProfesseurs() {
        executorService.submit(() -> {
            try {
                ArrayList<Professeur> professeurs = utilisateurDao.getAllProfesseurs();
                System.out.println("Nombre de professeurs récupérés : " + professeurs.size());

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
        VBox professeurBox = new VBox(15);
        professeurBox.setPadding(new Insets(15));
        professeurBox.setStyle("-fx-background-color: blue; -fx-border-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.5, 0, 4);");
        professeurBox.setAlignment(Pos.CENTER);
        professeurBox.setMaxWidth(Double.MAX_VALUE);

        Label label = new Label(professeur.getFilomod());

        label.setWrapText(true); 
        label.setTextAlignment(TextAlignment.CENTER); 
        label.setMinHeight(Region.USE_PREF_SIZE);
        label.setMaxWidth(200);
        label.setPrefWidth(Region.USE_COMPUTED_SIZE);

        label.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Arial', sans-serif; -fx-opacity: 0.9; -fx-cursor: hand;");

        label.setOnMouseEntered(event -> {
            label.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-opacity: 1;");
            professeurBox.setStyle("-fx-background-color: #45a049;");
        });

        label.setOnMouseExited(event -> {
            label.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-opacity: 0.9;");
            professeurBox.setStyle("-fx-background-color: blue; -fx-border-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.5, 0, 4);");
        });

        label.setOnMouseClicked(event -> {
            label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
            loadQuizByProfesseur(professeur);

            if (quizVisible) {
                AccueilPane.setVisible(true);
                quizTilePane.setVisible(false);
                quizVisible = false;
            } else {
                AccueilPane.setVisible(false);
                quizTilePane.setVisible(true);
                quizVisible = true;
            }
        });

        professeurBox.getChildren().add(label);
        professeurBox.setMinWidth(200); 
        professeurBox.setPrefWidth(Region.USE_COMPUTED_SIZE); 
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
                        emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black; -fx-font-weight: bold;");
                        quizTilePane.getChildren().add(emptyLabel);
                    } else {
                        for (Quiz quiz : quizzes) {
                            VBox quizBox = new VBox(10);
                            quizBox.setPadding(new Insets(10));
                            quizBox.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: blue; -fx-padding: 10; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.5, 0, 4);");
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
                            button.setStyle("-fx-background-color: blue; -fx-border-radius: 20; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 10;");
                            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #45a049; -fx-border-radius: 20; -fx-text-fill: white; -fx-font-weight: bold;"));
                            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: blue; -fx-border-radius: 20; -fx-text-fill: white; -fx-font-weight: bold;"));

                            button.setCursor(Cursor.HAND);
                            quizBox.getChildren().addAll(labelTitre, labelDescription, button);

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/QuizPage.fxml"));
            AnchorPane quizPage = loader.load();
            QuestionController questionController = loader.getController(); 
            questionController.selectionnerQuiz(quiz);
            Scene quizScene = new Scene(quizPage);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(quizScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void afficherProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/Profil.fxml"));
            Parent Profil = loader.load();
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(Profil));
            stage.show();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void afficherAccueil() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/EtudiantAccueil.fxml"));
            Parent EtudiantAccueil = loader.load();

            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(EtudiantAccueil));

            stage.show();
        } catch (Exception e) {

        }
    }

    public void seDeconnecter() {
        try {
            Session.getInstance().setUtilisateurConnecte(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/LoginPage.fxml"));
            Parent LoginPage = loader.load();
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(LoginPage));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }

    @FXML
    public void onClose() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
