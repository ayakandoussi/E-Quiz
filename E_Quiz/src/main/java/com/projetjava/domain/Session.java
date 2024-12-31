
package com.projetjava.domain;

public class Session {
    private static Session instance;
    private Utilisateur utilisateurConnecte;


    private Session() {}

    
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

   
    public void setUtilisateurConnecte(Utilisateur utilisateur) {
        this.utilisateurConnecte = utilisateur;
    }
}