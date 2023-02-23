package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Utilisateur;
import com.schooltrack.models.factory.UtilisateurFactory;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
    }
    
    @Override
    public Utilisateur read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM utilisateur WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
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
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "DELETE FROM utilisateur WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
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
                Utilisateur utilisateur = UtilisateurFactory.getUtilisateur(resultSet.getString("type"));
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setPrenom(resultSet.getString("prenom"));
                utilisateur.setLogin(resultSet.getString("login"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setTelephone(resultSet.getString("numeroTel"));
                utilisateur.setPassword(resultSet.getString("password"));
                utilisateurs.add(utilisateur);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return utilisateurs;
    }
    
    /**
     * lis un utilisateur par son login
     */
    public Utilisateur readByLogin(String login) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM utilisateur WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Utilisateur utilisateur = null;
                utilisateur = UtilisateurFactory.getUtilisateur(resultSet.getString("type"));
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setPrenom(resultSet.getString("prenom"));
                utilisateur.setLogin(resultSet.getString("login"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setTelephone(resultSet.getString("numeroTel"));
                utilisateur.setPassword(resultSet.getString("password"));
                return utilisateur;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return null;
    }
    
    /**
     * lis un utilisateur par son login et mot de passe
     * @return l'utilisateur si il existe, null sinon
     */
    public Utilisateur readByLoginPassword(String login, String password) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM utilisateur WHERE login = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Utilisateur utilisateur = null;
                utilisateur = UtilisateurFactory.getUtilisateur(resultSet.getString("type"));
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setPrenom(resultSet.getString("prenom"));
                utilisateur.setLogin(resultSet.getString("login"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setTelephone(resultSet.getString("numeroTel"));
                utilisateur.setPassword(resultSet.getString("password"));
                return utilisateur;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return null;
    }
}
