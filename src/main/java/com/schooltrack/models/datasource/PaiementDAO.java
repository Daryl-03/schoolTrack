package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Paiement;
import javafx.collections.FXCollections;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class PaiementDAO implements DAO<Paiement> {
    private int id_rubrique;
    private int id_eleve;
    private int id_annee;
    
    public int getId_eleve() {
        return id_eleve;
    }
    
    public void setId_eleve(int id_eleve) {
        this.id_eleve = id_eleve;
    }
    
    public int getId_rubrique() {
        return id_rubrique;
    }
    
    public void setId_rubrique(int id_rubrique) {
        this.id_rubrique = id_rubrique;
    }
    
    public int getId_annee() {
        return id_annee;
    }
    
    public void setId_annee(int id_annee) {
        this.id_annee = id_annee;
    }
    
    /**
     * insère un nouveau paiement dans la base de données
     * @param paiement
     * @throws DAOException
     */
    @Override
    public void create(Paiement paiement) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "INSERT INTO paiement(id, montant, date, id_rubrique, id_eleve, id_annee) VALUES (NULL, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, paiement.getMontant());
            preparedStatement.setDate(2, Date.valueOf(paiement.getDate()));
            if(id_annee == 0 || id_eleve == 0 || id_rubrique == 0)
                throw new DAOException("L'année ou l'élève n'est pas défini");
            preparedStatement.setInt(3, id_rubrique);
            preparedStatement.setInt(4, id_eleve);
            preparedStatement.setInt(5, id_annee);
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public Paiement read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        new RubriqueDAO().read(resultSet.getInt("id_rubrique"))
                );
                return paiement;
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Paiement paiement) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE paiement SET montant = ?, date = ?, observation = ?, id_rubrique = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, paiement.getMontant());
            preparedStatement.setDate(2, Date.valueOf(paiement.getDate()));
            preparedStatement.setString(3, paiement.getObservation());
            preparedStatement.setInt(4, paiement.getRubrique().getId());
            preparedStatement.setInt(5, paiement.getId());
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "DELETE FROM paiement WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Paiement> readAll() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        new RubriqueDAO().read(resultSet.getInt("id_rubrique"))
                );
                paiements.add(paiement);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements d'un élève pour une année donnée
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByEleveAndAnnee() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_eleve = ? AND id_annee = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_annee);
            if(id_annee == 0 || id_eleve == 0)
                throw new DAOException("L'année ou l'élève n'est pas défini");
                
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        new RubriqueDAO().read(resultSet.getInt("id_rubrique"))
                );
                paiements.add(paiement);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements d'un élève pour une année donnée selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByEleveAndAnneeAndRubrique() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_eleve = ? AND id_annee = ? AND id_rubrique = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_annee);
            preparedStatement.setInt(3, id_rubrique);
            if (id_annee == 0 || id_eleve == 0 || id_rubrique == 0)
                throw new DAOException("L'année ou l'élève ou la rubrique n'est pas défini");
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        new RubriqueDAO().read(resultSet.getInt("id_rubrique"))
                );
                paiements.add(paiement);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements journaliers d'un élève selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByEleveAndRubriqueAndDate() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_eleve = ? AND id_rubrique = ? AND date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_rubrique);
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
            if (id_eleve == 0 || id_rubrique == 0)
                throw new DAOException("L'élève ou la rubrique ou la date n'est pas défini");
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        new RubriqueDAO().read(resultSet.getInt("id_rubrique"))
                );
                paiements.add(paiement);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements hebdomadaires d'un élève selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByEleveAndRubriqueAndWeek() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_eleve = ? AND id_rubrique = ? AND date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_rubrique);
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now().minusDays(7)));
            preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
            if (id_eleve == 0 || id_rubrique == 0)
                throw new DAOException("L'élève ou la rubrique ou la date n'est pas défini");
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        new RubriqueDAO().read(resultSet.getInt("id_rubrique"))
                );
                paiements.add(paiement);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements mensuels d'un élève selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByEleveAndRubriqueAndMonth() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_eleve = ? AND id_rubrique = ? AND date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_rubrique);
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now().minusDays(30)));
            preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
            if (id_eleve == 0 || id_rubrique == 0)
                throw new DAOException("L'élève ou la rubrique ou la date n'est pas défini");
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        new RubriqueDAO().read(resultSet.getInt("id_rubrique"))
                );
                paiements.add(paiement);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements journaliers selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByRubriqueAndDate() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_rubrique = ? AND date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_rubrique);
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
            if (id_rubrique == 0)
                throw new DAOException("La rubrique ou la date n'est pas défini");
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        new RubriqueDAO().read(resultSet.getInt("id_rubrique"))
                );
                paiements.add(paiement);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements hebdomadaires selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByRubriqueAndWeek() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_rubrique = ? AND date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_rubrique);
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now().minusDays(7)));
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
            if (id_rubrique == 0)
                throw new DAOException("La rubrique ou la date n'est pas défini");
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        new RubriqueDAO().read(resultSet.getInt("id_rubrique"))
                );
                paiements.add(paiement);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements mensuels selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByRubriqueAndMonth() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_rubrique = ? AND date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_rubrique);
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now().minusDays(30)));
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
            if (id_rubrique == 0)
                throw new DAOException("La rubrique ou la date n'est pas défini");
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        new RubriqueDAO().read(resultSet.getInt("id_rubrique"))
                );
                paiements.add(paiement);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return paiements;
    }
}

