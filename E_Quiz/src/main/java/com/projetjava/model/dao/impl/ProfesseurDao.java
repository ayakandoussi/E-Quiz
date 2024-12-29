package com.projetjava.model.dao.impl;

import com.projetjava.domain.Professeur;
import com.projetjava.model.dao.Dao;
import java.sql.*;
import java.util.ArrayList;

public class ProfesseurDao implements Dao<Professeur> {

    @Override
    public Professeur getById(int id) {
        Professeur professeur = null;
        try {
            BDConnexion bd = new BDConnexion();
            String query = "SELECT * FROM professeur WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                professeur = new Professeur();
                professeur.setNom(resultSet.getString("nom"));
                professeur.setPrenom(resultSet.getString("prenom"));
                professeur.setEmail(resultSet.getString("email"));
                professeur.setMotDePasse(resultSet.getString("mot_de_passe"));
                professeur.setRole(resultSet.getString("role"));
            }
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
        }
        return professeur;
    }

    @Override
    public ArrayList<Professeur> getAll() {
        ArrayList<Professeur> professeurs = new ArrayList<>();
        try {
            BDConnexion bd = new BDConnexion();
            String query = "SELECT * FROM professeur";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Professeur professeur = new Professeur();
                professeur.setNom(resultSet.getString("nom"));
                professeur.setPrenom(resultSet.getString("prenom"));
                professeur.setEmail(resultSet.getString("email"));
                professeur.setMotDePasse(resultSet.getString("mot_de_passe"));
                professeur.setRole(resultSet.getString("role"));
                professeurs.add(professeur);
            }
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
        }
        return professeurs;
    }

    @Override
    public void delete(int id) {
        try {
            BDConnexion bd = new BDConnexion();
            String query = "DELETE FROM professeur WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
        }
    }

    @Override
    public void add(Professeur professeur) {
         try {
            BDConnexion bd = new BDConnexion();
            String query = "INSERT INTO professeur (nom, prenom, email, mot_de_passe, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setString(1, professeur.getNom());
            preparedStatement.setString(2, professeur.getPrenom());
            preparedStatement.setString(3, professeur.getEmail());
            preparedStatement.setString(4, professeur.getMotDePasse());
            preparedStatement.setString(5, professeur.getRole());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
        }
    }

    @Override
    public void update(Professeur professeur) {
         try {
            BDConnexion bd = new BDConnexion();
            String query = "UPDATE professeur SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, role = ? WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setString(1, professeur.getNom());
            preparedStatement.setString(2, professeur.getPrenom());
            preparedStatement.setString(3, professeur.getEmail());
            preparedStatement.setString(4, professeur.getMotDePasse());
            preparedStatement.setString(5, professeur.getRole());
            preparedStatement.setInt(6, professeur.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
        }
}
}

   
