package com.projetjava.controller;
import com.projetjava.domain.Etudiant;
import com.projetjava.domain.Professeur;
import com.projetjava.domain.Question;
import com.projetjava.domain.Quiz;
import com.projetjava.domain.Session;
import com.projetjava.domain.Utilisateur;
import com.projetjava.model.dao.impl.QuestionDAO;
import com.projetjava.model.dao.impl.QuizDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


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
    private  TextArea description;
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

    private Quiz quizEnCours; // Stocker le quiz ajouté
    private QuizDao quizDao;
    private QuestionDAO questionDao;
    private int nombreQuestionsAjoutees = 0; // Compteur pour les questions ajoutées
    private static final int NOMBRE_MAX_QUESTIONS = 20; // Nombre maximum de questions
    
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
        // Récupérer l'utilisateur connecté depuis la session
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
        questionsuivante.setOnAction(event -> ajouterQuestion());
   
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

        quizDao.add(quizEnCours); // ID automatiquement récupéré ici

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
    private void ajouterQuestion() {
        // Vérifier si le nombre maximum de questions est atteint
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
        nombreQuestionsAjoutees++; // Incrémenter le compteur des questions ajoutées

        // Vérifier si les 20 questions sont atteintes après cet ajout
        if (nombreQuestionsAjoutees == NOMBRE_MAX_QUESTIONS) {
            afficherAlerte("Succès", "Vous avez ajouté les 20 questions requises. Quiz terminé.", Alert.AlertType.INFORMATION);
        } else {
            afficherAlerte("Succès", "La question a été ajoutée. (" + nombreQuestionsAjoutees + "/20)", Alert.AlertType.INFORMATION);
        }

        // Réinitialiser les champs pour la question suivante
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
        alert.showAndWait();
    }
}
