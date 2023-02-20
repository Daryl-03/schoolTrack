package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Classe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClasseDAO implements DAO<Classe>{
    private int id_section;
    
    public int getId_section() {
        return id_section;
    }
    
    public void setId_section(int id_section) {
        this.id_section = id_section;
    }
    
    /**
     * Insertion d'une classe dans la base de données
     * @param classe
     * @throws DAOException
     */
    @Override
    public void create(Classe classe) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            // Insertion de l'élève
            String sql = "INSERT INTO classe (nom, id_section) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classe.getNom());
            preparedStatement.setInt(2, id_section);
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * Retourne une classe à partir de son id
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public Classe read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM classe WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }
    
    @Override
    public void update(Classe classe) throws DAOException {
    
    }
    
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    /**
     * Retourne la liste de toutes les classes
     * @return
     * @throws DAOException
     */
    @Override
    public List<Classe> readAll() throws DAOException {
        List<Classe> classes = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM classe";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Classe classe = new Classe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        FXCollections.observableArrayList(new RubriqueDAO().readAll()),
                        FXCollections.observableArrayList(new MatiereDAO().readAll()),
                        FXCollections.observableArrayList(new EleveDAO().readAll())
                );
                classes.add(classe);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }
    
    /**
     * Retourne la liste des classes d'une section
     * @param idSection
     * @return
     * @throws DAOException
     */
    public List<Classe> readAll(int idSection) throws DAOException {
        List<Classe> classes = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM classe WHERE id_section = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idSection);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Classe classe = new Classe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        FXCollections.observableArrayList(new RubriqueDAO().readAll()),
                        FXCollections.observableArrayList(new MatiereDAO().readAll()),
                        FXCollections.observableArrayList(new EleveDAO().readAll())
                );
                classes.add(classe);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }
}
