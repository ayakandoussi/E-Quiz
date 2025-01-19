package com.projetjava.controller;

import com.projetjava.domain.Etudiant;
import com.projetjava.domain.Resultat;
import com.projetjava.domain.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import com.projetjava.domain.Session;
import com.projetjava.model.dao.impl.ResultatDao;
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
    private MenuItem Profil;
    @FXML
    private MenuItem SeDeconnecter;
    @FXML
    private MenuItem Accueil;
    private double score;

    @FXML
    private void initialize() {

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

    private void afficherRole(Utilisateur utilisateur) {
        String contenu = "";
        if (utilisateur instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) utilisateur;
            contenu = etudiant.afficher();
        }

        rolelabel.setText(contenu);
    }

    public void afficherResultat(double score, int idQuiz) {
        this.score = score;
        System.out.println("Score reçu : " + score);
        scoreLabel.setText(String.format("%.0f", score));
        afficheResume();
        enregistrerResultat(score, idQuiz);
    }

    public void enregistrerResultat(double score, int idQuiz) {

        Utilisateur utilisateurConnecte = Session.getInstance().getUtilisateurConnecte();

        if (utilisateurConnecte instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) utilisateurConnecte;
            ResultatDao resultatDao = new ResultatDao();
            Resultat resultat= new Resultat();
            System.out.println("id"+ idQuiz);
            resultat.setIdQuizzes(idQuiz);
            resultat.setEtudiant(etudiant);
            resultat.setScore(score);
            resultatDao.add(resultat);
            System.out.println("Résultat enregistré dans la base de données.");
        } else {
            System.out.println("L'utilisateur connecté n'est pas un étudiant.");
        }

    }

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
