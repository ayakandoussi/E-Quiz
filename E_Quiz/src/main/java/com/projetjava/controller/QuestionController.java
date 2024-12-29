package com.projetjava.controller;

import com.projetjava.model.dao.impl.BDConnexion;
import com.projetjava.domain.Question; // Assurez-vous que cette classe existe et contient les informations des questions

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class QuestionController {
    
    private List<Question> questions; // Liste pour stocker les questions du quiz
    private int indexQuestionActuelle = 0; // Index de la question actuelle
    private int quizId; // ID du quiz actuel

    @FXML
    private TextField questionField;

    @FXML
    private CheckBox choix1, choix2, choix3, choix4;

    @FXML
    private Button validerButton, suivantButton;
    
    // Constructeur avec quizId
    public QuestionController(int quizId) {
        this.quizId = quizId;
        this.questions = new ArrayList<>();
        chargerQuestions(); // Charger les questions à partir de la base de données
    }
    
    // Méthode pour charger les questions depuis la base de données
    private void chargerQuestions() {
    String sql = "SELECT * FROM Question WHERE quiz_id = ?";

    try (Connection connection = BDConnexion.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, quizId); // Assigner le quiz_id à la requête
        ResultSet resultSet = statement.executeQuery();

        // Parcourir les résultats et ajouter les questions à la liste
        while (resultSet.next()) {
            int idQuestion = resultSet.getInt("idQuestion"); // ID de la question
            String enonce = resultSet.getString("enonce"); // Texte de la question
            String choix1 = resultSet.getString("choix1");
            String choix2 = resultSet.getString("choix2");
            String choix3 = resultSet.getString("choix3");
            String choix4 = resultSet.getString("choix4");
            String bonneReponse = resultSet.getString("bonne_reponse");
            int idQuiz = resultSet.getInt("quiz_id"); // ID du quiz auquel cette question appartient

            // Ajouter la question à la liste
            questions.add(new Question(idQuestion, enonce, choix1, choix2, choix3, choix4, bonneReponse, idQuiz));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showMessage("Erreur", "Erreur lors du chargement des questions.");
    }
}


    // Méthode pour valider la réponse
    @FXML
    private void validerReponse() {
        String reponseSelectionnee = null;
        boolean estCorrect = false;

        // Vérifier quel choix est sélectionné
        if (choix1.isSelected()) {
            deselectionnerAutresChoix(choix1);
            reponseSelectionnee = choix1.getText(); // La réponse choisie
        } else if (choix2.isSelected()) {
            deselectionnerAutresChoix(choix2);
            reponseSelectionnee = choix2.getText();
        } else if (choix3.isSelected()) {
            deselectionnerAutresChoix(choix3);
            reponseSelectionnee = choix3.getText();
        } else if (choix4.isSelected()) {
            deselectionnerAutresChoix(choix4);
            reponseSelectionnee = choix4.getText();
        }

        // Vérifier si une réponse a été sélectionnée
        if (reponseSelectionnee != null) {
            // Vérifier si la réponse est correcte
            estCorrect = reponseSelectionnee.equals(getQuestionActuelle().getBonneReponse());

            // Enregistrer la réponse dans la base de données
            enregistrerReponseDansBaseDeDonnees(reponseSelectionnee, estCorrect);

            // Passer à la question suivante
            questionSuivante();
        } else {
            // Si aucune réponse n'est sélectionnée
            showMessage("Erreur", "Veuillez sélectionner une réponse.");
        }
    }

    /**
     * Enregistre la réponse de l'étudiant dans la base de données.
     * 
     * @param reponseChoisie La réponse sélectionnée par l'étudiant.
     * @param estCorrect Indique si la réponse est correcte ou non.
     */
    private void enregistrerReponseDansBaseDeDonnees(String reponseChoisie, boolean estCorrect) {
        // Récupérer l'ID de l'étudiant (supposons que vous ayez un objet 'Utilisateur' ou un ID stocké dans la session)
        int etudiantId = getEtudiantId(); // Cette méthode doit renvoyer l'ID de l'étudiant connecté

        // Récupérer l'ID de la question (si vous avez un objet 'Question' ou un ID passé à la méthode)
        int questionId = getQuestionId(); // Cette méthode doit renvoyer l'ID de la question actuelle

        // Utiliser la classe BDConnexion pour obtenir la connexion
        try (Connection connection = BDConnexion.getConnection()) {  
            // Créer une requête SQL pour insérer la réponse dans la table ResultatEtudiant
            String sql = "INSERT INTO ResultatEtudiant (question_id, etudiant_id, reponse_choisie, est_correct) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Remplacer les paramètres de la requête
                statement.setInt(1, questionId);  // ID de la question
                statement.setInt(2, etudiantId);  // ID de l'étudiant
                statement.setString(3, reponseChoisie);  // La réponse choisie
                statement.setBoolean(4, estCorrect);  // Vérifier si la réponse est correcte

                // Exécuter la requête
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Erreur", "Erreur lors de l'enregistrement de la réponse.");
        }
    }

    /**
     * Désélectionne tous les autres choix sauf celui qui est actuellement sélectionné.
     * 
     * @param choixSelectionne Le choix actuellement sélectionné.
     */
    private void deselectionnerAutresChoix(CheckBox choixSelectionne) {
        if (choixSelectionne != choix1) choix1.setSelected(false);
        if (choixSelectionne != choix2) choix2.setSelected(false);
        if (choixSelectionne != choix3) choix3.setSelected(false);
        if (choixSelectionne != choix4) choix4.setSelected(false);
    }

    // Méthode pour afficher un message d'alerte
    private void showMessage(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour passer à la question suivante
    @FXML
    private void questionSuivante() {
        // Vérifier si l'index est valide
        if (indexQuestionActuelle < questions.size()) {
            // Récupérer la question actuelle
            Question questionActuelle = questions.get(indexQuestionActuelle);

            // Mettre à jour le texte de la question
            questionField.setText(questionActuelle.getEnonce());

            // Mettre à jour les choix
            choix1.setText(questionActuelle.getChoix1());
            choix2.setText(questionActuelle.getChoix2());
            choix3.setText(questionActuelle.getChoix3());
            choix4.setText(questionActuelle.getChoix4());

            // Réinitialiser les cases à cocher
            choix1.setSelected(false);
            choix2.setSelected(false);
            choix3.setSelected(false);
            choix4.setSelected(false);

            // Incrémenter l'index pour la prochaine question
            indexQuestionActuelle++;
        } else {
            // Si nous avons atteint la fin des questions, afficher un message ou un score
            showMessage("Fin du quiz", "Vous avez terminé le quiz !");
            // Vous pouvez également désactiver le bouton suivant
            suivantButton.setDisable(true);
        }
    }

    // Méthodes fictives pour récupérer l'ID de l'étudiant et de la question
    private int getEtudiantId() {
        // Récupérer l'ID de l'étudiant à partir de la session ou de l'utilisateur connecté
        return 1; // Par exemple
    }

    private int getQuestionId() {
        // Retourner l'ID de la question actuelle
        return questions.get(indexQuestionActuelle).getIdQuestion(); // Exemple basé sur l'objet Question
    }

    // Méthode pour récupérer la question actuelle
    private Question getQuestionActuelle() {
        return questions.get(indexQuestionActuelle);
    }
}
