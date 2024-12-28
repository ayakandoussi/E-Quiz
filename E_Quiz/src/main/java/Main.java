/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Bouchama
 */
 // Assurez-vous que le package correspond à votre structure

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/QuizPage.fxml"));
        AnchorPane root = loader.load();

        // Créer la scène et définir le titre de la fenêtre
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("E-Quiz");

        // Afficher la fenêtre
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Lance l'application JavaFX
    }
}

