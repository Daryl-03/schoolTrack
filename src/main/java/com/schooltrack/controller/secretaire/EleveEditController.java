package com.schooltrack.controller.secretaire;

import com.schooltrack.models.Eleve;
import com.schooltrack.utils.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.validation.Validator;

public class EleveEditController {
    
    @FXML
    private RadioButton FemmeBR;

    @FXML
    private TextField adrTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker dtNaissDatePicker;

    @FXML
    private TextField emailTextField;

    @FXML
    private ToggleGroup genre;

    @FXML
    private RadioButton hommeBR;

    @FXML
    private TextField lieuNaissTextField;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField numTextField;

    @FXML
    private Button okButton;

    @FXML
    private TextField prenomTextField;
    
    private Eleve eleve;
    
    private Stage dialogStage;
    
    private boolean okClicked = false;
    
    public Eleve getEleve() {
        return eleve;
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
        if (eleve != null) {
            nomTextField.setText(eleve.getNom());
            prenomTextField.setText(eleve.getPrenom());
            numTextField.setText(eleve.getNumtelephone());
            emailTextField.setText(eleve.getEmail());
            lieuNaissTextField.setText(eleve.getLieuDeNaissance());
            dtNaissDatePicker.setValue(eleve.getDateDeNaissance());
            adrTextField.setText(eleve.getAdresse());
            if (eleve.getSexe().equals("Femme")) {
                FemmeBR.setSelected(true);
            } else {
                hommeBR.setSelected(true);
            }
        }
    }
    
    @FXML
    private void initialize() {
    
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            okClicked = true;
            if(eleve == null) {
                eleve = new Eleve();
            }
            eleve.setNom(nomTextField.getText());
            eleve.setPrenom(prenomTextField.getText());
            eleve.setNumtelephone(numTextField.getText());
            eleve.setEmail(emailTextField.getText());
            eleve.setLieuDeNaissance(lieuNaissTextField.getText());
            eleve.setDateDeNaissance(dtNaissDatePicker.getValue());
            eleve.setAdresse(adrTextField.getText());
            if (FemmeBR.isSelected()) {
                eleve.setSexe("Femme");
            } else {
                eleve.setSexe("Homme");
            }
            dialogStage.close();
        }
    }
    
    private boolean isInputValid() {
        String errorMessage = "";
        if (nomTextField.getText() == null || nomTextField.getText().isBlank())
            errorMessage += "Nom invalide !\n";
        if (prenomTextField.getText() == null || prenomTextField.getText().isBlank())
            errorMessage += "Prenom invalide !\n";
        if (numTextField.getText() == null || numTextField.getText().isBlank() || !isValidPhoneNumber(numTextField.getText()))
            errorMessage += "Numero de telephone invalide !\n";
        if (emailTextField.getText() == null || emailTextField.getText().isBlank() || !isValidEmail(emailTextField.getText()))
            errorMessage += "Email invalide !\n";
        if (lieuNaissTextField.getText() == null || lieuNaissTextField.getText().isBlank())
            errorMessage += "Lieu de naissance invalide !\n";
        if (dtNaissDatePicker.getValue() == null)
            errorMessage += "Date de naissance invalide !\n";
        if (adrTextField.getText() == null || adrTextField.getText().isBlank())
            errorMessage += "Adresse invalide !\n";
        if (genre.getSelectedToggle() == null)
            errorMessage += "Genre invalide !\n";
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alerts.showError(dialogStage, "Veuillez remplir les champs correctement :\n"+ errorMessage);
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
    
    @FXML
    private void cancel() {
        dialogStage.close();
    }
}
