package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Classe;
import com.schooltrack.models.Section;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SectionDAO implements DAO<Section>{
    @Override
    public void create(Section section) throws DAOException {
    
    }
    
    @Override
    public Section read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM section WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                List<Classe> classes = new ClasseDAO().readAll(resultSet.getInt("id"));
                Section section = new Section(
                        resultSet.getInt("id"),
                        resultSet.getString("intitule"),
                        classes!=null ? FXCollections.observableArrayList(classes) : FXCollections.observableArrayList()
                );
                return section;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return null;
    }
    
    @Override
    public void update(Section section) throws DAOException {
    
    }
    
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    @Override
    public List<Section> readAll() throws DAOException {
        List<Section> sections = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM section";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Classe> classes = new ClasseDAO().readAll(resultSet.getInt("id"));
                Section section = new Section(
                        resultSet.getInt("id"),
                        resultSet.getString("intitule"),
                        classes!=null ? FXCollections.observableArrayList(classes) : FXCollections.observableArrayList()
                );
                sections.add(section);
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return sections;
    }
    
    /**
     * Retourne toutes les sections en fonction de l'année scolaire
     * @param id_annee
     * @return List<Section> sections
     * @throws DAOException
     */
    public List<Section> readAll(int id_annee) throws DAOException {
        List<Section> sections = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM section";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Classe> classes = new ClasseDAO().readAll(resultSet.getInt("id"), id_annee);
                Section section = new Section(
                        resultSet.getInt("id"),
                        resultSet.getString("intitule"),
                        classes != null ? FXCollections.observableArrayList(classes) : FXCollections.observableArrayList()
                );
                sections.add(section);
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return sections;
    }
    
    /**
     * Retourne toutes les sections en fonction de l'année scolaire. Les classes ne contiendront pas les élèves. Version optimisée
     * @param id_annee
     * @return List<Section> sections
     * @throws DAOException
     */
    public List<Section> readAllWithoutStudents(int id_annee) throws DAOException {
        List<Section> sections = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM section";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Classe> classes = new ClasseDAO().readAllWithoutEleves(resultSet.getInt("id"));
                Section section = new Section(
                        resultSet.getInt("id"),
                        resultSet.getString("intitule"),
                        classes != null ? FXCollections.observableArrayList(classes) : FXCollections.observableArrayList()
                );
                sections.add(section);
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return sections;
    }
    
    /**
     * Retourne toutes les sections en fonction de l'année scolaire. Les classes ne contiendront que l'id et le nom. Version optimisée pour l'incription
     * @param id_annee
     * @return List<Section> sections
     * @throws DAOException
     */
    public List<Section> readAllSimple(int id_annee) throws DAOException {
        List<Section> sections = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM section";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Classe> classes = new ClasseDAO().readAllSimple(resultSet.getInt("id"));
                Section section = new Section(
                        resultSet.getInt("id"),
                        resultSet.getString("intitule"),
                        classes != null ? FXCollections.observableArrayList(classes) : FXCollections.observableArrayList()
                );
                sections.add(section);
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return sections;
    }
    
    /**
    * Retourne une section en fonction de son nom
     * @param nom
     * @return Section
     * @throws DAOException
     */
    public Section read(String nom) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM section WHERE intitule = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nom);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                List<Classe> classes = new ClasseDAO().readAll(resultSet.getInt("id"));
                Section section = new Section(
                        resultSet.getInt("id"),
                        resultSet.getString("intitule"),
                        classes!=null ? FXCollections.observableArrayList(classes) : FXCollections.observableArrayList()
                );
                return section;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return null;
    }
}
