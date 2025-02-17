package com.projetjava.controller;

import com.projetjava.domain.Etudiant;
import com.projetjava.domain.Professeur;
import com.projetjava.domain.Question;
import com.projetjava.domain.Quiz;
import com.projetjava.domain.Session;
import com.projetjava.domain.Utilisateur;
import com.projetjava.exceptions.BonChoixException;
import com.projetjava.model.dao.impl.QuestionDAO;
import com.projetjava.model.dao.impl.QuizDao;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AjoutQuizController {

    @FXML
    private AnchorPane Ajoutquiz;
    @FXML
    private MenuItem Accueil;
    @FXML
    private MenuItem Profil;
    @FXML
    private MenuItem SeDeconnecter;
    @FXML
    private MenuButton menu;
    @FXML
    private Label rolelabel;
    @FXML
    private TextArea description;
    @FXML
    private Button AjoutQuestion;
    @FXML
    private TextField titre;
    @FXML
    private Pane formulairequestion;
    @FXML
    private TextField questionField, choix1Field, choix2Field, choix3Field, choix4Field, reponse;
    @FXML
    private Button questionsuivante;

    private Quiz quizEnCours; 
    private QuizDao quizDao;
    private QuestionDAO questionDao;
    private int nombreQuestionsAjoutees = 0; 
    private static final int NOMBRE_MAX_QUESTIONS = 20; 

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

    public void initialize() {
        Utilisateur utilisateurConnecte = Session.getInstance().getUtilisateurConnecte();

        if (utilisateurConnecte != null) {
            afficherRole(utilisateurConnecte);
        } else {
            System.out.println("Aucun utilisateur connecté.");
        }
        quizDao = new QuizDao();
        questionDao = new QuestionDAO();
        quizEnCours = new Quiz();
        AjoutQuestion.setOnAction(event -> ajouterQuiz());
        questionsuivante.setOnAction(event -> {
            try {
                ajouterQuestion();
            } catch (BonChoixException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de l'ajout de la question");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });
        Profil.setOnAction(event -> afficherProfil());
        Accueil.setOnAction(event -> afficherAccueil());
        SeDeconnecter.setOnAction(event -> seDeconnecter());

    }

    @FXML
    private void ajouterQuiz() {
        String titreQuiz = titre.getText().trim();
        String descriptionQuiz = description.getText().trim();

        if (titreQuiz.isEmpty() || descriptionQuiz.isEmpty()) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        Utilisateur utilisateurConnecte = Session.getInstance().getUtilisateurConnecte();
        quizEnCours.setTitre(titreQuiz);
        quizEnCours.setDescription(descriptionQuiz);
        quizEnCours.setIdEnseignant(utilisateurConnecte.getId());

        quizDao.add(quizEnCours); 

        if (quizEnCours.getIdQuiz() <= 0) {
            afficherAlerte("Erreur", "Impossible de créer le quiz. Veuillez réessayer.", Alert.AlertType.ERROR);
            return;
        }

        formulairequestion.setVisible(true);
        Ajoutquiz.setVisible(false);

        titre.clear();
        description.clear();
    }

    @FXML
    private void ajouterQuestion() throws BonChoixException {
        if (nombreQuestionsAjoutees >= NOMBRE_MAX_QUESTIONS) {
            afficherAlerte("Information", "Vous avez déjà ajouté les 20 questions requises.", Alert.AlertType.INFORMATION);
            return;
        }

        if (quizEnCours == null || quizEnCours.getIdQuiz() <= 0) {
            afficherAlerte("Erreur", "Quiz non valide. Veuillez ajouter un quiz d'abord.", Alert.AlertType.ERROR);
            return;
        }

        String questionTexte = questionField.getText().trim();
        String choix1 = choix1Field.getText().trim();
        String choix2 = choix2Field.getText().trim();
        String choix3 = choix3Field.getText().trim();
        String choix4 = choix4Field.getText().trim();
        String reponseCorrecte = reponse.getText().trim();

        if (questionTexte.isEmpty() || choix1.isEmpty() || choix2.isEmpty() || choix3.isEmpty() || choix4.isEmpty() || reponseCorrecte.isEmpty()) {
            afficherAlerte("Erreur", "Tous les champs de la question doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        Question question = new Question();
        question.setEnonce(questionTexte);
        question.setChoix1(choix1);
        question.setChoix2(choix2);
        question.setChoix3(choix3);
        question.setChoix4(choix4);
        question.setBonneReponse(reponseCorrecte);
        question.setIdQuiz(quizEnCours.getIdQuiz());

        questionDao.add(question);
        nombreQuestionsAjoutees++; 

        if (nombreQuestionsAjoutees == NOMBRE_MAX_QUESTIONS) {
            afficherAlerte("Succès", "Vous avez ajouté les 20 questions requises. Quiz terminé.", Alert.AlertType.INFORMATION);
        } else {
            afficherAlerte("Succès", "La question a été ajoutée. (" + nombreQuestionsAjoutees + "/20)", Alert.AlertType.INFORMATION);
        }

        questionField.clear();
        choix1Field.clear();
        choix2Field.clear();
        choix3Field.clear();
        choix4Field.clear();
        reponse.clear();
    }

    private void afficherAlerte(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setOnHidden(event -> {

            if (message.contains("Quiz terminé")) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/ProfAccueil.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) menu.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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
            e.printStackTrace();
        }
    }

    public void afficherAccueil() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/ProfAccueil.fxml"));
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
