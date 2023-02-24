package com.schooltrack.controller;

import com.schooltrack.controller.secretaire.SecretaryHomeController;
import com.schooltrack.models.Utilisateur;
import com.schooltrack.models.datasource.UtilisateurDAO;
import com.schooltrack.utils.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button closeButton;
    @FXML
    private Label errorLabel;
    private Stage primaryStage;
    
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    @FXML
    void initialize() {
    
    }
    
    @FXML
    private void handleLogin() {
        if(isInputValid()){
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            try {
                Utilisateur utilisateur = utilisateurDAO.readByLoginPassword(loginField.getText(), passwordField.getText());
                if(utilisateur == null) {
                    showError("Login ou Mot de passe invalide!");
                } else {
                    primaryStage.hide();
                    FXMLLoader loader = new FXMLLoader();
                    Stage homeStage = new Stage();
                    homeStage.setTitle("SchoolTrack");
                    homeStage.setResizable(false);
                    homeStage.setOnCloseRequest(event -> {
                        event.consume();
                    });
                    homeStage.setUserData(utilisateur);
                    switch (utilisateur.getType()) {
                        case "Administateur":
//                            AdminHomeController adminHomeController = new AdminHomeController();
//                            adminHomeController.setPrimaryStage(primaryStage);
//                            adminHomeController.show();
                            break;
                        case "Secretaire":
                            loader.setLocation(getClass().getResource("/com/schooltrack/view/secretaire/root.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            homeStage.setScene(scene);
                            SecretaryHomeController controller = loader.getController();
                            controller.setParentStage(primaryStage);
                            controller.setActualStage(homeStage);
                            controller.setUtilisateurConnecte(utilisateur);
                            controller.dashboard();
                            controller.initRootFeatures();
                            homeStage.showAndWait();
                            break;
                        case "Caissier":
//                            CashierHomeController cashierHomeController = new CashierHomeController();
//                            cashierHomeController.setPrimaryStage(primaryStage);
//                            cashierHomeController.show();
                            break;
                        default:
                            break;
                    }
                    reinitFields();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alerts.showError(primaryStage, e.getMessage());
            }
        
        }
    }
    
    private void reinitFields() {
        loginField.setText("");
        passwordField.setText("");
        errorLabel.setText("");
    }
    
    private boolean isInputValid() {
        String errorMessage = "";
        if (loginField.getText() == null || loginField.getText().length() == 0 || passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "Login ou Mot de passe invalide!";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            showError(errorMessage);
            return false;
        }
    }
    
    private void showError(String errorMessage) {
        errorLabel.setStyle("-fx-text-fill: red");
        errorLabel.setText(errorMessage);
    }
    
    @FXML
    private void handleClose() {
        if(Alerts.showConfirmation(primaryStage, "Voulez-vous vraiment quitter l'application?")) {
            primaryStage.close();
            System.exit(0);
        }
    }
}
