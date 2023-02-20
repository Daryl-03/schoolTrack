package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.EcheancierPaiement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EcheancierPaiementDAO implements DAO<EcheancierPaiement>{
    
    /**
     * insère un nouvel échéancier de paiement dans la base de données
     * @param echeancierPaiement
     * @throws DAOException
     */
    @Override
    public void create(EcheancierPaiement echeancierPaiement) throws DAOException {
    
    }
    
    /**
     * Lit un échéancier de paiement dans la base de données
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public EcheancierPaiement read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM echeancier WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                EcheancierPaiement echeancierPaiement = new EcheancierPaiement(
                        resultSet.getInt("id"),
                        resultSet.getDouble("montant"),
                        FXCollections.observableArrayList(readEcheances(resultSet.getInt("id")))
                );
                return echeancierPaiement;
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }
    
    /**
     * mets à jour un échéancier de paiement dans la base de données
     * @param echeancierPaiement
     * @throws DAOException
     */
    @Override
    public void update(EcheancierPaiement echeancierPaiement) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE echeancier SET montant = ? WHERE id = ? AND id_annee = (SELECT id FROM annee ORDER BY id DESC LIMIT 1)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, echeancierPaiement.getMontant());
            preparedStatement.setInt(2, echeancierPaiement.getId());
            preparedStatement.executeUpdate();
            for (LocalDate echeance: echeancierPaiement.getEcheances()) {
                sql = "UPDATE echeance SET date = ? WHERE id_echeancier = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, echeancierPaiement.getId());
                preparedStatement.setDate(2, java.sql.Date.valueOf(echeance));
                preparedStatement.executeUpdate();
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * supprime un échéancier de paiement dans la base de données
     * @param id
     * @throws DAOException
     */
    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "DELETE FROM echeancier WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * Lit tous les échéanciers de paiement dans la base de données
     * @return
     * @throws DAOException
     */
    @Override
    public List<EcheancierPaiement> readAll() throws DAOException {
        List<EcheancierPaiement> echeancierPaiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM echeancier";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                EcheancierPaiement echeancierPaiement = new EcheancierPaiement(
                        resultSet.getInt("id"),
                        resultSet.getDouble("montant"),
                        FXCollections.observableArrayList(readEcheances(resultSet.getInt("id")))
                );
                echeancierPaiements.add(echeancierPaiement);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return echeancierPaiements;
    }
    
    /**
     * Lit les échéances d'un échéancier de paiement dans la base de données
     * @param id
     * @return
     * @throws DAOException
     */
    public List<LocalDate> readEcheances(int id) throws DAOException {
        List<LocalDate> echeances = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM echeance WHERE id_echeancier = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                echeances.add(resultSet.getDate("date").toLocalDate());
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return echeances;
    }
    
    /**
     * Lit l'échéancier de paiement d'une rubrique d'une classe pour une année dans la base de données
     * @param id
     * @return
     * @throws DAOException
     */
     public EcheancierPaiement readEcheancierPaiement(int id, int idClasse, int idAnnee) throws DAOException {
         try (Connection connection = DBManager.getConnection()) {
             String sql = "SELECT * FROM echeancierPaiement WHERE id_rubrique = ? AND id_classe = ? AND id_annee = ?";
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             preparedStatement.setInt(1, id);
             preparedStatement.setInt(2, idClasse);
             preparedStatement.setInt(3, idAnnee);
             ResultSet resultSet = preparedStatement.executeQuery();
             if (resultSet.next()) {
                 EcheancierPaiement echeancierPaiement = new EcheancierPaiement(
                         resultSet.getInt("id"),
                         resultSet.getDouble("montant"),
                         FXCollections.observableArrayList(readEcheances(resultSet.getInt("id")))
                 );
                 return echeancierPaiement;
             }
         } catch (DBHandlingException | SQLException e) {
             throw new DAOException(e.getMessage());
         }
         return null;
     }
}
