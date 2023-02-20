package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;

import java.util.List;

public interface DAO<T> {
    void create(T t) throws DAOException;
    T read(int id) throws DAOException;
    void update(T t) throws DAOException;
    void delete(int id) throws DAOException;
    List<T> readAll() throws DAOException;
}
