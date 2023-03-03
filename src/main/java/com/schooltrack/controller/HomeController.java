package com.schooltrack.controller;

import com.schooltrack.controller.administateur.UserController;
import com.schooltrack.controller.caissier.PaiementController;
import com.schooltrack.controller.secretaire.DashboardController;
import com.schooltrack.controller.secretaire.InscriptionController;
import com.schooltrack.controller.secretaire.SectionController;
import com.schooltrack.exceptions.DAOException;
import com.schooltrack.models.AnneeScolaire;
import com.schooltrack.models.Utilisateur;
import com.schooltrack.models.datasource.AnneeScolaireDAO;
import com.schooltrack.utils.Alerts;
import com.schooltrack.utils.Constantes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeController {
    @FXML
    private AnchorPane rootLayout;
    
    private Utilisateur utilisateurConnecte;
    private Stage parentStage;
    private Stage actualStage;
    @FXML
    private ChoiceBox<String> anneeScolaireChoiceBox;
    @FXML
    private Label loggedUserLabel;
    @FXML
    private TextField promptRechercheTextField;
    
    
    public void setActualStage(Stage actualStage) {
        this.actualStage = actualStage;
    }
    
    public void setParentStage(Stage primaryStage) {
        this.parentStage = primaryStage;
    }
    
    public void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
    }
    
    @FXML
    private void classes() {
        try {
            FXMLLoader loader = new FXMLLoader();
            if (Constantes.CURRENT_USER.getType().equals("Secretaire"))
                loader.setLocation(getClass().getResource("/com/schooltrack/view/secretaire/sections.fxml"));
            else if (Constantes.CURRENT_USER.getType().equals("Caissier"))
                loader.setLocation(getClass().getResource("/com/schooltrack/view/caissier/sections.fxml"));
            else
                loader.setLocation(getClass().getResource("/com/schooltrack/view/adminR/sections.fxml"));
            AnchorPane sections = loader.load();
            rootLayout.getChildren().setAll(sections);
            SectionController sectionController = loader.getController();
            sectionController.initSectionChoiceBox();
            
        } catch (Exception e){
            System.out.println("In Secretaire-classes() method");
            e.printStackTrace();
            Alerts.showError(parentStage, e.getMessage()+e.getCause());
        }
    }

    @FXML
    public void dashboard() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/schooltrack/view/secretaire/dashboard.fxml"));
            AnchorPane dashboard = loader.load();
            rootLayout.getChildren().setAll(dashboard);
            DashboardController dashboardController = loader.getController();
            dashboardController.setLoginLabelValue(utilisateurConnecte.getLogin());
            dashboardController.initSectionSize();
        } catch (Exception e){
            System.out.println("In Secretaire-dashboard() method");
//            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            Alerts.showError(parentStage, e.getMessage()+e.getCause());
        }
    }
    
    public void initRootFeatures(){
        loggedUserLabel.setText(Constantes.CURRENT_USER.getLogin());
        try {
            AnneeScolaireDAO anneeScolaireDAO = new AnneeScolaireDAO();
            anneeScolaireChoiceBox.getItems().addAll(anneeScolaireDAO.readAllIntitules());
            // set the most recent year as default
            anneeScolaireChoiceBox.setValue(anneeScolaireDAO.read(anneeScolaireDAO.readLastId()).getIntitule());
            
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showError(parentStage, e.getMessage()+e.getCause());
        }
    }
    
    @FXML
    private void inscription() {
        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/schooltrack/view/secretaire/inscription.fxml"));
            AnchorPane inscription = loader.load();
    
            InscriptionController inscriptionController = loader.getController();
            inscriptionController.setParentStage(parentStage);
            inscriptionController.initSectionChoiceBox();
            
            rootLayout.getChildren().setAll(inscription);
        } catch (Exception e){
            System.out.println("In Secretaire-inscription() method");
            e.printStackTrace();
            Alerts.showError(parentStage, e.getMessage()+e.getCause());
        }
    }
    
    @FXML
    private void searchEleve() {
        if(promptRechercheTextField.getText().isBlank()) {
            Alerts.showError(parentStage, "Veuillez saisir un nom ou un prénom");
            return;
        }
         try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/schooltrack/view/searchResult.fxml"));
                AnchorPane searchEleve = loader.load();
                SearchResultController searchResultController = loader.getController();
                searchResultController.setPrompt(promptRechercheTextField.getText());
                rootLayout.getChildren().setAll(searchEleve);
            } catch (Exception e){
                System.out.println("In Secretaire-searchEleve() method");
                e.printStackTrace();
                Alerts.showError(parentStage, e.getMessage()+e.getCause());
            }
    }
    
    @FXML
    private void paiement() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/schooltrack/view/caissier/paiement.fxml"));
            AnchorPane paiement = loader.load();
    
            PaiementController paiementController = loader.getController();
            paiementController.setParentStage(parentStage);
            paiementController.initChoiceBoxes();
            
            rootLayout.getChildren().setAll(paiement);
        } catch (Exception e){
            System.out.println("In Secretaire-paiement() method");
            e.printStackTrace();
            Alerts.showError(parentStage, e.getMessage()+e.getCause());
        }
    }
    
    @FXML
    private void initialize() {
        // ajout de listener sur le choiceBox pour stocker l'année scolaire sélectionnée
        anneeScolaireChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                AnneeScolaireDAO anneeScolaireDAO = new AnneeScolaireDAO();
                AnneeScolaire anneeScolaire = anneeScolaireDAO.readByIntitule(newValue);
                Constantes.CURRENT_YEAR = anneeScolaire;
            } catch (DAOException e) {
                e.printStackTrace();
                Alerts.showError(actualStage, e.getMessage()+e.getCause());
            }
        });
    }
    
    @FXML
    public void users() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/schooltrack/view/adminR/utilisateurs.fxml"));
            AnchorPane users = loader.load();
            rootLayout.getChildren().setAll(users);
            UserController usersController = loader.getController();
            usersController.initTable();
        } catch (Exception e){
            System.out.println("In Secretaire-users() method");
            e.printStackTrace();
            Alerts.showError(parentStage, e.getMessage()+e.getCause());
        }
    }
    
    @FXML
    private void deconnexion() {
        if(Alerts.showConfirmation(parentStage, "Voulez-vous vraiment vous déconnecter?")) {
            parentStage.show();
            actualStage.close();
        }
    }
    
    @FXML
    private void close() {
        if(Alerts.showConfirmation(parentStage, "Voulez-vous vraiment quitter l'application?")) {
            actualStage.close();
        }
    }
}
