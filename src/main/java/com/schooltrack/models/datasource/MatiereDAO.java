package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Matiere;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatiereDAO implements DAO<Matiere>{

    /**
     * Ajout d'une matière dans la base de données
     * @param matiere
     * @throws DAOException
     */
    @Override
    public void create(Matiere matiere) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            // Insertion de l'élève
            String sql = "INSERT INTO matiere (nom, description, coefficient, statut, id_classe) VALUES (?, ?, ?, 'en cours', ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, matiere.getNom());
            preparedStatement.setString(2, matiere.getDescription());
            preparedStatement.setDouble(3, matiere.getCoefficient());
            preparedStatement.setInt(4, matiere.getId_classe());
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * Lecture d'une matière dans la base de données
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public Matiere read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM matiere WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Matiere matiere = new Matiere(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getInt("coefficient"),
                        resultSet.getInt("id_classe")
                );
                return matiere;
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }
    
    /**
     * Mets à jour une matière dans la base de données
     * @param matiere
     * @throws DAOException
     */
    @Override
    public void update(Matiere matiere) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE matiere SET nom = ?, description = ?, coefficient = ?, id_classe = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, matiere.getNom());
            preparedStatement.setString(2, matiere.getDescription());
            preparedStatement.setDouble(3, matiere.getCoefficient());
            preparedStatement.setInt(4, matiere.getId_classe());
            preparedStatement.setInt(5, matiere.getId());
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * suppression d'une matière du cursus actuel
     * @param id
     * @throws DAOException
     */
    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE matiere SET statut = 'supprimée' WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * Lecture de toutes les matières de la base de données
     * @return
     * @throws DAOException
     */
    @Override
    public List<Matiere> readAll() throws DAOException {
        List<Matiere> matieres = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM matiere WHERE statut = 'en cours'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Matiere matiere = new Matiere(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getInt("coefficient"),
                        resultSet.getInt("id_classe")
                );
                matieres.add(matiere);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return matieres;
    }
    
    /**
     * Lecture de toutes les matières d'une classe
     * @param idClasse
     * @return
     * @throws DAOException
     */
    public List<Matiere> readAllByClasse(int idClasse) throws DAOException {
        List<Matiere> matieres = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM matiere WHERE statut = 'en cours' AND id_classe = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idClasse);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Matiere matiere = new Matiere(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getInt("coefficient"),
                        resultSet.getInt("id_classe")
                );
                matieres.add(matiere);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return matieres;
    }
}
