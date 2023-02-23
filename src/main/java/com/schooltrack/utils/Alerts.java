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
}
