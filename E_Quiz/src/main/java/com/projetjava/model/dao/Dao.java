package com.projetjava.model.dao;

import java.util.ArrayList;

public interface Dao<T> {

    void add(T objet);

    T getById(int id);

    ArrayList<T> getAll();

    void update(T objet);

    void delete(int id);
}

