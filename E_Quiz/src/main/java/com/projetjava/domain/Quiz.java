/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.projetjava.domain;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
 
    private int id; 
    private String titre; 
    private String description;
    private String theme; 
    private int idEnseignant; // Identifiant du professeur qui a créé le quiz
    private List<Question> questions; // Liste des questions associées au quiz
    public Quiz() {
      
    }
    
    public Quiz( String titre, String description, String theme, int idEnseignant ,Question questions) {

        this.titre = titre;
        this.description = description;
        this.theme = theme;
        this.idEnseignant = idEnseignant;
        this.questions =  new ArrayList<>();
    }
    
  

    // Getters et Setters
    public int getIdQuiz() {
        return id;
    }

    public void setIdQuiz(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre= titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme= theme;
    }

    public int getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(int idEnseignant) {
        this.idEnseignant = idEnseignant;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", theme=" + theme +
                ", idEnseignant=" + idEnseignant +
                ", questions=" + (questions != null ? questions.size() : 0) +
                '}';
    }

    

   
}





    

