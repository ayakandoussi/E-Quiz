package com.projetjava.controller;

import com.projetjava.domain.Etudiant;
import com.projetjava.domain.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import com.projetjava.domain.Session;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class ResultatPageController {

    @FXML
    private MenuButton menu;

    @FXML
    private Label rolelabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label remarqueLabel;
    @FXML
    private MenuItem Profil;  // Bouton pour afficher le profil
    @FXML
    private MenuItem SeDeconnecter;
    @FXML
    private MenuItem Accueil;
    private double score;

    // Méthode d'initialisation
    @FXML
    private void initialize() {

        // Récupérer l'utilisateur connecté via la session
        Utilisateur utilisateurConnecte = Session.getInstance().getUtilisateurConnecte();

        if (utilisateurConnecte != null) {
            afficherRole(utilisateurConnecte);
            Profil.setOnAction(event -> afficherProfil());
            Accueil.setOnAction(event -> afficherAccueil(utilisateurConnecte));
            SeDeconnecter.setOnAction(event -> seDeconnecter());
        } else {
            System.out.println("Aucun utilisateur connecté.");
        }

    }

    // Méthode pour afficher le rôle de l'utilisateur
    private void afficherRole(Utilisateur utilisateur) {
        String contenu = "";

        // Affichage spécifique pour l'étudiant
        if (utilisateur instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) utilisateur;
            contenu = etudiant.afficher(); // Appeler la méthode qui retourne le rôle de l'étudiant
        }

        // Afficher le rôle dans le Label
        rolelabel.setText(contenu);
    }

    public void afficherResultat(double score) {
        this.score = score;
        System.out.println("Score reçu : " + score);
        // Mettre à jour le label du score
        scoreLabel.setText(String.format("%.0f", score));  // Afficher le score sans décimale

        // Appeler la méthode pour afficher la remarque en fonction du score
        afficheResume();
    }

    // Méthode pour afficher la remarque en fonction du score
    public void afficheResume() {
        if (score >= 0 && score <= 5) {
            remarqueLabel.setText("Null!");
        } else if (score > 5 && score <= 10) {
            remarqueLabel.setText("Passable!");
        } else if (score > 10 && score <= 15) {
            remarqueLabel.setText("Bien!");
        } else if (score > 15 && score <= 19) {
            remarqueLabel.setText("Très Bien!");
        } else {
            remarqueLabel.setText("Excellent!");
        }
    }

    public void afficherProfil() {
        try {
            // Charger le fichier FXML de la page de profil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/Profil.fxml"));
            Parent root = loader.load();

            // Récupérer la fenêtre actuelle
            Stage stage = (Stage) menu.getScene().getWindow();

            // Remplacer la scène existante
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

            // Récupérer la fenêtre actuelle à partir de n'importe quel élément de la scène
            // Ici on utilise le MenuButton 'menu'
            Stage stage = (Stage) menu.getScene().getWindow();

            // Remplacer la scène existante
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {

        }
    }

    public void seDeconnecter() {
        try {

            // Réinitialiser la session avant de rediriger
            Session.getInstance().setUtilisateurConnecte(null);
            // Charger le fichier FXML de la page de profil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/LoginPage.fxml"));
            Parent root = loader.load();

            // Récupérer la fenêtre actuelle
            Stage stage = (Stage) menu.getScene().getWindow();

            // Remplacer la scène existante
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {

        }
    }

}
