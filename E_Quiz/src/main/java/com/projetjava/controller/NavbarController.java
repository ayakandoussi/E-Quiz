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
        
    }
    
    // Méthode pour afficher la page Profil
 

    
    
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
