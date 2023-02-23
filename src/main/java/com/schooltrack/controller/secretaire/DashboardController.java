package com.schooltrack.controller.secretaire;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.models.Section;
import com.schooltrack.models.datasource.SectionDAO;
import com.schooltrack.utils.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class DashboardController {
    
    @FXML
    private Label effectifCollege;

    @FXML
    private Label effectifElementaire;

    @FXML
    private Label effectifFroid;

    @FXML
    private Label effectifMaternelle;

    @FXML
    private Label loginLabel;
    
    @FXML
    private AnchorPane dashboardLayout;
    
    @FXML
    void initialize() {
//        initSectionSize();
    }
    
    public void setLoginLabel(String loginLabel) {
        this.loginLabel.setText(loginLabel);
    }
    
    /**
     * afficher les effectifs des sections
     * @return
     */
    public void initSectionSize() {
        SectionDAO sectionDAO = new SectionDAO();
        try {
            ObservableList<Section> sections = FXCollections.observableArrayList(sectionDAO.readAll());
            for (Section section : sections) {
                switch (section.getIntitule()) {
                    case "Maternelle":
                        effectifMaternelle.setText(String.valueOf(section.getEffectif()));
                        break;
                    case "Elémentaire":
                        effectifElementaire.setText(String.valueOf(section.getEffectif()));
                        break;
                    case "Collège":
                        effectifCollege.setText(String.valueOf(section.getEffectif()));
                        break;
                    case "Froid et climatisation":
                        effectifFroid.setText(String.valueOf(section.getEffectif()));
                        break;
                }
            }
        } catch (Exception e) {
            Alerts.showError((Stage) dashboardLayout.getScene().getWindow(), e.getMessage());
        }
    }
}
