package com.projetjava.controller;

import com.projetjava.domain.Session;
import com.projetjava.domain.Utilisateur;
import com.projetjava.domain.Professeur;
import com.projetjava.domain.Etudiant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class ProfilController {

    @FXML
    private TextField nomfield;
    @FXML
    private TextField prenomfiled;
    @FXML
    private TextField emailfield;
    @FXML
    private TextField rolefield;
    @FXML
    private Button bouttonmodifier;
    @FXML
    private Label roleLabel;
    @FXML
    private MenuButton menu;
    @FXML
    private MenuItem Accueil;
    @FXML
    private MenuItem Profil;
    @FXML
    private MenuItem SeDeconnecter;

    @FXML
    public void initialize() {
        Utilisateur utilisateurConnecte = Session.getInstance().getUtilisateurConnecte();

        if (utilisateurConnecte != null) {
            nomfield.setText(utilisateurConnecte.getNom());
            prenomfiled.setText(utilisateurConnecte.getPrenom());
            emailfield.setText(utilisateurConnecte.getEmail());
            rolefield.setText(utilisateurConnecte.getRole());
            afficherRole(utilisateurConnecte);
            Profil.setOnAction(event -> afficherProfil());
            Accueil.setOnAction(event -> afficherAccueil(utilisateurConnecte));
            SeDeconnecter.setOnAction(event -> seDeconnecter());

        } else {
            nomfield.setText("Nom non disponible");
            prenomfiled.setText("Prénom non disponible");
            emailfield.setText("Email non disponible");
            rolefield.setText("Rôle non disponible");
            roleLabel.setText("Rôle : Non disponible");

        }

    }

    private void afficherRole(Utilisateur utilisateur) {
        String contenu = "";
        String role = utilisateur.getRole();

        if ("professeur".equals(role)) {
            Professeur professeur = new Professeur(utilisateur);
            contenu = professeur.afficher();
        } else if ("etudiant".equals(role)) {
            Etudiant etudiant = new Etudiant(utilisateur);
            contenu = etudiant.afficher();
        } else {
            contenu = "Rôle non reconnu";
        }
        roleLabel.setText(contenu);
    }

    @FXML
    private void handleModifierMotDePasseAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/modifiermotdepasse.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) bouttonmodifier.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Modifier Mot de Passe");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page de modification de mot de passe.");
        }
    }

    public void afficherProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/Profil.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) menu.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {

        }
    }

    public void afficherAccueil(Utilisateur u) {
        String role = u.getRole();
        try {

            String fxmlPath;
            if ("professeur".equals(u.getRole())) {
                fxmlPath = "/com/projetjava/view/pages/ProfAccueil.fxml";
            } else {
                fxmlPath = "/com/projetjava/view/pages/EtudiantAccueil.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) menu.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {

        }
    }

    public void seDeconnecter() {
        try {

            Session.getInstance().setUtilisateurConnecte(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/LoginPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) menu.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {

        }
    }

}
