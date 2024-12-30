package com.projetjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("LoginPage"), 800, 500);
        scene = new Scene(loadFXML("QuizPage"), 640, 480);
        scene = new Scene(loadFXML("PagePrincipaleEtudiant"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
<<<<<<< HEAD
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/" +fxml + ".fxml"));
=======
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/projetjava/view/pages/LoginPage.fxml"));
>>>>>>> origin/feature/GestionDesQuiz
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
