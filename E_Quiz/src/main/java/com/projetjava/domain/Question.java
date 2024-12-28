/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projetjava.domain;

/**
 *
 * @author Bouchama
 */
public class Question {
    private int idQuestion;
    private String enonce;
    private String choix1;
    private String choix2;
    private String choix3;
    private String choix4;
    private String bonneReponse; // La bonne réponse parmi les quatre choix
    private int idQuiz;

    // Constructeur, getters et setters
    public Question(int idQuestion, String enonce, String choix1, String choix2, String choix3, String choix4, String bonneReponse, int idQuiz) {
        this.idQuestion = idQuestion;
        this.enonce = enonce;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.choix3 = choix3;
        this.choix4 = choix4;
        this.bonneReponse = bonneReponse;
        this.idQuiz = idQuiz;
    }

    // Getters et setters
    public int getIdQuestion() {
        return idQuestion;
    }

    public String getEnonce() {
        return enonce;
    }

    public String getChoix1() {
        return choix1;
    }

    public String getChoix2() {
        return choix2;
    }

    public String getChoix3() {
        return choix3;
    }

    public String getChoix4() {
        return choix4;
    }

    public String getBonneReponse() {
        return bonneReponse;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public void setChoix1(String choix1) {
        this.choix1 = choix1;
    }

    public void setChoix2(String choix2) {
        this.choix2 = choix2;
    }

    public void setChoix3(String choix3) {
        this.choix3 = choix3;
    }

    public void setChoix4(String choix4) {
        this.choix4 = choix4;
    }

    public void setBonneReponse(String bonneReponse) {
        this.bonneReponse = bonneReponse;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }
}
