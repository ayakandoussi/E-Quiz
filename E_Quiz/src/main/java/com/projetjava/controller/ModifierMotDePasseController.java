package com.projetjava.controller;

import com.projetjava.domain.Etudiant;
import com.projetjava.domain.Professeur;
import com.projetjava.domain.Session;
import com.projetjava.model.dao.impl.UtilisateurDao;
import com.projetjava.domain.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

public class ModifierMotDePasseController {

    @FXML
    private PasswordField oldField;

    @FXML
    private PasswordField newField;

    @FXML
    private Label roleLabel;

    @FXML
    private MenuButton menu;

    @FXML
    private MenuItem Accueil, Profil, SeDeconnecter;

    @FXML
    private Button addQuizButton;

    private Utilisateur utilisateur;
    private UtilisateurDao utilisateurDAO;

    public ModifierMotDePasseController() {
        utilisateurDAO = new UtilisateurDao(); // Initialisation du DAO
    }

    @FXML
    private void initialize() {
        utilisateur = Session.getInstance().getUtilisateurConnecte(); // Récupérer l'utilisateur connecté
        if (utilisateur != null) {
            roleLabel.setText(utilisateur.getRole()+ ":" + utilisateur.getNom() + " " + utilisateur.getPrenom());
        } else {
            afficherErreur("Utilisateur non connecté. Veuillez vous reconnecter.");
        }
    }
    
   


    /**
     * Vérifie si le mot de passe saisi correspond au mot de passe actuel de l'utilisateur.
     */
    public boolean verifierMotDePasse(String motDePasseSaisi, Utilisateur utilisateur) {
        return BCrypt.checkpw(motDePasseSaisi, utilisateur.getMotDePasse());
    }

    /**
     * Gestion de la modification du mot de passe.
     */
    @FXML
    private void handleEnregistrerAction(ActionEvent event) {
        String ancienMotDePasse = oldField.getText();
        String nouveauMotDePasse = newField.getText();

        if (ancienMotDePasse.isEmpty() || nouveauMotDePasse.isEmpty()) {
            afficherErreur("Les champs ne peuvent pas être vides.");
            return;
        }

        if (!verifierMotDePasse(ancienMotDePasse, utilisateur)) {
            afficherErreur("Ancien mot de passe incorrect.");
            return;
        }

        if (ancienMotDePasse.equals(nouveauMotDePasse)) {
            afficherErreur("Le nouveau mot de passe doit être différent de l'ancien.");
            return;
        }

        // Hacher le nouveau mot de passe et mettre à jour l'utilisateur
        
        utilisateur.setMotDePasse(newField.getText());

        try {
            utilisateurDAO.update(utilisateur); // Sauvegarder dans la base de données
            afficherMessage("Mot de passe modifié avec succès.");
        } catch (Exception e) {
            afficherErreur("Une erreur est survenue lors de la mise à jour du mot de passe.");
            e.printStackTrace();
        }
    }

    /**
     * Affiche un message d'information.
     */
    private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Affiche un message d'erreur.
     */
    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Navigation vers la page de profil.
     */
    public void afficherProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/Profil.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) roleLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            afficherErreur("Erreur lors du chargement de la page de profil.");
            e.printStackTrace();
        }
    }

    /**
     * Navigation vers l'accueil.
     */
    @FXML
    private void handleAccueilAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/Accueil.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) roleLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            afficherErreur("Erreur lors du chargement de la page d'accueil.");
            e.printStackTrace();
        }
    }

    /**
     * Déconnexion de l'utilisateur.
     */
    @FXML
    private void handleSeDeconnecterAction(ActionEvent event) {
        Session.getInstance().setUtilisateurConnecte(null); // Réinitialiser la session
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/LoginPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) roleLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            afficherErreur("Erreur lors de la déconnexion.");
            e.printStackTrace();
        }
    }
}
