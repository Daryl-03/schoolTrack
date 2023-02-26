package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Matiere;
import com.schooltrack.models.Note;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class NoteDAO implements DAO<Note>{
    
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
            preparedStatement.setInt(3, note.getId_bulletin());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
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
                        new MatiereDAO().read(rs.getInt("id_matiere")),
                        rs.getInt("id_bulletin")
                        );
                return note;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
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
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
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
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
    }
    
    /**
     * lit toutes les notes
     * @return
     * @throws DAOException
     */
    @Override
    public List<Note> readAll() throws DAOException {
        List<Note> notes = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM note ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Note note = new Note(
                        rs.getInt("id"),
                        rs.getDouble("valeur"),
                        new MatiereDAO().read(rs.getInt("id_matiere")),
                        rs.getInt("id_bulletin")
                );
                notes.add(note);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return notes;
    }
    
    /**
     * lit toutes les notes d'un bulletin
     * @param id_bulletin
     * @return
     * @throws DAOException
     */
    public List<Note> readAllByBulletin(int id_bulletin) throws DAOException {
        List<Note> notes = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM note WHERE id_bulletin = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_bulletin);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Note note = new Note(
                        rs.getInt("id"),
                        rs.getDouble("valeur"),
                        new MatiereDAO().read(rs.getInt("id_matiere")),
                        rs.getInt("id_bulletin")
                );
                notes.add(note);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return notes;
    }
    
    /**
     * génère des notes pour un bulletin. On crée une note pour chaque matière si la note n'existe pas déjà dans le bulletin
     * @param id_bulletin id du bulletin
     * @throws DAOException
     */
    public void generateNotes(int id_bulletin) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "INSERT INTO note (valeur, id_matiere, id_bulletin) SELECT 0, id, ? FROM matiere WHERE statut = 'en cours' AND id NOT IN (SELECT id_matiere FROM note WHERE id_bulletin = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_bulletin);
            preparedStatement.setInt(2, id_bulletin);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
    }
}
