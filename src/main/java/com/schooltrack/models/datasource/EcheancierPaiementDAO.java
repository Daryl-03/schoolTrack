package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.EcheancierPaiement;
import com.schooltrack.models.Rubrique;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EcheancierPaiementDAO implements DAO<EcheancierPaiement>{
    
    /**
     * insère un nouvel échéancier de paiement dans la base de données
     * @param echeancierPaiement
     * @throws DAOException
     */
    @Override
    public void create(EcheancierPaiement echeancierPaiement) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            // Insertion de l'échéancier
            String sql = "INSERT INTO echeancier (id_classe, id_rubrique, id_annee) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, echeancierPaiement.getId_classe());
            preparedStatement.setInt(2, echeancierPaiement.getId_rubrique());
            preparedStatement.setInt(3, echeancierPaiement.getId_annee());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                echeancierPaiement.setId(resultSet.getInt(1));
            }
            // Insertion des échéances
            String sql2 = "INSERT INTO echeance (id_echeancier, numero, date_echeance) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            if (echeancierPaiement.getEcheances() != null){
                for (LocalDate date : echeancierPaiement.getEcheances()) {
                    preparedStatement2.setInt(1, echeancierPaiement.getId());
                    preparedStatement2.setInt(2, echeancierPaiement.getEcheances().indexOf(date) + 1);
                    preparedStatement2.setDate(3, java.sql.Date.valueOf(date));
                    preparedStatement2.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
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
                List<LocalDate> echeances = readEcheances(id);
                EcheancierPaiement echeancierPaiement = new EcheancierPaiement(
                        resultSet.getInt("id"),
                        echeances==null?FXCollections.observableArrayList():FXCollections.observableArrayList(echeances),
                        resultSet.getInt("id_classe"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_annee")
                );
                return echeancierPaiement;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
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
            String sql = "UPDATE echeancier SET id_classe = ?, id_rubrique = ?, id_annee = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, echeancierPaiement.getId_classe());
            preparedStatement.setInt(2, echeancierPaiement.getId_rubrique());
            preparedStatement.setInt(3, echeancierPaiement.getId_annee());
            preparedStatement.setInt(4, echeancierPaiement.getId());
            preparedStatement.executeUpdate();
            for (LocalDate echeance: echeancierPaiement.getEcheances()) {
                String sql2 = "UPDATE echeance SET date_echeance = ? WHERE id_echeancier = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, echeancierPaiement.getId());
                preparedStatement2.setDate(2, java.sql.Date.valueOf(echeance));
                preparedStatement2.executeUpdate();
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
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
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
    }
    
    /**
     * Lit tous les échéanciers de paiement de l'anée scolaire courante dans la base de données
     * @return
     * @throws DAOException
     */
    @Override
    public List<EcheancierPaiement> readAll() throws DAOException {
        List<EcheancierPaiement> echeancierPaiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM echeancier WHERE id_annee = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, new AnneeScolaireDAO().readLastId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<LocalDate> echeances = readEcheances(resultSet.getInt("id"));
                EcheancierPaiement echeancierPaiement = new EcheancierPaiement(
                        resultSet.getInt("id"),
                        echeances==null?FXCollections.observableArrayList():FXCollections.observableArrayList(echeances),
                        resultSet.getInt("id_classe"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_annee")
                );
                echeancierPaiements.add(echeancierPaiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
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
                echeances.add(resultSet.getDate("date_echeance").toLocalDate());
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return echeances;
    }
    
    /**
     * Lit l'échéancier de paiement d'une rubrique d'une classe pour une année dans la base de données
     * @param id_rubrique, idClasse, idAnnee
     * @return
     * @throws DAOException
     */
     public EcheancierPaiement readEcheancierPaiement(int id_rubrique, int idClasse, int idAnnee) throws DAOException {
         try (Connection connection = DBManager.getConnection()) {
             String sql = "SELECT * FROM echeancier WHERE id_rubrique = ? AND id_classe = ? AND id_annee = ?";
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             preparedStatement.setInt(1, id_rubrique);
             preparedStatement.setInt(2, idClasse);
             preparedStatement.setInt(3, idAnnee);
             ResultSet resultSet = preparedStatement.executeQuery();
             if (resultSet.next()) {
                List<LocalDate> echeances = readEcheances(resultSet.getInt("id"));
                 EcheancierPaiement echeancierPaiement = new EcheancierPaiement(
                         resultSet.getInt("id"),
                         echeances==null?FXCollections.observableArrayList():FXCollections.observableArrayList(echeances),
                        resultSet.getInt("id_classe"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_annee")
                 );
                 return echeancierPaiement;
             }
         } catch (DBHandlingException | SQLException e) {
             throw new DAOException(e.getMessage(),e.getCause());
         }
         return null;
     }
    
    /**
     * Crée un échéancier de paiement avec 9 échéances pour chaque mois de l'année scolaire pour la rubrique de chaque classe nommée "Scolarité"
     * @throws DAOException
     */
    public void createEcheancierPaiementScolarite() throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM classe";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idClasse = resultSet.getInt("id");
                int idAnnee = new AnneeScolaireDAO().readLastId();
                List<Rubrique> rubriques = new RubriqueDAO().readAllByClasse(idClasse);
                for (Rubrique rubrique : rubriques) {
                    if (rubrique.getIntitule().equals("Scolarité")) {
                        int idRubrique = rubrique.getId();
                        EcheancierPaiement echeancierPaiement = readEcheancierPaiement(idRubrique, idClasse, idAnnee);
                        if (echeancierPaiement == null) {
                            echeancierPaiement = new EcheancierPaiement();
                            echeancierPaiement.setId_classe(idClasse);
                            echeancierPaiement.setId_rubrique(idRubrique);
                            echeancierPaiement.setId_annee(idAnnee);
                            // création de 9 échéances pour chaque mois de l'année scolaire, de la date de début de l'année scolaire à 10 mois après
                            LocalDate dateDebut = new AnneeScolaireDAO().readLast().getDateDebut().plusMonths(1);
                            LocalDate dateFin = dateDebut.plusMonths(9);
                            List<LocalDate> echeances = new ArrayList<>();
                            for (LocalDate date = dateDebut; date.isBefore(dateFin); date = date.plusMonths(1)) {
                                echeances.add(date);
                            }
                            echeancierPaiement.setEcheances(FXCollections.observableArrayList(echeances));
                            create(echeancierPaiement);
                        }
                    }
                }
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
    }
}
