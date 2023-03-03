package com.schooltrack.controller.administateur;

import com.schooltrack.models.Utilisateur;
import com.schooltrack.models.factory.UtilisateurFactory;
import com.schooltrack.utils.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UserEditController {
    
      @FXML
    private RadioButton adminBR;

    @FXML
    private RadioButton caissierBR;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nomTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private RadioButton secretaireBR;

    @FXML
    private ToggleGroup type;
    
    @FXML
    private Label titleLabel;

    @FXML
    private TextField usernameTextField;
    
    @FXML
    private TextField numeroTextField;
    
    @FXML
    private Button addButton;
    
    private Stage dialogStage;
    private boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    private Utilisateur utilisateur;
    
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.titleLabel.setText("Modifier l'utilisateur");
        this.usernameTextField.setText(utilisateur.getLogin());
        this.passwordTextField.setText(utilisateur.getPassword());
        this.nomTextField.setText(utilisateur.getNom());
        this.prenomTextField.setText(utilisateur.getPrenom());
        this.emailTextField.setText(utilisateur.getEmail());
        this.numeroTextField.setText(utilisateur.getTelephone());
        this.type.selectToggle(utilisateur.getType().equals("Administrateur") ? adminBR : utilisateur.getType().equals("Caissier") ? caissierBR : secretaireBR);
        this.addButton.setText("Modifier");
    }
    
    @FXML
    void addUser(ActionEvent event) {
        if (isInputValid()) {
            okClicked = true;
            if(utilisateur == null) {
                // création d'un nouvel utilisateur selon le type choisi
                utilisateur = UtilisateurFactory.getUtilisateur(((RadioButton)(type.getSelectedToggle())).getText());
            } else {
                // modification d'un utilisateur existant
                int id = utilisateur.getId();
                utilisateur = UtilisateurFactory.getUtilisateur(((RadioButton)(type.getSelectedToggle())).getText());
                utilisateur.setId(id);
            }
            utilisateur.setLogin(usernameTextField.getText());
            utilisateur.setPassword(passwordTextField.getText());
            utilisateur.setNom(nomTextField.getText());
            utilisateur.setPrenom(prenomTextField.getText());
            utilisateur.setEmail(emailTextField.getText());
            utilisateur.setTelephone(numeroTextField.getText());
            dialogStage.close();
        }
    }
    
    private boolean isInputValid() {
        String errorMessage = "";
        
        if (usernameTextField.getText() == null || usernameTextField.getText().isBlank())
            errorMessage += "Login invalide! \n";
        if (passwordTextField.getText() == null || passwordTextField.getText().isBlank() || passwordTextField.getText().length() < 8)
            errorMessage += "Mot de passe invalide! 8 caractères minimum \n";
        if (nomTextField.getText() == null || nomTextField.getText().isBlank())
            errorMessage += "Nom invalide! \n";
        if (prenomTextField.getText() == null || prenomTextField.getText().isBlank())
            errorMessage += "Prénom invalide! \n";
        if (emailTextField.getText() == null || emailTextField.getText().isBlank() || !isEmailValid(emailTextField.getText()))
            errorMessage += "Email invalide! \n";
        if (numeroTextField.getText() == null || numeroTextField.getText().isBlank() || !isPhoneValid(numeroTextField.getText()))
            errorMessage += "Numéro de téléphone invalide! \n";
        if (type.getSelectedToggle() == null)
            errorMessage += "Sélectionnez un type d'utilisateur! \n";
            
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerts.showError(dialogStage, errorMessage);
            return false;
        }
        
    }
    
    private boolean isEmailValid(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    private boolean isPhoneValid(String phone) {
        return phone.matches("^[0-9]{7,}$");
    }
    
    @FXML
    void handleCancel(ActionEvent event) {
        if(Alerts.showConfirmation(dialogStage, "Voulez-vous vraiment annuler?"))
        dialogStage.close();
    }

    
    @FXML
    private void initialize() {
    
    }
}
