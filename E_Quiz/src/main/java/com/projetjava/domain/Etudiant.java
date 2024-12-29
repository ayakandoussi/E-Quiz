package com.projetjava.domain;

import java.util.ArrayList;

public class Etudiant extends Utilisateur {

    private ArrayList<Quiz> quizDisponibles;

    public Etudiant(String nom, String prenom, String email, String motDePasse, String role, ArrayList<Quiz> quizDisponibles) {
        super(nom, prenom, email, motDePasse, role);
        this.quizDisponibles = quizDisponibles;
    }

    @Override
    public void afficher() {
        System.out.println("Etudiant: " + getNom() + " " + getPrenom() + ".");
    }

}
