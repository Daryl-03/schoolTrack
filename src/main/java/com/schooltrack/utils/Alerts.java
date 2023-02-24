package com.schooltrack.utils;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Alerts {
    public static void showError(Stage owner, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(owner);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(new javafx.scene.control.Label(message));
        alert.setResizable(false);
        alert.showAndWait();
    }
    
    public static boolean showConfirmation(Stage owner, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(owner);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(new javafx.scene.control.Label(message));
        alert.setResizable(false);
        alert.showAndWait();
        return alert.getResult().getText().equals("OK");
    }
    
    /**
     * popup de confirmation
     * @param owner fenetre parent
     * @param header titre du popup
     * @param message message du popup
     */
    public static boolean showConfirmation(Stage owner, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(owner);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.getDialogPane().setContent(new javafx.scene.control.Label(message));
        alert.setResizable(false);
        alert.showAndWait();
        return alert.getResult().getText().equals("OK");
    }
    
    public static void showInfo(Stage owner, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(owner);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(new javafx.scene.control.Label(message));
        alert.setResizable(false);
        alert.showAndWait();
    }
    

    public static boolean showSuccess(Stage owner, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(owner);
        alert.setTitle("Succès");
        alert.setHeaderText(header);
        alert.getDialogPane().setContent(new javafx.scene.control.Label(message));
        alert.setResizable(false);
        alert.showAndWait();
        return alert.getResult().getText().equals("OK");
    }
    
    public static boolean showSuccess(Stage owner, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(owner);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(new javafx.scene.control.Label(message));
        alert.setResizable(false);
        alert.showAndWait();
        return alert.getResult().getText().equals("OK");
    }
}
