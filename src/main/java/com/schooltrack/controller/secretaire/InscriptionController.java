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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class InscriptionController {
    
    private Stage parentStage;
    private List<Section> sections;
    
    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
    
    @FXML
    private TextField adresseTextField;

    @FXML
    private DatePicker dtNaissPicker;

    @FXML
    private RadioButton femininBR;

    @FXML
    private TextField lieuNaissTextField;

    @FXML
    private TextField mailTextField;

    @FXML
    private RadioButton masculinBR;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private ChoiceBox<String> sectionChoiceBox;
    
    @FXML
    private ChoiceBox<String> classeChoiceBox;

    @FXML
    private ToggleGroup sexe;

    @FXML
    private TextField telephoneTextField;

    @FXML
    void inscrire(ActionEvent event) {
        if (isInputValid() && Alerts.showConfirmation(parentStage, "Voulez-vous vraiment inscrire cet élève en "+classeChoiceBox.getValue()+" ?") ) {
            Eleve eleve = new Eleve();
            eleve.setNom(nomTextField.getText());
            eleve.setPrenom(prenomTextField.getText());
            eleve.setSexe(masculinBR.isSelected() ? "M" : "F");
            eleve.setLieuDeNaissance(lieuNaissTextField.getText());
            eleve.setDateDeNaissance(dtNaissPicker.getValue());
            eleve.setAdresse(adresseTextField.getText());
            eleve.setNumtelephone(telephoneTextField.getText());
            eleve.setEmail(mailTextField.getText());
            eleve.setId_classe(sections.get(sectionChoiceBox.getSelectionModel().getSelectedIndex()).getClasses().get(classeChoiceBox.getSelectionModel().getSelectedIndex()).getId());
            try {
                new EleveDAO().create(eleve);
                Alerts.showSuccess(parentStage, "L'élève a été inscrit avec succès");
            } catch (DAOException e) {
                Alerts.showError(parentStage, "Erreur lors de l'inscription de l'élève");
            } catch (Exception e) {
                e.printStackTrace();
                Alerts.showError(parentStage, e.getMessage()+" in "+getClass().getSimpleName());
            }
            clearFields();
        }
    }
    
    private void clearFields() {
        nomTextField.clear();
        prenomTextField.clear();
        masculinBR.setSelected(true);
        lieuNaissTextField.clear();
        dtNaissPicker.setValue(null);
        adresseTextField.clear();
        telephoneTextField.clear();
        mailTextField.clear();
    }
    
    @FXML
    private void initialize() {
        // ajout de listener sur le choix de la section
        sectionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateClasseChoiceBox();
        }
        );
    }
    
    private void updateClasseChoiceBox() {
        classeChoiceBox.getItems().clear();
        for (Classe classe : sections.get(sectionChoiceBox.getSelectionModel().getSelectedIndex()).getClasses()) {
            classeChoiceBox.getItems().add(classe.getNom());
        }
        //choisir la première classe par défaut
        classeChoiceBox.getSelectionModel().selectFirst();
    }
    
    public void initSectionChoiceBox() {
        try {
            sections = new SectionDAO().readAllSimple(new AnneeScolaireDAO().readLastId());
            System.out.println("Sections : "+sections);
        } catch (DAOException e) {
            Alerts.showError(parentStage, "Erreur lors de la lecture des sections"+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showError(parentStage, e.getMessage()+" in "+getClass().getSimpleName());
        }
        for (Section section : sections) {
            sectionChoiceBox.getItems().add(section.getIntitule());
        }
        //choisir la première section par défaut
        sectionChoiceBox.getSelectionModel().selectFirst();
    }
    
    private boolean isInputValid() {
        String errorMessage = "";
        if (nomTextField.getText() == null || nomTextField.getText().isBlank())
            errorMessage += "Nom invalide !\n";
        if (prenomTextField.getText() == null || prenomTextField.getText().isBlank())
            errorMessage += "Prenom invalide !\n";
        if (telephoneTextField.getText() == null || telephoneTextField.getText().isBlank() || !isValidPhoneNumber(telephoneTextField.getText()))
            errorMessage += "Numero de telephone invalide !\n";
        if (mailTextField.getText() == null || mailTextField.getText().isBlank() || !isValidEmail(mailTextField.getText()))
            errorMessage += "Email invalide !\n";
        if (lieuNaissTextField.getText() == null || lieuNaissTextField.getText().isBlank())
            errorMessage += "Lieu de naissance invalide !\n";
        if (dtNaissPicker.getValue() == null)
            errorMessage += "Date de naissance invalide !\n";
        if (adresseTextField.getText() == null || adresseTextField.getText().isBlank())
            errorMessage += "Adresse invalide !\n";
        if (sexe.getSelectedToggle() == null)
            errorMessage += "Genre invalide !\n";
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alerts.showError(parentStage, "Veuillez remplir les champs correctement :\n"+ errorMessage);
        }
        return false;
    }
    
    /**
     * checks if the email is valid
     * @param email
     * @return true if the email is valid
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    /**
     * checks if the phone number is valid
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^[0-9]{7,15}$");
    }
}
