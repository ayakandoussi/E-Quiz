package com.projetjava.domain;

public class Resultat {

    private static int idResultatQuiz = 0;
    private Quiz quiz;
    private double score;
    private Etudiant etudiant;

    public Resultat() {
    }

    public Resultat(Etudiant etudiant, Quiz quiz, double score) {
        this.idResultatQuiz++;
        this.quiz = new Quiz();
        this.etudiant = new Etudiant();
        this.score = score;
    }

    public int getIdResultatQuiz() {
        return idResultatQuiz;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void afficheResume() {
        if (this.score >= 0 && this.score <= 5) {
            System.out.print("Null!");
        } else if (this.score > 5 && this.score <= 10) {
            System.out.print("Passable!");
        } else if (this.score > 10 && this.score <= 15) {
            System.out.print("Bien!");
        } else if (this.score > 15 && this.score <= 19) {
            System.out.print("Tres Bien!");
        } else {
            System.out.print("Excellent!");
        }

    }
}