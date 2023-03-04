package com.schooltrack.controller.secretaire;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.models.Classe;
import com.schooltrack.models.Eleve;
import com.schooltrack.models.Section;
import com.schooltrack.models.datasource.AnneeScolaireDAO;
import com.schooltrack.models.datasource.EleveDAO;
import com.schooltrack.models.datasource.SectionDAO;
import com.schooltrack.utils.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.util.List;

public class ReaffectationController {

    @FXML
    private ChoiceBox<String> classeChoiceBox;

    @FXML
    private ChoiceBox<String> sectionChoiceBox;
    
    private List<Section> sections;
    
    private Stage dialogStage;
    
    private Eleve eleve;
    
    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    @FXML
    void handleCancel(ActionEvent event) {
        if(Alerts.showConfirmation(dialogStage, "Voulez-vous vraiment annuler l'opération?")){
            dialogStage.close();
        }
    }

    @FXML
    void handleValider(ActionEvent event) {
        if(Alerts.showConfirmation(dialogStage, "Voulez-vous vraiment valider l'opération?")){
            EleveDAO eleveDAO = new EleveDAO();
            eleve.setId_classe(sections.get(sectionChoiceBox.getSelectionModel().getSelectedIndex()).getClasses().get(classeChoiceBox.getSelectionModel().getSelectedIndex()).getId());
            try {
                eleveDAO.update(eleve);
                eleveDAO.modifierInscription(eleve);
                Alerts.showInfo(dialogStage, "L'élève a été réaffecté avec succès");
            } catch (DAOException e) {
                System.out.println("Erreur lors de la mise à jour de l'élève");
                e.printStackTrace();
                Alerts.showError(dialogStage, "Erreur lors de la mise à jour de l'élève");
            }
            dialogStage.close();
        }
    }

    @FXML
    void initialize() {
        // ajout de listener sur le choix de la section
        sectionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateClasseChoiceBox();
        }
        );
    }
    
    public void initSectionChoiceBox() {
        try{
            sections = new SectionDAO().readAllSimple(new AnneeScolaireDAO().readLastId());
            System.out.println("Sections : "+sections);
        } catch (DAOException e) {
            Alerts.showError(dialogStage, "Erreur lors de la lecture des sections"+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showError(dialogStage, e.getMessage()+" in "+getClass().getSimpleName());
        }
        for (Section section : sections) {
            sectionChoiceBox.getItems().add(section.getIntitule());
        }
        //choisir la première section par défaut
        sectionChoiceBox.getSelectionModel().selectFirst();
    }
    
    private void updateClasseChoiceBox() {
        classeChoiceBox.getItems().clear();
        for (Classe classe : sections.get(sectionChoiceBox.getSelectionModel().getSelectedIndex()).getClasses()) {
            classeChoiceBox.getItems().add(classe.getNom());
        }
        //choisir la première classe par défaut
        classeChoiceBox.getSelectionModel().selectFirst();
    }
}
