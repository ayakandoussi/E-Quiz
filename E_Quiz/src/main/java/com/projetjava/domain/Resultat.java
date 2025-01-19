package com.projetjava.domain;

public class Resultat implements Interpretation{

    private  int idResultatQuiz;
    private double score;
    private Etudiant etudiant;
    private int idQuiz;

    public Resultat() {
    }

    public Resultat(Etudiant etudiant, int idQuiz, double score) {

        this.idQuiz = idQuiz;
        this.etudiant = etudiant;
        this.score = score;
    }

    public int getIdResultatQuiz() {
        return idResultatQuiz;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

   

    public double getScore() {
        return score;
    }

    public int getIdQuizzes() {
        return idQuiz;
    }

    public void setIdResultatQuiz(int idResultatQuiz) {
        this.idResultatQuiz = idResultatQuiz;
    }

    public void setScore(double score) {
        this.score = score;
    }

   

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public void setIdQuizzes(int idQuiz) {
        this.idQuiz = idQuiz;
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
