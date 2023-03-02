package com.schooltrack.controller.caissier;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.models.Eleve;
import com.schooltrack.models.Paiement;
import com.schooltrack.models.Rubrique;
import com.schooltrack.models.datasource.EleveDAO;
import com.schooltrack.models.datasource.PaiementDAO;
import com.schooltrack.models.datasource.RubriqueDAO;
import com.schooltrack.utils.Alerts;
import com.schooltrack.utils.Constantes;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaiementEditController {
    
    @FXML
    private TextField matriculeTextField;

    @FXML
    private ChoiceBox<String> rubriqueChoiceBox;
    
    @FXML
    private TextArea observationsTextArea;

    @FXML
    private Label titleLabel;
    private Paiement paiement;
    
    private Stage actualStage;
    
    private Stage parentStage;
    
    private PaiementDAO paiementDAO = new PaiementDAO();
    private EleveDAO eleveDAO = new EleveDAO();
    private RubriqueDAO rubriqueDAO = new RubriqueDAO();
    private boolean editMode = false;
    
    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
    
    public void setActualStage(Stage actualStage) {
        this.actualStage = actualStage;
    }
    
    @FXML
    void handleCancel(ActionEvent event) {
        if (Alerts.showConfirmation(parentStage, "Quitter?"))
            actualStage.close();
    }
    
    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
        editMode = true;
        if(paiement != null) {
            try {
                matriculeTextField.setText(eleveDAO.read(paiement.getId_eleve()).getMatricule());
                rubriqueChoiceBox.setValue(rubriqueDAO.read(paiement.getId_rubrique()).getIntitule());
                observationsTextArea.setText(paiement.getObservation());
                titleLabel.setText("Modifier un paiement");
            } catch (DAOException e) {
                System.out.println("Erreur lors de la récupération des données PaiementEditController");
                e.printStackTrace();
                Alerts.showError(actualStage, "Erreur lors de la récupération des données :"+ e.getMessage());
            }
        }
    }
    
    @FXML
    void handleOk(ActionEvent event) {
        if(isInputValid()) {
            if(paiement == null) {
                paiement = new Paiement();
            }
            try {
                Eleve eleve = eleveDAO.readByMatricule(matriculeTextField.getText());
                Rubrique rubrique = rubriqueDAO.readByIntituleAndMatricule(rubriqueChoiceBox.getValue(), matriculeTextField.getText());
                if(rubrique.getIntitule().equals("Inscription") && paiementDAO.isPayedInscription(eleve.getId())){
                    Alerts.showError(actualStage, "L'inscription de cet élève a déjà été payée");
                    return;
                } else if(rubrique.getIntitule().equals("Scolarité") && paiementDAO.countScolarite(eleve.getId()) >= Constantes.MAX_SCHOOL_FEE){
                    Alerts.showError(actualStage, "L'élève a déjà payé le maximum de scolarité");
                    return;
                } else if(rubrique.getIntitule().equals("Scolarité") && paiementDAO.isPayedInscription(eleve.getId()) && eleve.getId_classe()==Constantes.ID_CRECHE){
                    Alerts.showError(actualStage, "L'élève a déjà payé la scolarité");
                    return;
                }
                paiement.setMontant(rubrique.getMontant());
                paiement.setDate(LocalDate.now());
                paiement.setId_annee(Constantes.CURRENT_YEAR.getId());
                paiement.setId_eleve(eleve.getId());
                paiement.setId_rubrique(rubrique.getId());
                paiement.setObservation(observationsTextArea.getText());
                if (editMode)
                    paiementDAO.update(paiement);
                else
                    paiementDAO.create(paiement);
                actualStage.close();
            } catch (DAOException e) {
                System.out.println("Erreur lors de la validation des entrées PaiementEditController");
                e.printStackTrace();
                Alerts.showError(actualStage, "Erreur lors de la validation des entrées :"+ e.getMessage());
            }
        }
    }
    
    
    @FXML
    private void initialize() {
    }
    
    public void initRubriqueChoiceBox() {
        List<String> rubriques = new ArrayList<>();
        try {
            rubriques = rubriqueDAO.readAllIntitule();
            rubriqueChoiceBox.setItems(FXCollections.observableArrayList(rubriques));
            rubriqueChoiceBox.getSelectionModel().select(0);
        } catch (DAOException e) {
            System.out.println("Erreur lors de la lecture des rubriques PaiementEditController");
            e.printStackTrace();
            Alerts.showError(actualStage, "Erreur lors de la lecture des rubriques :"+ e.getMessage());
        }
    }
    
    private boolean isInputValid() {
        if(matriculeTextField.getText().isBlank()) {
            Alerts.showError(actualStage, "Le matricule est obligatoire");
            return false;
        }
    
        try {
            if(rubriqueDAO.readByIntituleAndMatricule(rubriqueChoiceBox.getValue(), matriculeTextField.getText()) == null) {
                Alerts.showError(actualStage, "Cette rubrique n'existe pas pour cet élève");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture des entrées PaiementEditController");
            e.printStackTrace();
            Alerts.showError(actualStage, "Erreur lors de la lecture des entrées :"+ e.getMessage());
            return false;
        }
        return true;
    }
}
