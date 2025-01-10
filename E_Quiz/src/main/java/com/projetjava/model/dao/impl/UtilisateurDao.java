package com.projetjava.model.dao.impl;

import org.mindrot.jbcrypt.BCrypt;

import com.projetjava.domain.Etudiant;
import com.projetjava.domain.Professeur;
import com.projetjava.domain.Utilisateur;
import com.projetjava.exceptions.EmailException;
import com.projetjava.exceptions.MotDePasseException;
import com.projetjava.model.dao.Dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class UtilisateurDao implements Dao<Utilisateur> {

    @Override
    public void add(Utilisateur utilisateur) {
        try {
            BDConnexion bd = new BDConnexion();
            String query = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, role, filomod) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            String hashedPassword = BCrypt.hashpw(utilisateur.getMotDePasse(), BCrypt.gensalt());
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.setString(3, utilisateur.getEmail());
            preparedStatement.setString(4, hashedPassword);
            preparedStatement.setString(5, utilisateur.getRole());
            preparedStatement.setString(6, utilisateur.getFilomod());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erreur lors de l'ajout de l'utilisateur.");

        }
    }

    @Override
    public Utilisateur getById(int id) {
        Utilisateur utilisateur = null;
        try {
            BDConnexion bd = new BDConnexion();
            String query = "SELECT * FROM utilisateur WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String role = resultSet.getString("role");
                if (role.equals("professeur")) {
                    utilisateur = new Professeur(); // Type spécifique
                } else if (role.equals("etudiant")) {
                    utilisateur = new Etudiant(); // Type spécifique
                }
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setPrenom(resultSet.getString("prenom"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));
                utilisateur.setFilomod(resultSet.getString("filomod"));
                utilisateur.setRole(role);
            }
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {

        } catch (EmailException ex) {
            System.out.println(ex.getMessage());
        } catch (MotDePasseException ex) {
            System.out.println(ex.getMessage());
        }
        return utilisateur;
    }

    @Override
    public ArrayList<Utilisateur> getAll() {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        try {
            BDConnexion bd = new BDConnexion();
            String query = "SELECT * FROM utilisateur";
            Statement statement = bd.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String role = resultSet.getString("role");
                Utilisateur utilisateur;
                if (role.equals("professeur")) {
                    utilisateur = new Professeur();
                } else {
                    utilisateur = new Etudiant();
                }
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setPrenom(resultSet.getString("prenom"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));
                utilisateur.setFilomod(resultSet.getString("filomod"));
                utilisateur.setRole(role);
                utilisateurs.add(utilisateur);
            }
            statement.close();
            bd.close();
        } catch (SQLException ex) {

        } catch (EmailException ex) {
            System.out.println(ex.getMessage());
        } catch (MotDePasseException ex) {
            System.out.println(ex.getMessage());
        }
        return utilisateurs;
    }

    public ArrayList<Professeur> getAllProfesseurs() throws EmailException, MotDePasseException {
        ArrayList<Professeur> professeurs = new ArrayList<>();
        try {
            BDConnexion bd = new BDConnexion();
            String query = "SELECT * FROM utilisateur WHERE role= ? ";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setString(1, "professeur");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Professeur professeur = new Professeur();
                professeur.setNom(resultSet.getString("nom"));
                professeur.setPrenom(resultSet.getString("prenom"));
                professeur.setEmail(resultSet.getString("email"));
                professeur.setMotDePasse(resultSet.getString("mot_de_passe"));
                professeur.setRole(resultSet.getString("role"));
                professeur.setFilomod(resultSet.getString("filomod"));
                professeur.setId(resultSet.getInt("id"));
                professeurs.add(professeur);
                System.out.println("Ajout du professeur : " + professeur.getFilomod());
            }
            System.out.println("nbr prof"+ professeurs.size());
            resultSet.close();
            preparedStatement.close();
            bd.close();
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return professeurs;
        
    }

    @Override
    public void update(Utilisateur utilisateur) {
        try {
            BDConnexion bd = new BDConnexion();
            String query = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, role = ?, , filomod= ? WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.setString(3, utilisateur.getEmail());
            preparedStatement.setString(4, utilisateur.getMotDePasse());
            preparedStatement.setString(5, utilisateur.getRole());
            preparedStatement.setString(6, utilisateur.getFilomod());
            preparedStatement.setInt(6, utilisateur.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {

        }
    }

    @Override
    public void delete(int id) {
        try {
            BDConnexion bd = new BDConnexion();
            String query = "DELETE FROM utilisateur WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {

        }
    }

    public Utilisateur getByEmail(String email) throws EmailException, MotDePasseException {
        Utilisateur utilisateur = null;
        try {
            BDConnexion bd = new BDConnexion();
            String query = "SELECT * FROM utilisateur WHERE email = ? ";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String role = resultSet.getString("role");
                if (role.equals("professeur")) {
                    utilisateur = new Professeur(); // Type spécifique
                } else if (role.equals("etudiant")) {
                    utilisateur = new Etudiant(); // Type spécifique
                }
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setPrenom(resultSet.getString("prenom"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setMotDePasseS(resultSet.getString("mot_de_passe"));
                utilisateur.setRole(role);
                utilisateur.setFilomod(resultSet.getString("filomod"));
            }
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return utilisateur;
    }

}
