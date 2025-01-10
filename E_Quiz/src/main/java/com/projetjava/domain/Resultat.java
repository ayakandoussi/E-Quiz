package com.projetjava.domain;

public class Resultat implements Interpretation{

    private  int idResultatQuiz;
    private Quiz quiz;
    private double score;
    private Etudiant etudiant;

    public Resultat() {
    }

    public Resultat(Etudiant etudiant, Quiz quiz, double score) {

        this.quiz = quiz;
        this.etudiant = etudiant;
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

    public void setIdResultatQuiz(int idResultatQuiz) {
        this.idResultatQuiz = idResultatQuiz;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    @Override
    public void afficheResume() {
        if (this.score >= 0 && this.score <= 5) {
            System.out.print(NULL);
        } else if (this.score > 5 && this.score <= 10) {
            System.out.print(PASSABLE);
        } else if (this.score > 10 && this.score <= 15) {
            System.out.print(BIEN);
        } else if (this.score > 15 && this.score <= 19) {
            System.out.print(TRES_BIEN);
        } else {
            System.out.print(EXCELLENT);
        }

    }
}
