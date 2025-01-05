package com.projetjava.controller;

import com.projetjava.domain.Session;
import com.projetjava.domain.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class NavbarController {

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

    @FXML
    public void initialize() {
        // Récupérer l'utilisateur connecté depuis la session
        Utilisateur utilisateurConnecte = Session.getInstance().getUtilisateurConnecte();

        if (utilisateurConnecte != null) {
            afficherRole(utilisateurConnecte);
        } else {
            System.out.println("Aucun utilisateur connecté.");
        }
        Profil.setOnAction(event -> afficherProfil());
    }
    
    // Méthode pour afficher la page Profil
    public void afficherProfil() {
        try {
            // Charger le fichier FXML de la page de profil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/Profil.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène et l'afficher dans la même fenêtre
            Stage stage = (Stage) menu.getScene().getWindow();  // Utiliser le même stage
            stage.setTitle("Page Profil");
            stage.setScene(new Scene(root));

            // Afficher la nouvelle fenêtre
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    public void afficherRole(Utilisateur utilisateur) {
        String contenu;

        if (utilisateur.getRole().equalsIgnoreCase("professeur")) {
            contenu = "Professeur";
        } else {
            contenu = "Etudiant";
        }

        rolelabel.setText(contenu);
    }
    
    

}
