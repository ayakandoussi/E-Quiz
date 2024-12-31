package com.projetjava.controller;

import com.projetjava.domain.Etudiant;
import com.projetjava.domain.Professeur;
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
        String contenu;
        if (utilisateur.getRole().equalsIgnoreCase("professeur")) {
            Professeur professeur = new Professeur(utilisateur);

            contenu = professeur.afficher();
        } else {
            Etudiant etudiant = new Etudiant(utilisateur);

            contenu = etudiant.afficher();
        }

        rolelabel.setText(contenu);
    }
}
