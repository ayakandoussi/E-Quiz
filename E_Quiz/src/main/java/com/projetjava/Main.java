






package com.projetjava;  

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);  // Démarre l'application JavaFX
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charge l'interface graphique FXML pour la fenêtre principale (QuizPage.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/QuizPage.fxml"));

            StackPane root = loader.load();
            
            // Crée une scène et la définit dans la fenêtre principale
            Scene scene = new Scene(root, 800, 600);  // Taille de la fenêtre
            primaryStage.setTitle("E-Quiz Application");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
