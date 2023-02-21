package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Note;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO implements DAO<Note>{
    private int id_bulletin;
    
    /**
     * insère une note dans la base de données
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
            if( id_bulletin == 0)
                throw new DAOException("L'id du bulletin est nul");
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * lit une note à partir de son id
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
     * met à jour une note
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
    
    /**
     * supprime une note
     * @param id
     * @throws DAOException
     */
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
    
    /**
     * lit toutes les notes d'un bulletin
     * @return
     * @throws DAOException
     */
    @Override
    public List<Note> readAll() throws DAOException {
        List<Note> notes = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM note WHERE id_bulletin = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_bulletin);
            if( id_bulletin == 0)
                throw new DAOException("L'id du bulletin est nul");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Note note = new Note(
                        rs.getInt("id"),
                        rs.getDouble("valeur"),
                        new MatiereDAO().read(rs.getInt("id_matiere"))
                );
                notes.add(note);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return notes;
    }
}
