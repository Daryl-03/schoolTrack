package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Paiement;
import com.schooltrack.utils.Constantes;
import javafx.collections.FXCollections;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class PaiementDAO implements DAO<Paiement> {
    
    /**
     * insère un nouveau paiement dans la base de données
     * @param paiement
     * @throws DAOException
     */
    @Override
    public void create(Paiement paiement) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            paiement.setNumero(generateNumero());
            String sql = "INSERT INTO paiement(numero, montant, observation, date, id_rubrique, id_eleve, id_annee) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, paiement.getNumero());
            preparedStatement.setDouble(2, paiement.getMontant());
            preparedStatement.setString(3, paiement.getObservation());
            preparedStatement.setDate(4, Date.valueOf(paiement.getDate()));
            preparedStatement.setInt(5, paiement.getId_rubrique());
            preparedStatement.setInt(6, paiement.getId_eleve());
            preparedStatement.setInt(7, paiement.getId_annee());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
    }
    
    /**
     * générer un numéro de paiement
     * @return String numero
     * @throws DAOException
     */
    public String generateNumero() throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT MAX(id) AS id FROM paiement";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                return String.format("%06d", id + 3);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return null;
    }

    /**
     * Lecture d'un paiement dans la base de données à partir de son id
     * @param id
     * @return
     * @throws DAOException
     */
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
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                return paiement;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return null;
    }
    
    /**
     * Mise à jour d'un paiement dans la base de données
     * @param paiement
     * @throws DAOException
     */
    @Override
    public void update(Paiement paiement) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE paiement SET numero = ?, montant = ?, date = ?, observation = ?, id_rubrique = ?, id_eleve = ?, id_annee = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, paiement.getNumero());
            preparedStatement.setDouble(2, paiement.getMontant());
            preparedStatement.setDate(3, Date.valueOf(paiement.getDate()));
            preparedStatement.setString(4, paiement.getObservation());
            preparedStatement.setInt(5, paiement.getId_rubrique());
            preparedStatement.setInt(6, paiement.getId_eleve());
            preparedStatement.setInt(7, paiement.getId_annee());
            preparedStatement.setInt(8, paiement.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "DELETE FROM paiement WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
    }

    /**
     * retourne la liste de tous les paiements
     * @return
     * @throws DAOException
     */
    @Override
    public List<Paiement> readAll() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_annee = ?";
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Constantes.CURRENT_YEAR.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements d'un élève pour une année donnée
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByEleveAndAnnee(int id_eleve, int id_annee) throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_eleve = ? AND id_annee = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_annee);
                
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements d'un élève pour une année donnée selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByEleveAndAnneeAndRubrique(int id_eleve, int id_annee, int id_rubrique) throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_eleve = ? AND id_annee = ? AND id_rubrique = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_annee);
            preparedStatement.setInt(3, id_rubrique);
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements journaliers d'un élève selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByEleveAndRubriqueAndDate(int id_eleve, int id_rubrique) throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_eleve = ? AND id_rubrique = ? AND date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_rubrique);
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements hebdomadaires d'un élève selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByEleveAndRubriqueAndWeek(int id_eleve, int id_rubrique) throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_eleve = ? AND id_rubrique = ? AND date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_rubrique);
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now().minusDays(7)));
            preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements mensuels d'un élève selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByEleveAndRubriqueAndMonth(int id_eleve, int id_rubrique) throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_eleve = ? AND id_rubrique = ? AND date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_rubrique);
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now().minusDays(30)));
            preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements journaliers selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByRubriqueAndDate(int id_rubrique) throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_rubrique = ? AND date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_rubrique);
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements hebdomadaires selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByRubriqueAndWeek(int id_rubrique) throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_rubrique = ? AND date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_rubrique);
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now().minusDays(7)));
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements mensuels selon la rubrique
     * @return
     * @throws DAOException
     */
    public List<Paiement> readAllByRubriqueAndMonth(int id_rubrique) throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_rubrique = ? AND date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_rubrique);
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now().minusDays(30)));
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements journaliers
     * @return  List<Paiement>
     * @throws DAOException
     */
    public List<Paiement> readAllByDate() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements hebdomadaires
     * @return  List<Paiement>
     * @throws DAOException
     */
    public List<Paiement> readAllByWeek() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now().minusDays(7)));
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements mensuels
     * @return  List<Paiement>
     * @throws DAOException
     */
    public List<Paiement> readAllByMonth() throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now().minusDays(30)));
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements journaliers pour toutes les rubriques d'intitulé donné
     * @param intitule
     * @return  List<Paiement>
     * @throws DAOException
     */
    public List<Paiement> readAllByDateAndRubrique(String intitule) throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE date = ? AND id_rubrique IN (SELECT id_rubriqueClasse FROM rubrique JOIN rubriqueclasse ON rubrique.id = rubriqueclasse.id_rubrique WHERE intitule = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(2, intitule);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements hebdomadaires pour toutes les rubriques d'intitulé donné
     * @param intitule
     * @return  List<Paiement>
     * @throws DAOException
     */
    public List<Paiement> readAllByWeekAndRubrique(String intitule) throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE date BETWEEN ? AND ? AND id_rubrique IN (SELECT id_rubriqueClasse FROM rubrique JOIN rubriqueclasse ON rubrique.id = rubriqueclasse.id_rubrique WHERE intitule = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now().minusDays(7)));
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(3, intitule);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements mensuels pour toutes les rubriques d'intitulé donné
     * @param intitule
     * @return  List<Paiement>
     * @throws DAOException
     */
    public List<Paiement> readAllByMonthAndRubrique(String intitule) throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE date BETWEEN ? AND ? AND id_rubrique IN (SELECT id_rubriqueClasse FROM rubrique JOIN rubriqueclasse ON rubrique.id = rubriqueclasse.id_rubrique WHERE intitule = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now().minusDays(30)));
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(3, intitule);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return paiements;
    }
    
    /**
     * retourne la liste des paiements pour toutes les rubriques d'intitulé donné
     * @param intitule
     * @return  List<Paiement>
     * @throws DAOException
     */
    public List<Paiement> readAllByRubrique(String intitule) throws DAOException {
        List<Paiement> paiements = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM paiement WHERE id_rubrique IN (SELECT id_rubriqueClasse FROM rubrique JOIN rubriqueclasse ON rubrique.id = rubriqueclasse.id_rubrique WHERE intitule = ?) AND id_annee = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, intitule);
            preparedStatement.setInt(2, Constantes.CURRENT_YEAR.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement(
                        resultSet.getInt("id"),
                        resultSet.getString("numero"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("observation"),
                        resultSet.getDouble("montant"),
                        resultSet.getInt("id_rubrique"),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_annee")
                );
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return paiements;
    }
}

