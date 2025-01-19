package com.projetjava.model.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertQuestions {

    public static void insertQuestions() {
        String url = "jdbc:mysql://localhost:3306/e_quiz";
        String username = "root";
        String password = "ABC@def123";

        String sql = "INSERT INTO question (enonce, choix1, choix2, choix3, choix4, bonneReponse, idQuiz) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Object[][] questions = {
            {"Quelle est la méthode principale d'une application Java ?", "start()", "main()", "run()", "execute()", "choix2"},
            {"Que signifie JVM ?", "Java Variable Machine", "Java Virtual Machine", "Java Verification Machine", "Java Version Manager", "choix2"},
            {"Quel mot-clé est utilisé pour hériter d'une classe ?", "inherit", "extends", "implements", "super", "choix2"},
            {"Comment déclarer une constante en Java ?", "final", "const", "constant", "immutable", "choix1"},
            {"Que fait la méthode equals() ?", "Compare des objets", "Concatène des chaînes", "Copie un objet", "Efface un objet", "choix1"},
            {"Que signifie JDK ?", "Java Development Kit", "Java Debugging Kit", "Java Data Kit", "Java Deployment Kit", "choix1"},
            {"Quelle classe est utilisée pour manipuler des chaînes ?", "String", "StringBuilder", "CharSequence", "StringBuffer", "choix1"},
            {"Quelle est la portée par défaut d'une variable dans une classe ?", "private", "public", "protected", "package-private", "choix4"},
            {"Comment capturer une exception en Java ?", "try-catch", "throw-catch", "handle-exception", "catch-throw", "choix1"},
            {"Quelle méthode est utilisée pour démarrer un thread ?", "run()", "start()", "begin()", "init()", "choix2"},
            {"Quel est le type par défaut d'un nombre décimal ?", "float", "int", "double", "long", "choix3"},
            {"Comment accéder à un membre statique ?", "new Instance", "super", "className.member", "this.member", "choix3"},
            {"Quel layout est par défaut dans un JavaFX Stage ?", "FlowPane", "StackPane", "BorderPane", "AnchorPane", "choix2"},
            {"Quelle méthode est utilisée pour charger une scène dans JavaFX ?", "loadScene()", "setScene()", "showScene()", "initializeScene()", "choix2"},
            {"Quelle est l'extension des fichiers CSS utilisés avec JavaFX ?", ".javafx", ".fxcss", ".css", ".style", "choix3"},
            {"Comment ajouter un événement à un bouton JavaFX ?", "addEventHandler()", "setOnAction()", "setEvent()", "handleEvent()", "choix2"},
            {"Quelle classe est utilisée pour afficher des boîtes de dialogue en JavaFX ?", "AlertBox", "Dialog", "Popup", "Alert", "choix4"},
            {"Quel package est utilisé pour les interfaces JavaFX ?", "javafx.scene", "javafx.stage", "javafx.graphics", "javafx.application", "choix1"},
            {"Quelle méthode permet de définir un texte dans un Label JavaFX ?", "setText()", "updateText()", "assignText()", "text()", "choix1"},
            {"Quelle classe est utilisée pour créer un formulaire en JavaFX ?", "FormPane", "VBox", "FormLayout", "GridPane", "choix4"}
        };

        int idQuiz = 3;

        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Object[] question : questions) {
                String enonce = (String) question[0];
                String choix1 = (String) question[1];
                String choix2 = (String) question[2];
                String choix3 = (String) question[3];
                String choix4 = (String) question[4];
                String bonneReponse = (String) question[5];
                statement.setString(1, enonce);
                statement.setString(2, choix1);
                statement.setString(3, choix2);
                statement.setString(4, choix3);
                statement.setString(5, choix4);
                statement.setString(6, bonneReponse);
                statement.setInt(7, idQuiz);

                statement.executeUpdate();
            }

            System.out.println("20 questions ont été insérées avec succès pour le quiz ID " + idQuiz + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        insertQuestions();
    }
}
