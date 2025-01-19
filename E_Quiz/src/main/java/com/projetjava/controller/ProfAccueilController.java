package com.projetjava.controller;

import com.projetjava.domain.Etudiant;
import com.projetjava.domain.Professeur;
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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProfAccueilController {

    @FXML
    private VBox quizListVBox;

    @FXML
    private VBox resultsVBox;
    @FXML
    private Pane NavbarController;
    @FXML
    private ScrollPane resultsPane;
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
    @FXML
    private Pane AccueilPane;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView img_Bienvenue;
    @FXML
    private Label label_Bienvenue;

    private QuizDao quizDao;
    private ResultatDao resultatDao;

    public ProfAccueilController() {
        quizDao = new QuizDao();
        resultatDao = new ResultatDao();
    }

    @FXML
    public void initialize() {

        Utilisateur professeurConnecte = Session.getInstance().getUtilisateurConnecte();

        if (professeurConnecte != null) {
            afficherRole(professeurConnecte);
            loadQuiz(professeurConnecte.getId());
            Profil.setOnAction(event -> afficherProfil());
            Accueil.setOnAction(event -> afficherAccueil());
        } else {
            System.out.println("Aucun professeur connecté.");
        }
        if (AccueilPane != null) {
            AccueilPane.setVisible(true);
        }
        if (resultsPane != null) {
            resultsPane.setVisible(false);
        }

        styleVBox(quizListVBox);
        styleVBox(resultsVBox);

        Profil.setOnAction(event -> afficherProfil());
        Accueil.setOnAction(event -> afficherAccueil());
        SeDeconnecter.setOnAction(event -> seDeconnecter());

    }

    @FXML
    public void handleMouseEntered() {
        addQuizButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white;");
    }

    @FXML
    public void handleMouseExited() {
        addQuizButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
    }

    private void afficherRole(Utilisateur utilisateur) {
        String contenu;

        Professeur professeur = new Professeur(utilisateur);

        contenu = professeur.afficher();

        rolelabel.setText(contenu);
    }

    private void styleVBox(VBox vbox) {
        vbox.setStyle(
                "-fx-spacing: 10px;"
                + "-fx-padding: 15px;"
                + "-fx-background-color: #f5f5f5;"
                + "-fx-border-radius: 10px;"
                + "-fx-background-radius: 10px;"
                + "-fx-border-color: #ddd;"
        );
    }

    private void loadQuiz(int idProfesseur) {
        List<Quiz> quizList = quizDao.getQuizzesByProfesseur(idProfesseur);

        Platform.runLater(() -> {
            quizListVBox.getChildren().clear();
            for (Quiz quiz : quizList) {
                addQuizToList(quiz);
            }
        });
    }

    private void addQuizToList(Quiz quiz) {
        Label quizLabel = new Label(quiz.getTitre());
        quizLabel.setStyle(
                "-fx-font-size: 10px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: black;"
                + "-fx-wrap-text: true;"
                + "-fx-text-alignment: center;"
        );
        quizLabel.setMaxWidth(200);
        quizLabel.setAlignment(Pos.CENTER);

        Button detailsButton = new Button("Voir les résultats");
        detailsButton.setStyle(
                "-fx-background-color: blue;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 5px 10px;"
                + "-fx-font-size: 12px;"
                + "-fx-background-radius: 5px;"
                + "-fx-border-radius: 5px;"
                + "-fx-font-weight: bold;"
        );

        detailsButton.setCursor(Cursor.HAND);
        detailsButton.setOnMouseEntered(event -> detailsButton.setStyle(
                "-fx-background-color: #45a049;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 5px 10px;"
                + "-fx-font-size: 12px;"
                + "-fx-background-radius: 5px;"
                + "-fx-border-radius: 5px;"
                + "-fx-font-weight: bold;"
        ));

        detailsButton.setOnMouseExited(event -> detailsButton.setStyle(
                "-fx-background-color: blue;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 5px 10px;"
                + "-fx-font-size: 12px;"
                + "-fx-background-radius: 5px;"
                + "-fx-border-radius: 5px;"
                + "-fx-font-weight: bold;"
        ));

        detailsButton.setOnAction(event -> {
            showQuizResults(quiz);
            AccueilPane.setVisible(false);
            resultsPane.setVisible(true);
        });

        VBox quizBox = new VBox(15, quizLabel, detailsButton);
        quizBox.setStyle(
                "-fx-padding: 10px;"
                + "-fx-border-radius: 8px;"
                + "-fx-background-radius: 8px;"
                + "-fx-background-color: #ffffff;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.1), 5, 0, 0, 2);"
                + "-fx-margin-bottom: 10px;"
        );

        quizBox.setAlignment(Pos.CENTER);

        quizListVBox.getChildren().add(quizBox);
        quizListVBox.setAlignment(Pos.CENTER);
    }

    private void showQuizResults(Quiz quiz) {
        List<Resultat> resultats = resultatDao.getResultatsByQuiz(quiz.getIdQuiz());

        Platform.runLater(() -> {
            resultsVBox.getChildren().clear();

            if (resultats.isEmpty()) {
                Label emptyLabel = new Label("Aucun résultat disponible.");
                emptyLabel.setStyle(
                        "-fx-font-size: 16px;"
                        + "-fx-text-fill: black;"
                        + "-fx-padding: 10px ;"
                        + "-fx-text-weight : bold"
                );
                emptyLabel.setAlignment(Pos.CENTER);
                resultsVBox.getChildren().add(emptyLabel);
            } else {
                for (Resultat resultat : resultats) {
                    addResultToList(resultat);
                }
            }
        });
    }

    private void addResultToList(Resultat resultat) {

        Label nomLabel = new Label(resultat.getEtudiant().getNom());
        nomLabel.setStyle(
                "-fx-font-family: 'monospaced';"
                + "-fx-font-size: 13px;"
                + "-fx-text-fill: black;"
                + "-fx-font-weight: bold;"
        );
        nomLabel.setPrefWidth(150);
        Label prenomLabel = new Label(resultat.getEtudiant().getPrenom());
        prenomLabel.setStyle(
                "-fx-font-family: 'monospaced';"
                + "-fx-font-size: 13px;"
                + "-fx-text-fill: black;"
                + "-fx-font-weight: bold;"
        );
        prenomLabel.setPrefWidth(150);

        Label scoreLabel = new Label(String.valueOf(resultat.getScore()));
        scoreLabel.setStyle(
                "-fx-font-family: 'monospaced';"
                + "-fx-font-size: 13px;"
                + "-fx-text-fill: black;"
                + "-fx-font-weight: bold;"
        );
        scoreLabel.setPrefWidth(100);

        HBox resultBox = new HBox(10);
        resultBox.setPadding(new Insets(5, 10, 5, 10));
        resultBox.setStyle(
                "-fx-background-color: #f0f0f0;"
                + "-fx-border-color: #ddd;"
                + "-fx-border-radius: 5px;"
                + "-fx-background-radius: 5px;"
        );

        resultBox.getChildren().addAll(nomLabel, prenomLabel, scoreLabel);

        resultsVBox.getChildren().add(resultBox);
    }

    @FXML
    private void handleAddQuizButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/AjoutQuiz.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) addQuizButton.getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/ProfAccueil.fxml"));
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
}
