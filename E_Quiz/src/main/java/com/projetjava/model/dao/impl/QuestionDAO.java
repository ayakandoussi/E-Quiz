package com.projetjava.model.dao.impl;

import com.projetjava.domain.Question;
import com.projetjava.model.dao.Dao;
import java.sql.*;
import java.util.ArrayList;

public class QuestionDAO implements Dao<Question> {

    @Override
    public void add(Question question) {
        try {
            BDConnexion bd = new BDConnexion();
            String query = "INSERT INTO question (enonce, choix1, choix2, choix3, choix4, bonneReponse, idQuiz) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setString(1, question.getEnonce());
            preparedStatement.setString(2, question.getChoix1());
            preparedStatement.setString(3, question.getChoix2());
            preparedStatement.setString(4, question.getChoix3());
            preparedStatement.setString(5, question.getChoix4());
            preparedStatement.setString(6, question.getBonneReponse());
            preparedStatement.setInt(7, question.getIdQuiz());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Question getById(int id) {
        Question question = null;
        try {
            BDConnexion bd = new BDConnexion();
            String query = "SELECT * FROM question WHERE idQuestion = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                question = new Question();
                question.setIdQuestion(resultSet.getInt("idQuestion"));
                question.setEnonce(resultSet.getString("enonce"));
                question.setChoix1(resultSet.getString("choix1"));
                question.setChoix2(resultSet.getString("choix2"));
                question.setChoix3(resultSet.getString("choix3"));
                question.setChoix4(resultSet.getString("choix4"));
                question.setBonneReponse(resultSet.getString("bonneReponse"));
                question.setIdQuiz(resultSet.getInt("idQuiz"));
            }
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return question;
    }

    @Override
    public ArrayList<Question> getAll() {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            BDConnexion bd = new BDConnexion();
            String query = "SELECT * FROM question";
            Statement statement = bd.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Question question = new Question();
                question.setIdQuestion(resultSet.getInt("idQuestion"));
                question.setEnonce(resultSet.getString("enonce"));
                question.setChoix1(resultSet.getString("choix1"));
                question.setChoix2(resultSet.getString("choix2"));
                question.setChoix3(resultSet.getString("choix3"));
                question.setChoix4(resultSet.getString("choix4"));
                question.setBonneReponse(resultSet.getString("bonneReponse"));
                question.setIdQuiz(resultSet.getInt("idQuiz"));
                questions.add(question);
            }
            statement.close();
            bd.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return questions;
    }

    @Override
    public void update(Question question) {
        try {
            BDConnexion bd = new BDConnexion();
            String query = "UPDATE question SET enonce = ?, choix1 = ?, choix2 = ?, choix3 = ?, choix4 = ?, bonneReponse = ?, idQuiz = ? WHERE idQuestion = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setString(1, question.getEnonce());
            preparedStatement.setString(2, question.getChoix1());
            preparedStatement.setString(3, question.getChoix2());
            preparedStatement.setString(4, question.getChoix3());
            preparedStatement.setString(5, question.getChoix4());
            preparedStatement.setString(6, question.getBonneReponse());
            preparedStatement.setInt(7, question.getIdQuiz());
            preparedStatement.setInt(8, question.getIdQuestion());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            BDConnexion bd = new BDConnexion();
            String query = "DELETE FROM question WHERE idQuestion = ?";
            PreparedStatement preparedStatement = bd.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            bd.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
