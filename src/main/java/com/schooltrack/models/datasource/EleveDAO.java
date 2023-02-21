package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Eleve;
import com.schooltrack.models.Paiement;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EleveDAO implements DAO<Eleve>{
    /**
     * Crée un nouvel élève dans la base de données
     * @param eleve
     */
    @Override
    public void create(Eleve eleve) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            // Insertion de l'élève
            String sql = "INSERT INTO eleve (nom, prenom, adresse, dtNaiss, lieuNaiss, numTel, email, sexe, id_classe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, eleve.getNom());
            preparedStatement.setString(2, eleve.getPrenom());
            preparedStatement.setString(3, eleve.getAdresse());
            preparedStatement.setDate(4, java.sql.Date.valueOf(eleve.getDateDeNaissance()));
            preparedStatement.setString(5, eleve.getLieuDeNaissance());
            preparedStatement.setString(6, eleve.getNumtelephone());
            preparedStatement.setString(7, eleve.getEmail());
            preparedStatement.setString(8, eleve.getSexe());
            preparedStatement.setInt(9, eleve.getId_classe());
            preparedStatement.executeUpdate();
            inscription(eleve);
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * Inscription d'un élève dans une classe
     * @param eleve
     */
    public void inscription(Eleve eleve) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            // Insertion de l'élève dans la table inscription
            String sql = "INSERT INTO inscription (id_eleve, id_classe, id_annee, dateInscription) VALUES (?, ?,(SELECT id FROM anneescolaire ODER BY id DESC LIMIT 1), ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, eleve.getId());
            preparedStatement.setInt(2, eleve.getId_classe());
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * Retourne la liste de tous les élèves
     * @return
     */
    public void modifierInscription(Eleve eleve) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            // Insertion de l'élève dans la table inscription
            String sql = "UPDATE inscription SET id_classe = ? WHERE id_eleve = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, eleve.getId_classe());
            preparedStatement.setInt(2, eleve.getId());
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * Retourne un élève à partir de son id
     * @param id
     * @return Eleve ou null si l'élève n'existe pas
     */
    @Override
    public Eleve read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM eleve WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                return new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("dtNaiss").toLocalDate(),
                        resultSet.getString("lieuNaiss"),
                        resultSet.getString("numTel"),
                        resultSet.getString("email"),
                        resultSet.getString("sexe"),
                        FXCollections.observableArrayList( new PaiementDAO().readAllByEleveAndAnnee(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId()) ),
                        FXCollections.observableArrayList(new BulletinDAO().readAll(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId())),
                        resultSet.getInt("id_classe")
                );
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }
    
    /**
     * Retourne la liste de tous les élèves
     * @return
     */
    @Override
    public void update(Eleve eleve) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE eleve SET nom = ?, prenom = ?, adresse = ?, dtNaiss = ?, lieuNaiss = ?, numTel = ?, email = ?, sexe = ?, id_classe = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, eleve.getNom());
            preparedStatement.setString(2, eleve.getPrenom());
            preparedStatement.setString(3, eleve.getAdresse());
            preparedStatement.setDate(4, java.sql.Date.valueOf(eleve.getDateDeNaissance()));
            preparedStatement.setString(5, eleve.getLieuDeNaissance());
            preparedStatement.setString(6, eleve.getNumtelephone());
            preparedStatement.setString(7, eleve.getEmail());
            preparedStatement.setString(8, eleve.getSexe());
            preparedStatement.setInt(9, eleve.getId_classe());
            preparedStatement.setInt(10, eleve.getId());
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * Supprime un élève de la base de données
     * @param id
     */
    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "DELETE FROM eleve WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    /**
     * Retourne la liste de tous les élèves
     * @return List<Eleve> eleves ou null si aucun élève n'existe
     */
    @Override
    public List<Eleve> readAll() throws DAOException {
        List<Eleve> eleves = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM eleve";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                
                eleves.add(new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("dtNaiss").toLocalDate(),
                        resultSet.getString("lieuNaiss"),
                        resultSet.getString("numTel"),
                        resultSet.getString("email"),
                        resultSet.getString("sexe"),
                        FXCollections.observableArrayList(new PaiementDAO().readAllByEleveAndAnnee(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId())),
                        FXCollections.observableArrayList(new BulletinDAO().readAll(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId())),
                        resultSet.getInt("id_classe")
                ));
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return eleves;
    }
    
    /**
     * Retourne la liste de tous les élèves d'une classe
     * @return List<Eleve> eleves ou null si aucun élève n'existe
     */
    public List<Eleve> readAllByClasse(int id_classe, int id_annee) throws DAOException {
        List<Eleve> eleves = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM eleve JOIN inscription ON eleve.id = inscription.id_eleve WHERE inscription.id_classe = ? AND inscription.id_annee = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_classe);
            preparedStatement.setInt(2, id_annee);
            if(id_classe == 0 || id_annee == 0 )
                throw new DAOException("La classe ou l'année n'est pas définie");
            ResultSet resultSet = preparedStatement.executeQuery();
            PaiementDAO paiementDAO = new PaiementDAO();
            while (resultSet.next()) {
                eleves.add(new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("dtNaiss").toLocalDate(),
                        resultSet.getString("lieuNaiss"),
                        resultSet.getString("numTel"),
                        resultSet.getString("email"),
                        resultSet.getString("sexe"),
                        FXCollections.observableArrayList(paiementDAO.readAllByEleveAndAnnee(resultSet.getInt("id"), id_annee)),
                        FXCollections.observableArrayList(new BulletinDAO().readAll(resultSet.getInt("id"), id_annee)),
                        resultSet.getInt("id_classe")
                ));
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return eleves;
    }
}
