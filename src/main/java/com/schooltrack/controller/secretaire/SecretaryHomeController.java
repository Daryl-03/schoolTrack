package com.schooltrack.controller.secretaire;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.models.AnneeScolaire;
import com.schooltrack.models.Utilisateur;
import com.schooltrack.models.datasource.AnneeScolaireDAO;
import com.schooltrack.utils.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class SecretaryHomeController {
    @FXML
    private AnchorPane rootLayout;
    
    private Utilisateur utilisateurConnecte;
    private Stage parentStage;
    private Stage actualStage;
    @FXML
    private ChoiceBox<String> anneeScolaireChoiceBox;
    @FXML
    private Label loggedUserLabel;
    
    
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
            loader.setLocation(getClass().getResource("/com/schooltrack/view/secretaire/sections.fxml"));
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
        loggedUserLabel.setText(((Utilisateur)(rootLayout.getScene().getWindow().getUserData())).getLogin());
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

    }
    
    @FXML
    private void initialize() {
    
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
