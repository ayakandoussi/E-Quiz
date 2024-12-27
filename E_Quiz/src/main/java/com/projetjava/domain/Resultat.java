package com.projetjava.domain;

public class Resultat {

    private int idResultatQuiz = 0;
    private int idEtudiant;  //a modifier
    private int idQuiz;      //a modifier
    private double score;
    private String nomEtudiant, prenomEtudiant, titreQuiz;

    public Resultat() {
    }

    public Resultat(int idEtudiant, int idQuiz, double score, String nomEtudiant, String prenomEtudiant, String titreQuiz) {
        this.idResultatQuiz++;
        this.idEtudiant = idEtudiant;
        this.idQuiz = idQuiz;
        this.score = score;
        this.nomEtudiant = nomEtudiant;
        this.prenomEtudiant = prenomEtudiant;
        this.titreQuiz = titreQuiz;
    }

    public int getIdResultatQuiz() {
        return idResultatQuiz;
    }

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public double getScore() {
        return score;
    }

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public String getPrenomEtudiant() {
        return prenomEtudiant;
    }

    public String getTitreQuiz() {
        return titreQuiz;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public void setPrenomEtudiant(String prenomEtudiant) {
        this.prenomEtudiant = prenomEtudiant;
    }

    public void setTitreQuiz(String titreQuiz) {
        this.titreQuiz = titreQuiz;
    }

    public void afficherObservation() {
        if (this.score <= 5) {
            System.out.println("Nul");
        } else if (this.score > 5 && this.score <= 10) {
            System.out.println("Passable");
        } else if (this.score > 10 && this.score <= 15) {
            System.out.println("Bien");
        } else if (this.score > 15 && this.score <= 19) {
            System.out.println("Tres bien");
        } else {
            System.out.println("Excellent");
        }
    }

}
