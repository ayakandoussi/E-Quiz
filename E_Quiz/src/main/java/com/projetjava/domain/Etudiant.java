package com.projetjava.domain;

import com.projetjava.exceptions.EmailException;
import com.projetjava.exceptions.MotDePasseException;
import java.util.ArrayList;

public class Etudiant extends Utilisateur {

    private ArrayList<Quiz> quizDisponibles;

    public Etudiant(String nom, String prenom, String email, String motDePasse, String role, String filomod, ArrayList<Quiz> quizDisponibles) throws EmailException,MotDePasseException {
        super(nom, prenom, email, motDePasse, role, filomod);
        this.quizDisponibles = quizDisponibles;
    }

    public Etudiant() {
        super();
        this.quizDisponibles = new ArrayList<>();
    }

    public Etudiant(Utilisateur utilisateur) {
        super(utilisateur);
    }

    @Override
    public String afficher() {
        return ("Etudiant: " + getPrenom() + " " + getNom());
    }

}
