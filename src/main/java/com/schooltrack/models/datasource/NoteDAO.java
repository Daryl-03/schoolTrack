package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.models.Note;

import java.util.List;

public class NoteDAO implements DAO<Note>{
    
    @Override
    public void create(Note note) throws DAOException {
    
    }
    
    @Override
    public Note read(int id) throws DAOException {
        return null;
    }
    
    @Override
    public void update(Note note) throws DAOException {
    
    }
    
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    @Override
    public List<Note> readAll() throws DAOException {
        return null;
    }
}
