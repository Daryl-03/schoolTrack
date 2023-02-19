package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;

public interface DAO<T> {
    public void create(T t) throws DAOException;
    public T read(int id) throws DAOException;
    public void update(T t) throws DAOException;
    public void delete(int id) throws DAOException;
    public void readAll() throws DAOException;
}
