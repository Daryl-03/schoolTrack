package com.schooltrack.controller.caissier;

import com.schooltrack.models.datasource.PaiementDAO;
import com.schooltrack.utils.Alerts;
import com.schooltrack.utils.Constantes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardCaissierController {
    
    @FXML
    private Label caisseLabel;

    @FXML
    private Label inscriptionLabel;

    @FXML
    private Label loginLabel;
    @FXML
    private Stage parentStage;
    
    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
    
    @FXML
    public void initialize() {
        try {
            loginLabel.setText(Constantes.CURRENT_USER.getLogin());
            caisseLabel.setText(String.valueOf(new PaiementDAO().getMontantMois()));
            inscriptionLabel.setText(String.valueOf(new PaiementDAO().getNombreInscription()));
        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation du dashboard caissier");
            e.printStackTrace();
            Alerts.showError((Stage) loginLabel.getScene().getWindow(), "Erreur lors de l'initialisation du dashboard caissier");
        }
    }
}
