
package com.projetjava.model.dao.impl;

import com.projetjava.domain.Etudiant;
import com.projetjava.model.dao.Dao;
import java.sql.*;
import java.util.ArrayList;

public class EtudiantDao implements Dao<Etudiant> {
    
    @Override
    public void add(Etudiant etudiant) {
        try {
            BDConnexion bd = new BDConnexion(); 
            String query = "INSERT INTO etudiant (nom, prenom, email, mot_de_passe, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setString(1, etudiant.getNom());
            preparedStatement.setString(2, etudiant.getPrenom());
            preparedStatement.setString(3, etudiant.getEmail());
            preparedStatement.setString(4, etudiant.getMotDePasse());
            preparedStatement.setString(5, etudiant.getRole());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
        }
    }

    
    @Override
    public Etudiant getById(int id) {
        Etudiant etudiant = null;
        try {
            BDConnexion bd = new BDConnexion();
            String query = "SELECT * FROM etudiant WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                etudiant = new Etudiant();
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));
                etudiant.setEmail(resultSet.getString("email"));
                etudiant.setMotDePasse(resultSet.getString("mot_de_passe"));
                etudiant.setRole(resultSet.getString("role"));
            }
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
        }
        return etudiant;
    }


    @Override
    public ArrayList<Etudiant> getAll() {
        ArrayList<Etudiant> etudiants = new ArrayList<>();
        try {
            BDConnexion bd = new BDConnexion();
            String query = "SELECT * FROM etudiant";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));
                etudiant.setEmail(resultSet.getString("email"));
                etudiant.setMotDePasse(resultSet.getString("mot_de_passe"));
                etudiant.setRole(resultSet.getString("role"));
                etudiants.add(etudiant);
            }
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
        }
        return etudiants;
    }

    // Méthode pour mettre à jour un étudiant
    @Override
    public void update(Etudiant etudiant) {
        try {
            BDConnexion bd = new BDConnexion();
            String query = "UPDATE etudiant SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, role = ? WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setString(1, etudiant.getNom());
            preparedStatement.setString(2, etudiant.getPrenom());
            preparedStatement.setString(3, etudiant.getEmail());
            preparedStatement.setString(4, etudiant.getMotDePasse());
            preparedStatement.setString(5, etudiant.getRole());
            preparedStatement.setInt(6, etudiant.getId());
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
            String query = "DELETE FROM etudiant WHERE id = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
        }
    }
}
