package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NoteDAO implements DAO<Note>{
    private int id_bulletin;
    
    /**
     *
     * @param note
     * @throws DAOException
     */
    @Override
    public void create(Note note) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "INSERT INTO note (valeur, id_matiere, id_bulletin) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, note.getValeur());
            preparedStatement.setInt(2, note.getMatiere().getId());
            preparedStatement.setInt(3, id_bulletin);
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     *
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public Note read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM note WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Note note = new Note(
                        rs.getInt("id"),
                        rs.getDouble("valeur"),
                        new MatiereDAO().read(rs.getInt("id_matiere"))
                );
                return note;
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }
    
    /**
     *
     * @param note
     * @throws DAOException
     */
    @Override
    public void update(Note note) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE note SET valeur = ?, id_matiere = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, note.getValeur());
            preparedStatement.setInt(2, note.getMatiere().getId());
            preparedStatement.setInt(3, note.getId());
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "DELETE FROM note WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    @Override
    public List<Note> readAll() throws DAOException {
        
        return null;
    }
}
