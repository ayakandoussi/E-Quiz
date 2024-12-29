package com.projetjava.model.dao;

import java.util.List;

public interface Dao<T> {

    void add(T objet);

    T getById(int id);

    List<T> getAll();

    void update(T objet);

    void delete(int id);
}
