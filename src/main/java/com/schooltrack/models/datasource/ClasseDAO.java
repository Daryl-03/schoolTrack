package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Classe;
import com.schooltrack.models.Eleve;
import com.schooltrack.models.Matiere;
import com.schooltrack.models.Rubrique;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ClasseDAO implements DAO<Classe>{
    
    /**
     * Insertion d'une classe dans la base de données
     * @param classe
     * @throws DAOException
     */
    @Override
    public void create(Classe classe) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            // Insertion de l'élève
            String sql = "INSERT INTO classe (nom, id_section) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classe.getNom());
            preparedStatement.setInt(2, classe.getId_section());
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
    }
    
    /**
     * Retourne une classe à partir de son id
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public Classe read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM classe WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                List<Rubrique> rubriques = new RubriqueDAO().readAllByClasse(resultSet.getInt("id"));
                List<Matiere> matieres = new MatiereDAO().readAllByClasse(resultSet.getInt("id"));
                List<Eleve> eleves = new EleveDAO().readAllByClasse(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                Classe classe = new Classe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        rubriques!=null?FXCollections.observableArrayList(rubriques):FXCollections.observableArrayList(),
                        matieres!=null?FXCollections.observableArrayList(matieres):FXCollections.observableArrayList(),
                        eleves!=null?FXCollections.observableArrayList(eleves):FXCollections.observableArrayList(),
                        resultSet.getInt("id_section")
                );
                return classe;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return null;
    }
    
    @Override
    public void update(Classe classe) throws DAOException {
    
    }
    
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    /**
     * Retourne la liste de toutes les classes
     * @return
     * @throws DAOException
     */
    @Override
    public List<Classe> readAll() throws DAOException {
        List<Classe> classes = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM classe";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Rubrique> rubriques = new RubriqueDAO().readAllByClasse(resultSet.getInt("id"));
                List<Matiere> matieres = new MatiereDAO().readAllByClasse(resultSet.getInt("id"));
                List<Eleve> eleves = new EleveDAO().readAllByClasse(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                Classe classe = new Classe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        rubriques!=null?FXCollections.observableArrayList(rubriques):FXCollections.observableArrayList(),
                        matieres!=null?FXCollections.observableArrayList(matieres):FXCollections.observableArrayList(),
                        eleves!=null?FXCollections.observableArrayList(eleves):FXCollections.observableArrayList(),
                        resultSet.getInt("id_section")
                );
                classes.add(classe);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return classes;
    }
    
    /**
     * Retourne la liste des classes d'une section
     * @param idSection
     * @return
     * @throws DAOException
     */
    public List<Classe> readAll(int idSection) throws DAOException {
        List<Classe> classes = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM classe WHERE id_section = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idSection);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Rubrique> rubriques = new RubriqueDAO().readAllByClasse(resultSet.getInt("id"));
                List<Matiere> matieres = new MatiereDAO().readAllByClasse(resultSet.getInt("id"));
                List<Eleve> eleves = new EleveDAO().readAllByClasse(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                Classe classe = new Classe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        rubriques!=null?FXCollections.observableArrayList(rubriques):FXCollections.observableArrayList(),
                        matieres!=null?FXCollections.observableArrayList(matieres):FXCollections.observableArrayList(),
                        eleves!=null?FXCollections.observableArrayList(eleves):FXCollections.observableArrayList(),
                        resultSet.getInt("id_section")
                );
                classes.add(classe);
            }
        } catch (Exception e) {
            System.out.println("Erreur in ClasseDAO.readAll(int idSection)");
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return classes;
    }
    
    /**
     * Retourne la liste des classes d'une section et d'une année scolaire
     * @param idSection l'id de la section
     * @param id_annee l'id de l'année scolaire
     * @return la liste des classes
     * @throws DAOException
     */
    public List<Classe> readAll(int idSection, int id_annee) throws DAOException {
        List<Classe> classes = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM classe WHERE id_section = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idSection);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Rubrique> rubriques = new RubriqueDAO().readAllByClasse(resultSet.getInt("id"));
                List<Matiere> matieres = new MatiereDAO().readAllByClasse(resultSet.getInt("id"));
                List<Eleve> eleves = new EleveDAO().readAllByClasse(resultSet.getInt("id"), id_annee);
                Classe classe = new Classe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        rubriques != null ? FXCollections.observableArrayList(rubriques) : FXCollections.observableArrayList(),
                        matieres != null ? FXCollections.observableArrayList(matieres) : FXCollections.observableArrayList(),
                        eleves != null ? FXCollections.observableArrayList(eleves) : FXCollections.observableArrayList(),
                        resultSet.getInt("id_section")
                );
                classes.add(classe);
            }
        } catch (Exception e) {
            System.out.println("Erreur in ClasseDAO.readAll(int idSection, int id_annee)");
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return classes;
    }
}
