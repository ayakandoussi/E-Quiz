package com.projetjava.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LoginPageController implements Initializable {

    @FXML
    private Button ConnecterBtn;

    @FXML
    private Button CreateAccountBtn;

    @FXML
    private TextField EmailIn;

    @FXML
    private TextField EmailSignUp;

    @FXML
    private Button HaveAccountBtn1;

    @FXML
    private Button InscrireBtn;

    @FXML
    private PasswordField PasswordIn;

    @FXML
    private PasswordField PaswordSignUp;

    @FXML
    private ComboBox Role;

    @FXML
    private AnchorPane SignIn;

    @FXML
    private AnchorPane SignUp;

    @FXML
    private AnchorPane SignUpForm;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Prenom;
    @FXML
    private AnchorPane SideAlradyHaveAccount;
    @FXML
    private Label LabelAleady;
    @FXML
    private Label LabelDont;

    private Alert alert;

    public void regBtn2() {
        if (EmailSignUp.getText().isEmpty() || PaswordSignUp.getText().isEmpty() || Nom.getText().isEmpty() || Prenom.getText().isEmpty() || Role.getSelectionModel().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Remplissez tous les champs !!");
            alert.showAndWait();
        } else {
            // Connexion à la base de données
            String url = "jdbc:mysql://localhost:3306/e-quiz";
            String user = "root";
            String password = "";
            String query = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe,role) VALUES (?, ?, ?, ?,?)";

            try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(query)) {

                // Récupérer les valeurs des champs
                String nom = Nom.getText();
                String prenom = Prenom.getText();
                String email = EmailSignUp.getText();
                String mot_de_passe = PaswordSignUp.getText();
                String role = (String) Role.getSelectionModel().getSelectedItem();

                // Ajouter les valeurs au PreparedStatement
                pstmt.setString(1, nom);
                pstmt.setString(2, prenom);
                pstmt.setString(3, email);
                pstmt.setString(4, mot_de_passe);
                pstmt.setString(5, role);

                // Exécuter la requête
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Utilisateur enregistré avec succès !");
                    alert.showAndWait();
                    
                    
                }
            } catch (SQLException e) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Erreur lors de l'enregistrement : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void regBtn() {
        if (EmailIn.getText().isEmpty() || PasswordIn.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Remplissez tous les champs !!!");
            alert.showAndWait();
        } else {
         
        // Connexion à la base de données
        String url = "jdbc:mysql://localhost:3306/e-quiz"; // Remplacez par le nom de votre base
        String user = "root"; // Votre utilisateur MySQL
        String password = ""; // Votre mot de passe MySQL
        String query = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Récupérer les valeurs des champs
            String email = EmailIn.getText();
            String motDePasse = PasswordIn.getText();

            // Ajouter les valeurs au PreparedStatement
            pstmt.setString(1, email);
            pstmt.setString(2, motDePasse);

            // Exécuter la requête
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Si un utilisateur correspond
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Connexion réussie");
                alert.setHeaderText(null);
                alert.setContentText("Bienvenue, " + rs.getString("nom") + " " + rs.getString("prenom") + " !");
                alert.showAndWait();
                
                // Rediriger l'utilisateur vers une autre interface ou tableau de bord
                // Exemple : openDashboard();
            } else {
                // Si aucun utilisateur ne correspond
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Email ou mot de passe incorrect !");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Gérer les exceptions SQL
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la connexion : " + e.getMessage());
            alert.showAndWait();
        }
    }
        
    }

    @FXML
    public void Switchform(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == CreateAccountBtn) {
            slider.setNode(SignUp);
            slider.setToX(400);  // Déplace le panneau SignUp
            slider.setDuration(Duration.seconds(0.5));

            // Action après la transition
            slider.setOnFinished(e -> {
                HaveAccountBtn1.setVisible(true);
                CreateAccountBtn.setVisible(false);
                SignIn.setVisible(false);
                LabelDont.setVisible(false);
                LabelAleady.setVisible(true);

            });

            slider.play(); // Démarre l'animation
        } else if (event.getSource() == HaveAccountBtn1) {
            slider.setNode(SignUp);

            slider.setToX(0);
            slider.setDuration(Duration.seconds(0.5));

            // Action après la transition
            slider.setOnFinished(e -> {
                HaveAccountBtn1.setVisible(false);
                CreateAccountBtn.setVisible(true);
                SignIn.setVisible(true);
                LabelDont.setVisible(true);
                LabelAleady.setVisible(false);

            });

            slider.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("Professeur", "Etudiant");
        Role.setItems(list);

    }

}
