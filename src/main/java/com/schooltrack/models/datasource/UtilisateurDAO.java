package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Utilisateur;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UtilisateurDAO implements DAO<Utilisateur>{
    
    @Override
    public void create(Utilisateur utilisateur) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "INSERT INTO utilisateur (nom, prenom, login, email, numeroTel, password, type ) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.setString(3, utilisateur.getLogin());
            preparedStatement.setString(4, utilisateur.getEmail());
            preparedStatement.setString(5, utilisateur.getTelephone());
            preparedStatement.setString(6, utilisateur.getPassword());
            preparedStatement.setString(7, utilisateur.getType());
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    @Override
    public Utilisateur read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM utilisateur WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }
    
    @Override
    public void update(Utilisateur utilisateur) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE utilisateur SET nom = ?, prenom = ?, login = ?, email = ?, numeroTel = ?, password = ?, type = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.setString(3, utilisateur.getLogin());
            preparedStatement.setString(4, utilisateur.getEmail());
            preparedStatement.setString(5, utilisateur.getTelephone());
            preparedStatement.setString(6, utilisateur.getPassword());
            preparedStatement.setString(7, utilisateur.getType());
            preparedStatement.setInt(8, utilisateur.getId());
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "DELETE FROM utilisateur WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    @Override
    public List<Utilisateur> readAll() throws DAOException {
        List<Utilisateur> utilisateurs = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM utilisateur";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //créer un caissier, un secrétaire ou un administateur selon le type
                Utilisateur utilisateur = null;
                
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * create
     */
}
