package com.projetjava.controller;


import com.projetjava.domain.Session;
import com.projetjava.domain.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

public class NavbarController {

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

    private void afficherRole(Utilisateur utilisateur) {
        // Utilise la méthode afficher() pour obtenir les informations de l'utilisateur (qui inclut déjà le rôle)
        String contenuAffichage;
        contenuAffichage = utilisateur.afficher();

        // Affiche le contenu dans le rolelabel
        rolelabel.setText(contenuAffichage);
    }
}