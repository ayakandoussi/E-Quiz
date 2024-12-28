/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projetjava.controller;

/**
 *
 * @author Bouchama
 */

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class QuestionController {

    @FXML
    private TextField questionField;

    @FXML
    private CheckBox choix1, choix2, choix3, choix4;

    @FXML
    private Button validerButton, suivantButton;
    
    // Définir la bonne réponse pour cette question
    private final String bonneReponse = "void"; // La bonne réponse pour cette question


    // Méthode pour valider la réponse
    @FXML
    private void validerReponse() {
        String reponseSelectionnee = null;

        // Vérifier les choix sélectionnés
        if (choix1.isSelected()) {
            reponseSelectionnee = choix1.getText(); // "void"
        } else if (choix2.isSelected()) {
            reponseSelectionnee = choix2.getText(); // "int"
        } else if (choix3.isSelected()) {
            reponseSelectionnee = choix3.getText(); // "String[]"
        } else if (choix4.isSelected()) {
            reponseSelectionnee = choix4.getText(); // "boolean"
        }

        // Comparer la réponse sélectionnée avec la bonne réponse
        if (reponseSelectionnee != null) {
            if (reponseSelectionnee.equals(bonneReponse)) {
                // Afficher un message de succès si la réponse est correcte
                showMessage("Correct !", "Votre réponse est correcte.");
            } else {
                // Afficher un message d'erreur si la réponse est incorrecte
                showMessage("Incorrect", "La réponse correcte est : " + bonneReponse);
            }
        } else {
            // Si aucune réponse n'est sélectionnée
            showMessage("Erreur", "Veuillez sélectionner une réponse.");
        }
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
        // Logique pour charger la prochaine question
        System.out.println("Passer à la question suivante");
        
        // Par exemple, changer le texte de la question
        questionField.setText("Question 2: Quel est le type de retour de la méthode main dans une application JavaFX ?");
        
        // Réinitialiser les choix (si nécessaire)
        choix1.setSelected(false);
        choix2.setSelected(false);
        choix3.setSelected(false);
        choix4.setSelected(false);
    }

    // Initialisation (si nécessaire)
    @FXML
    private void initialize() {
        // Vous pouvez mettre ici des actions à effectuer au démarrage de la page, comme réinitialiser les cases à cocher.
        choix1.setSelected(false);
        choix2.setSelected(false);
        choix3.setSelected(false);
        choix4.setSelected(false);
    }
}
