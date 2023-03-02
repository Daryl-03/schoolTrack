package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Rubrique;
import com.schooltrack.utils.Constantes;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class RubriqueDAO implements DAO<Rubrique>{
    /**
     * Insertion d'une rubrique dans la base de données
     * @param rubrique
     * @throws DAOException
     */
    @Override
    public void create(Rubrique rubrique) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            // Insertion de la rubrique
            String sql = "INSERT INTO rubrique (intitule) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, rubrique.getIntitule());
            preparedStatement.executeUpdate();
            String sql2  = "INSERT INTO rubriqueClasse (id_rubrique, id_classe, montant, description) VALUES (?, ?, ?, ?)";
            
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setInt(1, rubrique.getId());
            preparedStatement2.setInt(2, rubrique.getId_classe());
            preparedStatement2.setDouble(3, rubrique.getMontant());
            preparedStatement2.setString(4, rubrique.getDescription());
            preparedStatement2.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
    }
    
    /**
     * Lecture d'une rubrique dans la base de données
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public Rubrique read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM rubrique JOIN rubriqueClasse ON rubrique.id = rubriqueClasse.id_rubrique WHERE rubriqueClasse.id_rubriqueClasse = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Rubrique rubrique = new Rubrique(
                        resultSet.getInt("id_rubriqueClasse"),
                        resultSet.getDouble("montant"),
                        resultSet.getString("intitule"),
                        resultSet.getString("description"),
                        new EcheancierPaiementDAO().readEcheancierPaiement(resultSet.getInt("id_rubriqueClasse"), resultSet.getInt("id_classe"), Constantes.CURRENT_YEAR.getId()),
                        resultSet.getInt("id_classe")
                );
                return rubrique;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return null;
    }
    
    /**
     * Mets à jour une rubrique dans la base de données
     * @param rubrique
     * @throws DAOException
     */
    @Override
    public void update(Rubrique rubrique) throws DAOException {
    
    }
    
    /**
     * Supprime une rubrique dans la base de données
     * @param id
     * @throws DAOException
     */
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    /**
     * Lecture de toutes les rubriques dans la base de données
     * @return
     * @throws DAOException
     */
    @Override
    public List<Rubrique> readAll() throws DAOException {
        List<Rubrique> rubriques = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM rubrique JOIN rubriqueClasse ON rubrique.id = rubriqueClasse.id_rubrique";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Rubrique rubrique = new Rubrique(
                        resultSet.getInt("id_rubriqueClasse"),
                        resultSet.getDouble("montant"),
                        resultSet.getString("intitule"),
                        resultSet.getString("description"),
                        new EcheancierPaiementDAO().readEcheancierPaiement(resultSet.getInt("id_rubriqueClasse"), resultSet.getInt("id_classe"), Constantes.CURRENT_YEAR.getId()),
                        resultSet.getInt("id_classe")
                );
                rubriques.add(rubrique);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return rubriques;
    }
    
    /**
     * Lecture de toutes les rubriques d'une classe dans la base de données
     * @param id_classe
     * @return
     * @throws DAOException
     */
    public List<Rubrique> readAllByClasse(int id_classe) throws DAOException {
        List<Rubrique> rubriques = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM rubrique JOIN rubriqueClasse ON rubrique.id = rubriqueClasse.id_rubrique WHERE rubriqueClasse.id_classe = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_classe);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Rubrique rubrique = new Rubrique(
                        resultSet.getInt("id_rubriqueClasse"),
                        resultSet.getDouble("montant"),
                        resultSet.getString("intitule"),
                        resultSet.getString("description"),
                        new EcheancierPaiementDAO().readEcheancierPaiement(resultSet.getInt("id_rubriqueClasse"), resultSet.getInt("id_classe"), Constantes.CURRENT_YEAR.getId()),
                        resultSet.getInt("id_classe")
                );
                rubriques.add(rubrique);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return rubriques;
    }
    
    /**
     * Lecture de toutes les rubriques selon l'intitulé dans la base de données
     * @param intitule
     * @return List<Rubrique>
     * @throws DAOException
     */
    public List<Rubrique> readAllByIntitule(String intitule) throws DAOException {
        List<Rubrique> rubriques = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM rubrique JOIN rubriqueClasse ON rubrique.id = rubriqueClasse.id_rubrique WHERE rubrique.intitule = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, intitule);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Rubrique rubrique = new Rubrique(
                        resultSet.getInt("id_rubriqueClasse"),
                        resultSet.getDouble("montant"),
                        resultSet.getString("intitule"),
                        resultSet.getString("description"),
                        new EcheancierPaiementDAO().readEcheancierPaiement(resultSet.getInt("id_rubriqueClasse"), resultSet.getInt("id_classe"), Constantes.CURRENT_YEAR.getId()),
                        resultSet.getInt("id_classe")
                );
                rubriques.add(rubrique);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return rubriques;
    }
    
    /**
     * Lecture de tous les intitulés des rubriques dans la base de données
     * @return List<String>
     * @throws DAOException
     */
    public List<String> readAllIntitule() throws DAOException {
        List<String> intitules = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT intitule FROM rubrique";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                intitules.add(resultSet.getString("intitule"));
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return intitules;
    }
    
    /**
     * Lecture d'une rubrique selon l'intitulé et le matricule d'un eleve dans la base de données
     * @param intitule
     * @param matricule
     * @return Rubrique
     * @throws DAOException
     */
    public Rubrique readByIntituleAndMatricule(String intitule, String matricule) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM rubrique JOIN rubriqueClasse ON rubrique.id = rubriqueClasse.id_rubrique JOIN classe ON rubriqueClasse.id_classe = classe.id JOIN eleve ON classe.id = eleve.id_classe WHERE rubrique.intitule = ? AND eleve.matricule = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, intitule);
            preparedStatement.setString(2, matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Rubrique rubrique = new Rubrique(
                        resultSet.getInt("id_rubriqueClasse"),
                        resultSet.getDouble("montant"),
                        resultSet.getString("intitule"),
                        resultSet.getString("description"),
                        new EcheancierPaiementDAO().readEcheancierPaiement(resultSet.getInt("id_rubriqueClasse"), resultSet.getInt("id_classe"), Constantes.CURRENT_YEAR.getId()),
                        resultSet.getInt("id_classe")
                );
                return rubrique;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return null;
    }
    
    /**
     * récupération de la rubrique Scolarité à partir de l'id de la classe
     * @param id_classe
     * @return Rubrique
     * @throws DAOException
     */
    public Rubrique readScolariteByClasse(int id_classe) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM rubrique JOIN rubriqueClasse ON rubrique.id = rubriqueClasse.id_rubrique WHERE rubrique.intitule = ? AND rubriqueClasse.id_classe = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Scolarité");
            preparedStatement.setInt(2, id_classe);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Rubrique rubrique = new Rubrique(
                        resultSet.getInt("id_rubriqueClasse"),
                        resultSet.getDouble("montant"),
                        resultSet.getString("intitule"),
                        resultSet.getString("description"),
                        new EcheancierPaiementDAO().readEcheancierPaiement(resultSet.getInt("id_rubriqueClasse"), resultSet.getInt("id_classe"), Constantes.CURRENT_YEAR.getId()),
                        resultSet.getInt("id_classe")
                );
                return rubrique;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return null;
    }
}
