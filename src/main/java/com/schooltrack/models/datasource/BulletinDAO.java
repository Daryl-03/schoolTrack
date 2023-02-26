package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Bulletin;
import com.schooltrack.models.Note;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class BulletinDAO implements DAO<Bulletin> {
    
    @Override
    public void create(Bulletin bulletin) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "INSERT INTO bulletin (trimestre, moyenne, id_eleve, id_classe, id_annee) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bulletin.getTrimestre());
            preparedStatement.setFloat(2, bulletin.getMoyenne());
            preparedStatement.setInt(3, bulletin.getId_eleve());
            preparedStatement.setInt(4, bulletin.getId_classe());
            preparedStatement.setInt(5, bulletin.getId_annee());
            if (bulletin.getId_annee() == 0 || bulletin.getId_classe() == 0 || bulletin.getId_eleve() == 0) {
                throw new DAOException("L'élève, la classe ou l'année n'existe pas");
            }
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
    }
    
    @Override
    public Bulletin read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM bulletin WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                List<Note> notes = new NoteDAO().readAllByBulletin(id);
                Bulletin bulletin = new Bulletin(
                        resultSet.getInt("id"),
                        resultSet.getInt("trimestre"),
                        resultSet.getFloat("moyenne"),
                        notes != null ? FXCollections.observableArrayList(notes) : FXCollections.observableArrayList(),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_classe"),
                        resultSet.getInt("id_annee")
                );
                return bulletin;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return null;
    }
    
    @Override
    public void update(Bulletin bulletin) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE bulletin SET trimestre = ?, moyenne = ?, id_eleve = ?, id_classe = ?, id_annee = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bulletin.getTrimestre());
            preparedStatement.setFloat(2, bulletin.getMoyenne());
            preparedStatement.setInt(3, bulletin.getId_eleve());
            preparedStatement.setInt(4, bulletin.getId_classe());
            preparedStatement.setInt(5, bulletin.getId_annee());
            preparedStatement.setInt(6, bulletin.getId());
            if (bulletin.getId_annee() == 0 || bulletin.getId_classe() == 0 || bulletin.getId_eleve() == 0) {
                throw new DAOException("L'élève, la classe ou l'année n'existe pas");
            }
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "DELETE FROM bulletin WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
    }
    
    @Override
    public List<Bulletin> readAll() throws DAOException {
        List<Bulletin> bulletins = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM bulletin";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Note> notes = new NoteDAO().readAllByBulletin(resultSet.getInt("id"));
                Bulletin bulletin = new Bulletin(
                        resultSet.getInt("id"),
                        resultSet.getInt("trimestre"),
                        resultSet.getFloat("moyenne"),
                        notes != null ? FXCollections.observableArrayList(notes) : FXCollections.observableArrayList(),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_classe"),
                        resultSet.getInt("id_annee")
                );
                bulletins.add(bulletin);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return bulletins;
    }
    
    /**
     * lister les bulletins d'un élève par année
     */
    public List<Bulletin> readAllByYear(int id_eleve, int id_annee) throws DAOException {
        List<Bulletin> bulletins = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM bulletin WHERE id_eleve = ? AND id_annee = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_annee);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Note> notes = new NoteDAO().readAllByBulletin(resultSet.getInt("id"));
                Bulletin bulletin = new Bulletin(
                        resultSet.getInt("id"),
                        resultSet.getInt("trimestre"),
                        resultSet.getFloat("moyenne"),
                        notes != null ? FXCollections.observableArrayList(notes) : FXCollections.observableArrayList(),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_classe"),
                        resultSet.getInt("id_annee")
                );
                bulletins.add(bulletin);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return bulletins;
    }
    
    /**
     * lire un bulletin d'un eleve par année et par trimestre
     */
    public Bulletin read(int id_eleve, int id_annee, int trimestre) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM bulletin WHERE id_eleve = ? AND id_annee = ? AND trimestre = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_annee);
            preparedStatement.setInt(3, trimestre);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                List<Note> notes = new NoteDAO().readAllByBulletin(resultSet.getInt("id"));
                Bulletin bulletin = new Bulletin(
                        resultSet.getInt("id"),
                        resultSet.getInt("trimestre"),
                        resultSet.getFloat("moyenne"),
                        notes != null ? FXCollections.observableArrayList(notes) : FXCollections.observableArrayList(),
                        resultSet.getInt("id_eleve"),
                        resultSet.getInt("id_classe"),
                        resultSet.getInt("id_annee")
                );
                return bulletin;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return null;
    }
    
    /**
     * génération des bulletins d'un élève par année s'ils n'existent pas
     *
     * @param id_eleve
     * @param id_annee
     * @throws DAOException
     */
    public void generate(int id_eleve, int id_annee) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM bulletin WHERE id_eleve = ? AND id_annee = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_eleve);
            preparedStatement.setInt(2, id_annee);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                for (int i = 1; i <= 3; i++) {
                    String sql2 = "INSERT INTO bulletin (trimestre, moyenne, id_eleve, id_classe, id_annee) VALUES (?, ?, ?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sql2);
                    preparedStatement.setInt(1, i);
                    preparedStatement.setFloat(2, 0);
                    preparedStatement.setInt(3, id_eleve);
                    preparedStatement.setInt(4, new EleveDAO().read(id_eleve).getId_classe());
                    preparedStatement.setInt(5, id_annee);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
    }
}
