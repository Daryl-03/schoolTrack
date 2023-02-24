package com.schooltrack.controller.secretaire;

import com.schooltrack.models.Matiere;
import com.schooltrack.utils.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MatiereEditController {
     @FXML
    private TextField coeffTextField;

    @FXML
    private TextField matiereTextField;
    
    @FXML
    private TextArea descriptionTextArea;
    private Matiere matiere;
    private Stage dialogStage;
    private boolean okClicked = false;
    
    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
        if(matiere!=null){
            matiereTextField.setText(matiere.getNom());
            coeffTextField.setText(String.valueOf(matiere.getCoefficient()));
            descriptionTextArea.setText(matiere.getDescription());
        }
    }
    
    public Matiere getMatiere() {
        return matiere;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    private void handleOk(ActionEvent event) {
        if(isInputValid()){
            if(matiere == null){
                matiere = new Matiere();
            }
            matiere.setNom(matiereTextField.getText());
            matiere.setCoefficient(Integer.parseInt(coeffTextField.getText()));
            matiere.setDescription(descriptionTextArea.getText());
            okClicked = true;
            dialogStage.close();
        }
    }
    
    private boolean isInputValid() {
        String errorMessage = "";
        if(matiereTextField.getText() == null || matiereTextField.getText().isBlank())
            errorMessage += "Nom invalide !\n";
        if (coeffTextField.getText() == null || coeffTextField.getText().isBlank())
            errorMessage += "Coefficient invalide !\n";
        if (descriptionTextArea.getText() == null || descriptionTextArea.getText().isBlank())
            errorMessage += "Description invalide !\n";
            
        if (errorMessage.length() == 0) {
            return true;
        }
        // Show the error message.
        Alerts.showError(dialogStage, "Veuillez corriger les champs invalides\n"+errorMessage);
        return false;
    }
    
    @FXML
    private void initialize() {
    
    }
}
