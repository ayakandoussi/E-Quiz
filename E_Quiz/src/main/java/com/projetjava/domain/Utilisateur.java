package com.projetjava.domain;
import org.mindrot.jbcrypt.BCrypt;

public abstract class Utilisateur {

    private static int id = 0;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String role;

    public Utilisateur() {
    }
public Utilisateur(Utilisateur U) {
    this.id++;
    this.nom=U.nom;
    this.prenom=U.prenom;
    this.email=U.email;
    this.motDePasse=U.motDePasse;
    this.role=U.role;
    }
    public Utilisateur(String nom, String prenom, String email, String motDePasse, String role) {
        this.id++;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.setMotDePasse(motDePasse);
        this.role = role;
    }

    public static int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

     public void setMotDePasse(String motDePasse) {
        String motDePasseHache = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
        this.motDePasse = motDePasseHache;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    public void setRole(String role) {
        this.role = role;
    }

    public abstract void afficher();

}