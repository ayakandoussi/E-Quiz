package com.projetjava.domain;

import com.projetjava.exceptions.BonChoixException;

public class Question {

    private int idQuestion;
    private String enonce;
    private String choix1;
    private String choix2;
    private String choix3;
    private String choix4;
    private String bonneReponse;
    private int idQuiz;

    public Question() {
    }

    public Question(int idQuestion, String enonce, String choix1, String choix2, String choix3, String choix4, String bonneReponse, int idQuiz) throws BonChoixException {
        this.idQuestion = idQuestion;
        this.enonce = enonce;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.choix3 = choix3;
        this.choix4 = choix4;
        setBonneReponse(bonneReponse);
        this.idQuiz = idQuiz;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public String getChoix1() {
        return choix1;
    }

    public void setChoix1(String choix1) {
        this.choix1 = choix1;
    }

    public String getChoix2() {
        return choix2;
    }

    public void setChoix2(String choix2) {
        this.choix2 = choix2;
    }

    public String getChoix3() {
        return choix3;
    }

    public void setChoix3(String choix3) {
        this.choix3 = choix3;
    }

    public String getChoix4() {
        return choix4;
    }

    public void setChoix4(String choix4) {
        this.choix4 = choix4;
    }

    public String getBonneReponse() {
        return bonneReponse;
    }

    public void setBonneReponse(String bonneReponse)throws BonChoixException{
        if (!bonneReponse.equals(getChoix1()) && !bonneReponse.equals(getChoix2())&& !bonneReponse.equals(getChoix3()) && !bonneReponse.equals(getChoix4())){
            throw new BonChoixException("Le bon choix doit être parmi les 4 choix");
        }
        this.bonneReponse = bonneReponse;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }
}
