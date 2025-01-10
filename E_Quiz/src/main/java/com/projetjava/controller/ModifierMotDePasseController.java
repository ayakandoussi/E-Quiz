package com.projetjava.controller;

import com.projetjava.domain.Etudiant;
import com.projetjava.domain.Professeur;
import com.projetjava.domain.Session;
import com.projetjava.model.dao.impl.UtilisateurDao;
import com.projetjava.domain.Utilisateur;
import com.projetjava.exceptions.MotDePasseException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
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
            roleLabel.setText(utilisateur.getRole() + ":" + utilisateur.getNom() + " " + utilisateur.getPrenom());
            Profil.setOnAction(event -> afficherProfil());
            Accueil.setOnAction(event -> afficherAccueil(utilisateur));
            SeDeconnecter.setOnAction(event -> seDeconnecter());
        } else {
            afficherErreur("Utilisateur non connecté. Veuillez vous reconnecter.");
        }
    }

    /**
     * Vérifie si le mot de passe saisi correspond au mot de passe actuel de
     * l'utilisateur.
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
        try {
            // Hacher le nouveau mot de passe et mettre à jour l'utilisateur
            utilisateur.setMotDePasse(newField.getText());
            utilisateurDAO.update(utilisateur); // Sauvegarder dans la base de données
            afficherMessage("Mot de passe modifié avec succès.");
        } catch (MotDePasseException ex) {
            // Si une exception est levée, afficher une alerte personnalisée
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur Mot de Passe");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());  // Le message de l'exception sera affiché ici
            alert.showAndWait();
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
