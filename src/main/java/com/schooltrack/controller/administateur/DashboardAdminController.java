package com.schooltrack.controller.administateur;

import com.schooltrack.models.datasource.PaiementDAO;
import com.schooltrack.models.datasource.UtilisateurDAO;
import com.schooltrack.utils.Alerts;
import com.schooltrack.utils.Constantes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardAdminController {
    
    @FXML
    private Label caisseLabel;

    @FXML
    private Label inscriptionLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Label nombreUtilisateurLabel;

    
    @FXML
    private void initialize() {
        try {
            loginLabel.setText(Constantes.CURRENT_USER.getLogin());
            caisseLabel.setText(String.valueOf(new PaiementDAO().getMontantMois()));
            inscriptionLabel.setText(String.valueOf(new PaiementDAO().getNombreInscription()));
            nombreUtilisateurLabel.setText(String.valueOf(new UtilisateurDAO().count()));
        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation du dashboard Administrateur");
            e.printStackTrace();
            Alerts.showError((Stage) loginLabel.getScene().getWindow(), "Erreur lors de l'initialisation du dashboard administrateur");
        }
    }
}
