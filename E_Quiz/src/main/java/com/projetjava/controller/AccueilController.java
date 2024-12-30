package com.projetjava.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {

    @FXML
    private Label roleLabel;

    @FXML
    private VBox profList;

    @FXML
    private GridPane quizGrid;

    @FXML
    private ScrollPane profScrollPane;

    @FXML
    private ScrollPane quizScrollPane; 


    private List<Professor> professors;
    private List<Quiz> quizzes;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Exemple d'initialisation : définir le label du rôle et le nom/prénom de l'utilisateur
        User currentUser = getCurrentUser(); // Récupérer l'utilisateur actuel
        roleLabel.setText(currentUser.getRole() + " : " + currentUser.getFirstName() + " " + currentUser.getLastName());

        // Charger les professeurs
        loadProfessors();

        // Charger les quiz pour le premier professeur (exemple)
        if (!professors.isEmpty()) {
            loadQuizzesForProfessor(professors.get(0).getId());
        }
    }

    /**
     * Charge la liste des professeurs dans le VBox.
     */
    private void loadProfessors() {
        // Exemple dynamique : récupérer tous les professeurs
        professors = getProfessorsFromDatabase(); // Simuler la récupération depuis une base de données

        for (Professor professor : professors) {
            // Créer un bouton pour chaque professeur avec son nom et prénom
            Button profButton = new Button(professor.getFirstName() + " " + professor.getLastName());
            profButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            int profId = professor.getId(); // Pour utiliser dans le lambda
            profButton.setOnAction(event -> loadQuizzesForProfessor(profId));
            profList.getChildren().add(profButton);
        }

        // Si vous souhaitez que le VBox soit scrollable, vous pouvez ajouter le ScrollPane
        profScrollPane.setContent(profList);
    }

    /**
     * Charge les quiz associés à un professeur dans la grille.
     *
     * @param professorId L'identifiant du professeur.
     */
    private void loadQuizzesForProfessor(int professorId) {
        // Nettoyer la grille avant d'ajouter les quiz
        quizGrid.getChildren().clear();

        // Exemple dynamique : récupérer les quiz pour un professeur spécifique
        quizzes = getQuizzesForProfessor(professorId); // Simuler la récupération depuis une base de données

        for (int i = 0; i < quizzes.size(); i++) {
            Quiz quiz = quizzes.get(i);
            Label quizTitle = new Label(quiz.getTitle());
            quizTitle.setStyle("-fx-border-color: blue; -fx-alignment: center;");

            Label quizDescription = new Label(quiz.getDescription());
            quizDescription.setStyle("-fx-alignment: center;");

            Button quizButton = new Button("Passer");
            quizButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            int quizId = quiz.getId(); // Pour utilisation dans le lambda
            quizButton.setOnAction(event -> passQuiz(quizId));

            // Ajouter à la grille (3 colonnes)
            quizGrid.add(quizTitle, (i) % 3, (i) / 3);
            quizGrid.add(quizDescription, (i) % 3, (i) / 3 + 1);
            quizGrid.add(quizButton, (i) % 3, (i) / 3 + 2);
        }

        // Si vous souhaitez que la grille soit scrollable, vous pouvez ajouter le ScrollPane
        quizScrollPane.setContent(quizGrid);
    }

    /**
     * Action pour passer un quiz.
     *
     * @param quizId L'identifiant du quiz.
     */
    private void passQuiz(int quizId) {
        System.out.println("Passer le quiz : " + quizId);
        // Implémenter la logique pour passer le quiz (changer de page, afficher un formulaire, etc.)
    }

    /**
     * Méthodes pour le menu (Accueil, Profil, Déconnexion).
     */
    @FXML
    private void goToHome() {
        System.out.println("Naviguer vers l'accueil.");
        // Implémenter la navigation vers l'accueil
    }

    @FXML
    private void goToProfile() {
        System.out.println("Naviguer vers le profil.");
        // Implémenter la navigation vers le profil
    }

    @FXML
    private void logout() {
        System.out.println("Déconnexion.");
        // Implémenter la logique de déconnexion
    }

    // Méthodes simulant la récupération des données depuis une base de données

    private User getCurrentUser() {
        // Exemple d'utilisateur, il faut remplacer cela par une récupération réelle
        return new User("Etudiant", "John", "Doe");
    }

    private List<Professor> getProfessorsFromDatabase() {
        // Simuler des données pour les professeurs
        return List.of(
            new Professor(1, "Alice", "Martin"),
            new Professor(2, "Bob", "Dupont")
        );
    }

    private List<Quiz> getQuizzesForProfessor(int professorId) {
        // Simuler des quiz pour un professeur
        return List.of(
            new Quiz(1, "Quiz 1", "Description du quiz 1"),
            new Quiz(2, "Quiz 2", "Description du quiz 2")
        );
    }
}

// Classes simulées pour l'utilisateur, le professeur et le quiz

class User {
    private String role;
    private String firstName;
    private String lastName;

    public User(String role, String firstName, String lastName) {
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

class Professor {
    private int id;
    private String firstName;
    private String lastName;

    public Professor(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

class Quiz {
    private int id;
    private String title;
    private String description;

    public Quiz(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
