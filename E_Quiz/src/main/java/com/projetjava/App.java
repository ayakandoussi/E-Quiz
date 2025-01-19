package com.projetjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("/com/projetjava/view/pages/LoginPage"), 600, 400);
        //scene = new Scene(loadFXML("/com/projetjava/view/pages/AjoutQuiz"), 600, 400);
        //scene = new Scene(loadFXML("QuizPage"), 640, 480);//
        //scene = new Scene(loadFXML("EtudiantAccueil"), 640, 480);
        Image logo = new Image(getClass().getResourceAsStream("/com/projetjava/view/images/logo.png"));
        stage.getIcons().add(logo); 
        stage.setScene(scene);
        stage.setTitle("E-Quiz");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/projetjava/view/pages/AjoutQuiz.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/projetjava/view/pages/LoginPage.fxml"));
        // FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/projetjava/view/pages/EtudiantAccueil.fxml"));

        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
