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
    private Button addQuizButton, bouttonenregistrer;

    private Utilisateur utilisateur;
    private UtilisateurDao utilisateurDAO;

    public ModifierMotDePasseController() {
        utilisateurDAO = new UtilisateurDao();
    }

    @FXML
    private void initialize() {
        utilisateur = Session.getInstance().getUtilisateurConnecte();
        if (utilisateur != null) {
            afficherRole(utilisateur);            Profil.setOnAction(event -> afficherProfil());
            Accueil.setOnAction(event -> afficherAccueil(utilisateur));
            SeDeconnecter.setOnAction(event -> seDeconnecter());
            bouttonenregistrer.setOnAction(this::handleEnregistrerAction);

        } else {
            afficherErreur("Utilisateur non connecté. Veuillez vous reconnecter.");
        }
    }

    public boolean verifierMotDePasse(String motDePasseSaisi, Utilisateur utilisateur) {
        return BCrypt.checkpw(motDePasseSaisi, utilisateur.getMotDePasse());
    }

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
            System.out.println(" avant: " + utilisateur.getMotDePasse());
            utilisateur.setMotDePasse(newField.getText());
            System.out.println(" apres: " + utilisateur.getMotDePasse());
            utilisateurDAO.update(utilisateur);
            afficherMessage("Mot de passe modifié avec succès.");
        } catch (MotDePasseException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur Mot de Passe");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            afficherErreur("Une erreur est survenue lors de la mise à jour du mot de passe.");
            e.printStackTrace();
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
    

    private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
