package com.schooltrack.controller;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.models.Eleve;
import com.schooltrack.models.datasource.EleveDAO;
import com.schooltrack.utils.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class SearchResultController {
    
    
    @FXML
    private TableColumn<Eleve, String> adresseColumn;
    @FXML
    private TableColumn<Eleve, LocalDate> dtNaissColumn;
    @FXML
    private TableView<Eleve> eleveTable;
    @FXML
    private TableColumn<Eleve, String> emailColumn;
    @FXML
    private TableColumn<Eleve, String> matriculeColumn;
    @FXML
    private TableColumn<Eleve, String> lieuNaissColumn;
    @FXML
    private TableColumn<Eleve, String> nomColumn;
    @FXML
    private TableColumn<Eleve, String> numColumn;
    @FXML
    private TableColumn<Eleve, String> prenomColumn;
    @FXML
    private TableColumn<Eleve, String> sexeColumn;
    @FXML
    private TableColumn<Eleve, String> classeColumn;
    
    private String prompt;
    
    private Stage parentStage;
    
    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
    
    public void setPrompt(String prompt) {
        this.prompt = prompt;
        try {
            List<Eleve> eleves = new EleveDAO().readByPromptText(prompt);
            eleveTable.getItems().setAll(eleves);
        } catch (Exception e) {
            System.out.println("In SearchResultController-setPrompt() method");
            e.printStackTrace();
            Alerts.showError(parentStage, e.getMessage()+e.getCause());
        }
    }
    
    @FXML
    private void initialize() {
        matriculeColumn.setCellValueFactory(cellData -> cellData.getValue().matriculeProperty());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        sexeColumn.setCellValueFactory(cellData -> cellData.getValue().sexeProperty());
        dtNaissColumn.setCellValueFactory(cellData -> cellData.getValue().dateDeNaissanceProperty());
        lieuNaissColumn.setCellValueFactory(cellData -> cellData.getValue().lieuDeNaissanceProperty());
        adresseColumn.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
        numColumn.setCellValueFactory(cellData -> cellData.getValue().numtelephoneProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        classeColumn.setCellValueFactory(cellData -> cellData.getValue().classeProperty());
    }
    
    
}
