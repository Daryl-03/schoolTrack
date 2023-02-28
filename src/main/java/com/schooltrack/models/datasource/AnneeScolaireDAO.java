package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.AnneeScolaire;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.List;

public class AnneeScolaireDAO implements DAO<AnneeScolaire> {
    
    /**
     *
     * @return le dernier id de la table anneeScolaire
     * @throws DAOException
     */
      public int readLastId() throws DAOException {
          try (Connection connection = DBManager.getConnection()) {
              String sql = "SELECT MAX(id) AS id FROM anneeScolaire";
              PreparedStatement preparedStatement = connection.prepareStatement(sql);
              ResultSet resultSet = preparedStatement.executeQuery();
              if (resultSet.next()) {
                  return resultSet.getInt("id");
              }
          } catch (DBHandlingException | SQLException e) {
              throw new DAOException(e.getMessage(),e.getCause());
          }
          return 0;
      }
    
    @Override
    public void create(AnneeScolaire anneeScolaire) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "INSERT INTO anneeScolaire (intitule, date_debut, date_fin) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, anneeScolaire.getIntitule());
            preparedStatement.setDate(2, Date.valueOf(anneeScolaire.getDateDebut()));
            preparedStatement.setDate(3, Date.valueOf(anneeScolaire.getDateFin()));
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        
    }
    
    @Override
    public AnneeScolaire read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM anneeScolaire WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                AnneeScolaire anneeScolaire = new AnneeScolaire();
                anneeScolaire.setId(resultSet.getInt("id"));
                anneeScolaire.setIntitule(resultSet.getString("intitule"));
                if(resultSet.getDate("date_debut") != null)
                    anneeScolaire.setDateDebut(resultSet.getDate("date_debut").toLocalDate());
                if(resultSet.getDate("date_fin") != null)
                    anneeScolaire.setDateFin(resultSet.getDate("date_fin").toLocalDate());
                return anneeScolaire;
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return null;
    }
    
    @Override
    public void update(AnneeScolaire anneeScolaire) throws DAOException {
    
    }
    
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    @Override
    public List<AnneeScolaire> readAll() throws DAOException {
        List<AnneeScolaire> anneeScolaires = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM anneeScolaire";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AnneeScolaire anneeScolaire = new AnneeScolaire();
                anneeScolaire.setId(resultSet.getInt("id"));
                anneeScolaire.setIntitule(resultSet.getString("intitule"));
                if(resultSet.getDate("date_debut") != null)
                    anneeScolaire.setDateDebut(resultSet.getDate("date_debut").toLocalDate());
                if(resultSet.getDate("date_fin") != null)
                    anneeScolaire.setDateFin(resultSet.getDate("date_fin").toLocalDate());
                anneeScolaires.add(anneeScolaire);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return anneeScolaires;
    }
    
    public List<String> readAllIntitules() throws DAOException {
        List<String> intitules = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT intitule FROM anneeScolaire";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                intitules.add(resultSet.getString("intitule"));
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return intitules;
    }
    
    /**
     * retourne l'annee scolaire dont l'intitule est passé en parametre
     * @param intitule
     * @return AnneeScolaire
     */
    public AnneeScolaire readByIntitule(String intitule) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM anneeScolaire WHERE intitule = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, intitule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                AnneeScolaire anneeScolaire = new AnneeScolaire();
                anneeScolaire.setId(resultSet.getInt("id"));
                anneeScolaire.setIntitule(resultSet.getString("intitule"));
                if (resultSet.getDate("date_debut") != null)
                    anneeScolaire.setDateDebut(resultSet.getDate("date_debut").toLocalDate());
                if (resultSet.getDate("date_fin") != null)
                    anneeScolaire.setDateFin(resultSet.getDate("date_fin").toLocalDate());
                return anneeScolaire;
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return null;
    }
    
    /**
     * retourne la derniere annee scolaire
     * @return AnneeScolaire
     */
    public AnneeScolaire readLast() throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM anneeScolaire WHERE id = (SELECT MAX(id) FROM anneeScolaire)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                AnneeScolaire anneeScolaire = new AnneeScolaire();
                anneeScolaire.setId(resultSet.getInt("id"));
                anneeScolaire.setIntitule(resultSet.getString("intitule"));
                if (resultSet.getDate("date_debut") != null)
                    anneeScolaire.setDateDebut(resultSet.getDate("date_debut").toLocalDate());
                if (resultSet.getDate("date_fin") != null)
                    anneeScolaire.setDateFin(resultSet.getDate("date_fin").toLocalDate());
                return anneeScolaire;
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return null;
    }
}
