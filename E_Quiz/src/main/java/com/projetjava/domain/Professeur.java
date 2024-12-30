package com.projetjava.domain;

import java.util.ArrayList;

public class Professeur extends Utilisateur {

    private ArrayList<Quiz> quizCrees;

    public Professeur() {
        super();
        this.quizCrees = new ArrayList<>();
    }

    public Professeur(String nom, String prenom, String email, String motDePasse, String role, ArrayList<Quiz> quizCrees) {
        super(nom, prenom, email, motDePasse, role);
        this.quizCrees = quizCrees;
    }

    public ArrayList<Quiz> getQuizCrees() {
        return quizCrees;
    }

    public void setQuizCrees(ArrayList<Quiz> quizCrees) {
        this.quizCrees = quizCrees;
    }

    @Override
    public void afficher() {
        System.out.println("Professeur: " + getNom() + " " + getPrenom() );
    }

}
