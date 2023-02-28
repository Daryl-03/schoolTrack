package com.schooltrack.controller.caissier;

import com.schooltrack.models.PaiementTableViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

public class PaiementController {

    @FXML
    private TableView<PaiementTableViewModel> paiementTable;
    
    @FXML
    private TableColumn<PaiementTableViewModel, String> classeEleveColumn;

    @FXML
    private TableColumn<PaiementTableViewModel, LocalDate> dateColumn;

    @FXML
    private TableColumn<PaiementTableViewModel, String> matriculeColumn;

    @FXML
    private TableColumn<PaiementTableViewModel, Double> montantColumn;

    @FXML
    private TableColumn<PaiementTableViewModel, String> nomEleveColumn;

    @FXML
    private TableColumn<PaiementTableViewModel, String> numeroColumn;

    @FXML
    private TableColumn<PaiementTableViewModel, String> observationColumn;

    @FXML
    private TableColumn<PaiementTableViewModel, String> rubriqueColumn;

    
    @FXML
    private void initialize() {
        // initialisation des colonnes
        numeroColumn.setCellValueFactory(cellData -> cellData.getValue().getPaiement().numeroProperty());
        matriculeColumn.setCellValueFactory(cellData -> cellData.getValue().getEleve().matriculeProperty());
        nomEleveColumn.setCellValueFactory(cellData -> cellData.getValue().getEleve().nomProperty());
        classeEleveColumn.setCellValueFactory(cellData -> cellData.getValue().getEleve().classeProperty());
        rubriqueColumn.setCellValueFactory(cellData -> cellData.getValue().getRubrique().intituleProperty());
        montantColumn.setCellValueFactory(cellData -> cellData.getValue().getPaiement().montantProperty().asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getPaiement().dateProperty());
        observationColumn.setCellValueFactory(cellData -> cellData.getValue().getPaiement().observationProperty());
        
        
    }
}
