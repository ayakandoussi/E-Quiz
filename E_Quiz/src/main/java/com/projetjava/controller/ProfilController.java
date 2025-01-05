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

    // Champs pour les informations de profil
    @FXML
    private TextField nomfield;       // Champ nom
    @FXML
    private TextField prenomfiled;    // Champ prénom
    @FXML
    private TextField emailfield;     // Champ email
    @FXML
    private TextField rolefield;      // Champ rôle
    @FXML
    private Button bouttonmodifier;   // Bouton modifier mot de passe
    @FXML
    private Label roleLabel;          // Label pour afficher le rôle, nom et prénom

    // MenuButton et MenuItems
    @FXML
    private MenuButton menu;          // MenuButton pour afficher le menu
    @FXML
    private MenuItem Accueil;         // Item "Accueil"
    @FXML
    private MenuItem Profil;          // Item "Profil"
    @FXML
    private MenuItem SeDeconnecter;   // Item "Se déconnecter"

    /**
     * Méthode d'initialisation appelée après le chargement du fichier FXML.
     */
    @FXML
    public void initialize() {
        // Gestion des informations de profil
        Utilisateur utilisateurConnecte = Session.getInstance().getUtilisateurConnecte();

        if (utilisateurConnecte != null) {
            nomfield.setText(utilisateurConnecte.getNom());
            prenomfiled.setText(utilisateurConnecte.getPrenom());
            emailfield.setText(utilisateurConnecte.getEmail());
            rolefield.setText(utilisateurConnecte.getRole());
            afficherRole(utilisateurConnecte);
            Profil.setOnAction(event -> afficherProfil());
            Accueil.setOnAction(event -> afficherAccueil(utilisateurConnecte));
            
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
            // Créez un objet Professeur et récupérez son affichage
            Professeur professeur = new Professeur(utilisateur);
            contenu = professeur.afficher();  
        } else if ("etudiant".equals(role)) {
            // Créez un objet Etudiant et récupérez son affichage
            Etudiant etudiant = new Etudiant(utilisateur);
            contenu = etudiant.afficher();  // Affiche les informations de l'étudiant
        } else {
            // Cas par défaut si le rôle n'est ni professeur ni étudiant
            contenu = "Rôle non reconnu";
        }

        // Met à jour le texte du roleLabel avec le contenu approprié
        roleLabel.setText(contenu);
    }

    @FXML
    private void handleModifierMotDePasseAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/modifiermotdepasse.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modifier Mot de Passe");
            stage.show();
            ((Stage) bouttonmodifier.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page de modification de mot de passe.");
        }
    }

    public void afficherProfil() {
        try {
            // Charger le fichier FXML de la page de profil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/Profil.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène et l'afficher dans une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setTitle("Page Profil");
            stage.setScene(new Scene(root));

            // Afficher la nouvelle fenêtre
            stage.show();
        } catch (Exception e) {
           
        }
    }

    public void afficherAccueil(Utilisateur u) {
        String role = u.getRole();
        try {
            if ("professeur".equals(role)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/ProfAccueil.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Page Aceuil");
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/EtudiantAccueil.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Page Aceuil");
                stage.setScene(new Scene(root));
                stage.show();
            }

        } catch (Exception e) {

        }
    }

}
