package com.projetjava.domain;

import com.projetjava.exceptions.EmailException;
import com.projetjava.exceptions.MotDePasseException;
import org.mindrot.jbcrypt.BCrypt;

public abstract class Utilisateur {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String role, filomod;

    public Utilisateur() {
    }

    public Utilisateur(Utilisateur U) {
        this.nom = U.nom;
        this.prenom = U.prenom;
        this.email = U.email;
        this.motDePasse = U.motDePasse;
        this.role = U.role;
        this.filomod = U.filomod;
    }

    public Utilisateur(String nom, String prenom, String email, String motDePasse, String role, String filomod) throws EmailException, MotDePasseException {

        this.nom = nom;
        this.prenom = prenom;
        this.setEmail(email);
        this.setMotDePasse(motDePasse);
        this.role = role;
        this.filomod = filomod;
    }

    public int getId() {

        return id;
    }

    public String getRole() {
        return role;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getFilomod() {

        return filomod;
    }

    public void setMotDePasse(String motDePasse) throws MotDePasseException {
        if (motDePasse.length() < 8 || !motDePasse.matches(".*[A-Z].*") || !motDePasse.matches(".*[0-9].*")) {
            throw new MotDePasseException("Le mot de passe doit contenir au moins 8 caractères, une majuscule et un chiffre.");
        }
        String motDePasseHache = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
        this.motDePasse = motDePasseHache;
    }

    public void setMotDePasseS(String motDePasse) throws MotDePasseException {
        if (motDePasse.length() < 8 || !motDePasse.matches(".*[A-Z].*") || !motDePasse.matches(".*[0-9].*")) {
            throw new MotDePasseException("Le mot de passe doit contenir au moins 8 caractères, une majuscule et un chiffre.");
        }
        this.motDePasse = motDePasse;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) throws EmailException {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
            throw new EmailException("Adresse email invalide : " + email);
        }
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setFilomod(String filomod) {
        this.filomod = filomod;
    }

    public abstract String afficher();

}
