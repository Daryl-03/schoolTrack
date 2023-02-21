package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Bulletin;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BulletinDAO implements DAO<Bulletin>{
    private int id_eleve;
    private int id_classe;
    private int id_annee;
    @Override
    public void create(Bulletin bulletin) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "INSERT INTO bulletin (trimestre, moyenne, id_eleve, id_classe, id_annee) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bulletin.getTrimestre());
            preparedStatement.setFloat(2, bulletin.getMoyenne());
            preparedStatement.setInt(3, id_eleve);
            preparedStatement.setInt(4, id_classe);
            preparedStatement.setInt(5, id_annee);
            if(id_annee == 0 || id_classe == 0 || id_eleve == 0) {
                throw new DAOException("L'élève, la classe ou l'année n'existe pas");
            }
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    @Override
    public Bulletin read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM bulletin WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }
    
    @Override
    public void update(Bulletin bulletin) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE bulletin SET trimestre = ?, moyenne = ?, id_eleve = ?, id_classe = ?, id_annee = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bulletin.getTrimestre());
            preparedStatement.setFloat(2, bulletin.getMoyenne());
            preparedStatement.setInt(3, id_eleve);
            preparedStatement.setInt(4, id_classe);
            preparedStatement.setInt(5, id_annee);
            preparedStatement.setInt(6, bulletin.getId());
            if(id_annee == 0 || id_classe == 0 || id_eleve == 0) {
                throw new DAOException("L'élève, la classe ou l'année n'existe pas");
            }
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "DELETE FROM bulletin WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    @Override
    public List<Bulletin> readAll() throws DAOException {
        List<Bulletin> bulletins = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM bulletin";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeQuery();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return bulletins;
    }
    
    /**
     * lister les bulletins d'un élève par année
      */
    public List<Bulletin> readAll(int id_eleve, int id_annee) throws DAOException {
        List<Bulletin> bulletins = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM bulletin WHERE id_eleve = ? AND id_annee = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_annee);
            preparedStatement.executeQuery();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return bulletins;
    }
    
    /**
     * lire un bulletin d'un eleve par année et par trimestre
     */
    public Bulletin read(int id_eleve, int id_annee, int trimestre) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM bulletin WHERE id_eleve = ? AND id_annee = ? AND trimestre = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_annee);
            preparedStatement.setInt(3, trimestre);
            preparedStatement.executeQuery();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }
    
    
}
