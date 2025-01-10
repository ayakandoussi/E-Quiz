package com.projetjava.domain;

public class Quiz {

    private int id;
    private String titre;
    private String description;

    private int idEnseignant; // Identifiant du professeur qui a créé le quiz

    public Quiz() {

    }

    public Quiz(String titre, String description, int idEnseignant) {

        this.titre = titre;
        this.description = description;
        this.idEnseignant = idEnseignant;

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
        this.titre = titre;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(int idEnseignant) {
        this.idEnseignant = idEnseignant;
    }

    @Override
    public String toString() {
        return "Quiz{"
                + "id=" + id
                + ", titre='" + titre + '\''
                + ", idEnseignant=" + idEnseignant
                + '}';
    }

}
