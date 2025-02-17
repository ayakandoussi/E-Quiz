package com.projetjava.controller;

import com.projetjava.domain.Session;
import com.projetjava.domain.Utilisateur;
import com.projetjava.exceptions.EmailException;
import com.projetjava.exceptions.MotDePasseException;
import com.projetjava.model.dao.impl.UtilisateurDao;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;

public class LoginPageController implements Initializable {

    @FXML
    private Button ConnecterBtn;

    @FXML
    private Button CreateAccountBtn;

    @FXML
    private TextField EmailIn;

    @FXML
    private TextField EmailSignUp;

    @FXML
    private Button HaveAccountBtn1;

    @FXML
    private Button InscrireBtn;

    @FXML
    private PasswordField PasswordIn;

    @FXML
    private PasswordField PaswordSignUp;

    @FXML
    private ComboBox Role;

    @FXML
    private AnchorPane SignIn;

    @FXML
    private AnchorPane SignUp;

    @FXML
    private AnchorPane SignUpForm;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Prenom;
    @FXML
    private TextField Filomod;
    @FXML
    private AnchorPane SideAlradyHaveAccount;
    @FXML
    private Label LabelAleady;
    @FXML
    private Label LabelDont;

    private Alert alert;

    public void regBtn2() throws EmailException, MotDePasseException {
        if (EmailSignUp.getText().isEmpty() || PaswordSignUp.getText().isEmpty() || Nom.getText().isEmpty() || Prenom.getText().isEmpty() || Role.getSelectionModel().isEmpty() || Filomod.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Remplissez tous les champs !!");
            alert.showAndWait();
        } else {
            Utilisateur utilisateur = new Utilisateur() {
                @Override
                public String afficher() {
                    return null;
                }
            };
            utilisateur.setNom(Nom.getText());
            utilisateur.setPrenom(Prenom.getText());

            try {
                utilisateur.setEmail(EmailSignUp.getText());
                utilisateur.setMotDePasseS(PaswordSignUp.getText());
            } catch (EmailException ex) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur Email");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
                return;
            } catch (MotDePasseException ex) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur Mot de Passe");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
                return;
            }

            utilisateur.setRole((String) Role.getSelectionModel().getSelectedItem());
            utilisateur.setFilomod(Filomod.getText());
            UtilisateurDao utilisateurDao = new UtilisateurDao();
            utilisateurDao.add(utilisateur);

            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Utilisateur enregistré avec succès !");
            alert.showAndWait();
            TranslateTransition slider = new TranslateTransition();
            slider.setNode(SignUp);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(0.5));

            slider.setOnFinished(e -> {
                HaveAccountBtn1.setVisible(false);
                CreateAccountBtn.setVisible(true);
                SignIn.setVisible(true);
                LabelDont.setVisible(true);
                LabelAleady.setVisible(false);
            });

            slider.play();
        }
    }

    public boolean verifierMotDePasse(String motDePasseSaisi, String motDePasseHache) {
        return BCrypt.checkpw(motDePasseSaisi, motDePasseHache);
    }

    public void regBtn(ActionEvent event) throws IOException, EmailException, MotDePasseException {
        if (EmailIn.getText().isEmpty() || PasswordIn.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Remplissez tous les champs !!!");
            alert.showAndWait();
        } else {
            UtilisateurDao utilisateurDao = new UtilisateurDao();
            Utilisateur utilisateur = utilisateurDao.getByEmail(EmailIn.getText());

            if (utilisateur != null) {
                boolean motDePasseValide = BCrypt.checkpw(PasswordIn.getText(), utilisateur.getMotDePasse());
                if (motDePasseValide) {
                    Session session = Session.getInstance();
                    session.setUtilisateurConnecte(utilisateur);
                    String role = utilisateur.getRole();
                    FXMLLoader loader;

                    try {
                        if ("professeur".equals(role)) {
                            loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/ProfAccueil.fxml"));
                        } else if ("etudiant".equals(role)) {
                            loader = new FXMLLoader(getClass().getResource("/com/projetjava/view/pages/EtudiantAccueil.fxml"));
                        } else {
                            throw new IllegalStateException("Rôle non valide.");
                        }
                        Parent root = loader.load();
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText(null);
                        alert.setContentText("Erreur lors du chargement de la page : " + e.getMessage());
                        alert.showAndWait();
                    }

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Email ou mot de passe incorrect !");
                    alert.showAndWait();
                }
            } else {
                System.out.println("Échec de la connexion.");
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Email ou mot de passe incorrect !");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void Switchform(ActionEvent event
    ) {
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == CreateAccountBtn) {
            slider.setNode(SignUp);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(0.5));
            slider.setOnFinished(e -> {
                HaveAccountBtn1.setVisible(true);
                CreateAccountBtn.setVisible(false);
                SignIn.setVisible(false);
                LabelDont.setVisible(false);
                LabelAleady.setVisible(true);

            });

            slider.play();
        } else if (event.getSource() == HaveAccountBtn1) {
            slider.setNode(SignUp);

            slider.setToX(0);
            slider.setDuration(Duration.seconds(0.5));
            slider.setOnFinished(e -> {
                HaveAccountBtn1.setVisible(false);
                CreateAccountBtn.setVisible(true);
                SignIn.setVisible(true);
                LabelDont.setVisible(true);
                LabelAleady.setVisible(false);

            });

            slider.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        ObservableList<String> list = FXCollections.observableArrayList("Professeur", "Etudiant");
        Role.setItems(list);
    }

}
