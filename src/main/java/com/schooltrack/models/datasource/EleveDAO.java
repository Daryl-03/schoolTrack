package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.AnneeScolaire;
import com.schooltrack.models.Bulletin;
import com.schooltrack.models.Eleve;
import com.schooltrack.models.Paiement;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

public class EleveDAO implements DAO<Eleve>{
    /**
     * Crée un nouvel élève dans la base de données
     * @param eleve
     */
    @Override
    public void create(Eleve eleve) throws DAOException {
        eleve.setMatricule(generateMatricule());
        try (Connection connection = DBManager.getConnection()) {
            // Insertion de l'élève
            String sql = "INSERT INTO eleve (matricule, nom, prenom, adresse, dtNaiss, lieuNaiss, numTel, email, sexe, id_classe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, eleve.getMatricule());
            preparedStatement.setString(2, eleve.getNom());
            preparedStatement.setString(3, eleve.getPrenom());
            preparedStatement.setString(4, eleve.getAdresse());
            preparedStatement.setDate(5, java.sql.Date.valueOf(eleve.getDateDeNaissance()));
            preparedStatement.setString(6, eleve.getLieuDeNaissance());
            preparedStatement.setString(7, eleve.getNumtelephone());
            preparedStatement.setString(8, eleve.getEmail());
            preparedStatement.setString(9, eleve.getSexe());
            preparedStatement.setInt(10, eleve.getId_classe());
            preparedStatement.executeUpdate();
            // Récupération de l'id de l'élève
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                eleve.setId(resultSet.getInt(1));
            }
            inscription(eleve);
            new BulletinDAO().generate(eleve.getId(), new AnneeScolaireDAO().readLastId());
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
    }
    
    /**
     * Génère un matricule pour un élève en fonction de son année d'inscription et de son id
     * @return matricule
     */
    public String generateMatricule() throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            AnneeScolaire anneeScolaire = new AnneeScolaireDAO().readLast();
            int indexTiret = anneeScolaire.getIntitule().indexOf('-');
            String sql = "SELECT COUNT(id_eleve) FROM inscription WHERE id_annee = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, anneeScolaire.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return "10" + anneeScolaire.getIntitule().substring(indexTiret - 2, indexTiret) + anneeScolaire.getIntitule().substring(indexTiret + 1, indexTiret+3) + String.format("%03d", resultSet.getInt(1) + 1);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return null;
    }
    
    /**
     * Inscription d'un élève dans une classe
     * @param eleve
     */
    public void inscription(Eleve eleve) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            // Insertion de l'élève dans la table inscription
            String sql = "INSERT INTO inscription (id_eleve, id_classe, id_annee, dateInscription) VALUES (?, ?,(SELECT id FROM anneescolaire ORDER BY id DESC LIMIT 1), ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, eleve.getId());
            preparedStatement.setInt(2, eleve.getId_classe());
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
    }
   
    public void modifierInscription(Eleve eleve) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE inscription SET id_classe = ? WHERE id_eleve = ? AND id_annee = (SELECT id FROM anneescolaire ORDER BY id DESC LIMIT 1)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, eleve.getId_classe());
            preparedStatement.setInt(2, eleve.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
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
            if (resultSet.next()) {
                List<Paiement> paiements = new PaiementDAO().readAllByEleveAndAnnee(id, new AnneeScolaireDAO().readLastId());
                List<Bulletin> bulletins = new BulletinDAO().readAllByYear(id, new AnneeScolaireDAO().readLastId());
                return new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getString("matricule"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("dtNaiss").toLocalDate(),
                        resultSet.getString("lieuNaiss"),
                        resultSet.getString("numTel"),
                        resultSet.getString("email"),
                        resultSet.getString("sexe"),
                        paiements!=null?FXCollections.observableArrayList(paiements):FXCollections.observableArrayList(),
                        bulletins!=null?FXCollections.observableArrayList(bulletins):FXCollections.observableArrayList(),
                        resultSet.getInt("id_classe")
                );
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
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
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
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
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
    }
    
    /**
     * Retire un élève d'une classe
     * @param id
     * @throws DAOException
     */
    public void retirer(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE inscription SET statut = 'INV' WHERE id_eleve = ? AND id_annee = (SELECT MAX(id) FROM anneescolaire)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
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
                List<Paiement> paiements = new PaiementDAO().readAllByEleveAndAnnee(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                List<Bulletin> bulletins = new BulletinDAO().readAllByYear(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                eleves.add(new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getString("matricule"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("dtNaiss").toLocalDate(),
                        resultSet.getString("lieuNaiss"),
                        resultSet.getString("numTel"),
                        resultSet.getString("email"),
                        resultSet.getString("sexe"),
                        paiements!=null?FXCollections.observableArrayList(paiements):FXCollections.observableArrayList(),
                        bulletins!=null?FXCollections.observableArrayList(bulletins):FXCollections.observableArrayList(),
                        resultSet.getInt("id_classe")
                ));
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
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
            String sql = "SELECT * FROM eleve JOIN inscription ON eleve.id = inscription.id_eleve WHERE inscription.id_classe = ? AND inscription.id_annee = ? AND inscription.statut = 'VAL'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_classe);
            preparedStatement.setInt(2, id_annee);
            if(id_classe == 0 || id_annee == 0 )
                throw new DAOException("La classe ou l'année n'est pas définie");
            ResultSet resultSet = preparedStatement.executeQuery();
            PaiementDAO paiementDAO = new PaiementDAO();
            while (resultSet.next()) {
                List<Paiement> paiements = paiementDAO.readAllByEleveAndAnnee(resultSet.getInt("id"), id_annee);
                List<Bulletin> bulletins = new BulletinDAO().readAllByYear(resultSet.getInt("id"), id_annee);
                eleves.add(new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getString("matricule"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("dtNaiss").toLocalDate(),
                        resultSet.getString("lieuNaiss"),
                        resultSet.getString("numTel"),
                        resultSet.getString("email"),
                        resultSet.getString("sexe"),
                        paiements!=null?FXCollections.observableArrayList(paiements):FXCollections.observableArrayList(),
                        bulletins!=null?FXCollections.observableArrayList(bulletins):FXCollections.observableArrayList(),
                        resultSet.getInt("id_classe")
                ));
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return eleves;
    }
    
    /**
     * Recherche un élève par son nom, prénom, date de naissance et lieu de naissance
     * @return eleve ou null si aucun élève n'existe
     */
    public Eleve readByNomPrenomDateNaissLieuNaiss(String nom, String prenom, LocalDate dateNaiss, String lieuNaiss) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM eleve WHERE nom = ? AND prenom = ? AND dtNaiss = ? AND lieuNaiss = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setDate(3, java.sql.Date.valueOf(dateNaiss));
            preparedStatement.setString(4, lieuNaiss);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                List<Paiement> paiements = new PaiementDAO().readAllByEleveAndAnnee(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                List<Bulletin> bulletins = new BulletinDAO().readAllByYear(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                return new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getString("matricule"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("dtNaiss").toLocalDate(),
                        resultSet.getString("lieuNaiss"),
                        resultSet.getString("numTel"),
                        resultSet.getString("email"),
                        resultSet.getString("sexe"),
                        paiements != null ? FXCollections.observableArrayList(paiements) : FXCollections.observableArrayList(),
                        bulletins != null ? FXCollections.observableArrayList(bulletins) : FXCollections.observableArrayList(),
                        resultSet.getInt("id_classe")
                );
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return null;
    }
    
    /**
     * Recherche un élève par son matricule
     * @return eleve ou null si aucun élève n'existe
     */
    public Eleve readByMatricule(String matricule) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM eleve WHERE matricule = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                List<Paiement> paiements = new PaiementDAO().readAllByEleveAndAnnee(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                List<Bulletin> bulletins = new BulletinDAO().readAllByYear(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                return new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getString("matricule"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("dtNaiss").toLocalDate(),
                        resultSet.getString("lieuNaiss"),
                        resultSet.getString("numTel"),
                        resultSet.getString("email"),
                        resultSet.getString("sexe"),
                        paiements != null ? FXCollections.observableArrayList(paiements) : FXCollections.observableArrayList(),
                        bulletins != null ? FXCollections.observableArrayList(bulletins) : FXCollections.observableArrayList(),
                        resultSet.getInt("id_classe")
                );
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return null;
    }
    
    /**
     * Recherche des élèves par leur nom
     * @return eleve ou null si aucun élève n'existe
     */
    public List<Eleve> readByNom(String nom) throws DAOException {
        List<Eleve> eleves = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM eleve WHERE nom LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + nom + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Paiement> paiements = new PaiementDAO().readAllByEleveAndAnnee(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                List<Bulletin> bulletins = new BulletinDAO().readAllByYear(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                eleves.add(new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getString("matricule"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("dtNaiss").toLocalDate(),
                        resultSet.getString("lieuNaiss"),
                        resultSet.getString("numTel"),
                        resultSet.getString("email"),
                        resultSet.getString("sexe"),
                        paiements != null ? FXCollections.observableArrayList(paiements) : FXCollections.observableArrayList(),
                        bulletins != null ? FXCollections.observableArrayList(bulletins) : FXCollections.observableArrayList(),
                        resultSet.getInt("id_classe")
                ));
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return eleves;
    }
    
    /**
     * Recherche des élèves par leur prénom ou nom
     * @param promptText
     * @return eleve ou null si aucun élève n'existe
     * @throws DAOException
     */
    public List<Eleve> readByPromptText(String promptText) throws DAOException {
        List<Eleve> eleves = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM eleve WHERE nom LIKE ? OR prenom LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + promptText + "%");
            preparedStatement.setString(2, "%" + promptText + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Paiement> paiements = new PaiementDAO().readAllByEleveAndAnnee(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                List<Bulletin> bulletins = new BulletinDAO().readAllByYear(resultSet.getInt("id"), new AnneeScolaireDAO().readLastId());
                Eleve eleve = new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getString("matricule"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("dtNaiss").toLocalDate(),
                        resultSet.getString("lieuNaiss"),
                        resultSet.getString("numTel"),
                        resultSet.getString("email"),
                        resultSet.getString("sexe"),
                        paiements != null ? FXCollections.observableArrayList(paiements) : FXCollections.observableArrayList(),
                        bulletins != null ? FXCollections.observableArrayList(bulletins) : FXCollections.observableArrayList(),
                        resultSet.getInt("id_classe")
                );
                eleve.setClasse(new ClasseDAO().readNom(resultSet.getInt("id_classe")));
                eleves.add(eleve);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return eleves;
    }
}
