package com.schooltrack.controller.administateur;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.models.Utilisateur;
import com.schooltrack.models.datasource.UtilisateurDAO;
import com.schooltrack.utils.Alerts;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class UserController {
    
    @FXML
    private TableColumn<Utilisateur, String> emailColumn;

    @FXML
    private TableColumn<Utilisateur, String> nomColumn;

    @FXML
    private TableColumn<Utilisateur, String> numColumn;

    @FXML
    private TableColumn<Utilisateur, String> prenomColumn;

    @FXML
    private TableColumn<Utilisateur, String> typeColumn;
    
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @FXML
    private TableView<Utilisateur> userTable;
    
    private Stage parentStage;
    
    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    @FXML
    void addUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/schooltrack/view/adminR/userEdit.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initOwner(parentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Ajouter un utilisateur");
            stage.setResizable(false);
            stage.setScene(scene);
            
            UserEditController controller = loader.getController();
            controller.setDialogStage(stage);
            stage.showAndWait();
            if(controller.isOkClicked()) {
                Utilisateur utilisateur = controller.getUtilisateur();
                if (utilisateurDAO.readByLoginPassword(utilisateur.getLogin(), utilisateur.getPassword()) != null) {
                    Alerts.showError(parentStage, "Cet utilisateur existe déjà");
                } else {
                    utilisateurDAO.create(utilisateur);
                    userTable.getItems().add(utilisateur);
                    Alerts.showInfo(parentStage, "Utilisateur ajouté avec succès");
                }
            }
        } catch (Exception e) {
            System.out.println("In addUser: " + e.getMessage());
            e.printStackTrace();
            Alerts.showError(parentStage, "Une erreur est survenue :"+e.getMessage());
        }
    }

    @FXML
    private void deleteUser(ActionEvent event) {
        if(userTable.getSelectionModel().getSelectedItem() != null) {
            if(!Alerts.showConfirmation(parentStage, "Voulez-vous vraiment supprimer cet utilisateur?"))
                return;
            Utilisateur utilisateur = userTable.getSelectionModel().getSelectedItem();
            try {
                utilisateurDAO.delete(utilisateur.getId());
                userTable.getItems().remove(utilisateur);
                Alerts.showInfo(parentStage, "Utilisateur supprimé avec succès");
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
        } else{
            Alerts.showError(parentStage, "Veuillez sélectionner un utilisateur");
        }
    }

    @FXML
    void editUser(ActionEvent event) {
        try {
            if(userTable.getSelectionModel().getSelectedItem() != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/schooltrack/view/adminR/userEdit.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initOwner(parentStage);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setTitle("Modifier un utilisateur");
                stage.setResizable(false);
                stage.setScene(scene);
                
                UserEditController controller = loader.getController();
                controller.setDialogStage(stage);
                controller.setUtilisateur(userTable.getSelectionModel().getSelectedItem());
                stage.showAndWait();
                if(controller.isOkClicked()) {
                    Utilisateur utilisateur = controller.getUtilisateur();
                    utilisateurDAO.update(utilisateur);
                    initTable();
                    Alerts.showInfo(parentStage, "Utilisateur modifié avec succès");
                }
            } else{
                Alerts.showError(parentStage, "Veuillez sélectionner un utilisateur");
            }
        } catch (Exception e) {
            System.out.println("In editUser: " + e.getMessage());
            e.printStackTrace();
            Alerts.showError(parentStage, "Une erreur est survenue :"+e.getMessage());
        }
    }
    
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        numColumn.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        
    }
    
    public void initTable() {
        try {
            List<Utilisateur> utilisateurs = utilisateurDAO.readAll();
            userTable.setItems(utilisateurs!=null ? FXCollections.observableArrayList(utilisateurs) : FXCollections.observableArrayList());
        } catch (DAOException e) {
            throw new RuntimeException(e);
        };
    }
}
