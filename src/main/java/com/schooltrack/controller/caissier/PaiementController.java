package com.schooltrack.controller.caissier;

import com.almasb.fxgl.physics.box2d.dynamics.joints.LimitState;
import com.schooltrack.exceptions.DAOException;
import com.schooltrack.models.Paiement;
import com.schooltrack.models.PaiementTableViewModel;
import com.schooltrack.models.datasource.AnneeScolaireDAO;
import com.schooltrack.models.datasource.EleveDAO;
import com.schooltrack.models.datasource.PaiementDAO;
import com.schooltrack.models.datasource.RubriqueDAO;
import com.schooltrack.utils.Alerts;
import com.schooltrack.utils.Constantes;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private ChoiceBox<String> periodeChoiceBox;

    @FXML
    private ChoiceBox<String> rubriqueChoiceBox;
    
    private Stage parentStage;
    
    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
    
     @FXML
    void handleAdd(ActionEvent event) {

    }

    @FXML
    void handleDelete(ActionEvent event) {

    }

    @FXML
    void handleEdit(ActionEvent event) {

    }

    @FXML
    void handlePrint(ActionEvent event) {

    }

    
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
        
        // ajout de listener sur les choiceBox
        rubriqueChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && periodeChoiceBox.getSelectionModel().getSelectedItem() != null ) {
                updatePaiementTable();
            }
        });
        
        periodeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updatePaiementTable();
            }
        });
    }
    
    private void updatePaiementTable() {
        try {
            List<Paiement> paiements = new ArrayList<>();
            if(rubriqueChoiceBox.getSelectionModel().getSelectedItem().equals("Toutes")) {
                System.out.println(periodeChoiceBox.getSelectionModel().getSelectedItem());
                switch (periodeChoiceBox.getSelectionModel().getSelectedItem()) {
                    case "Toutes":
                        paiements = new PaiementDAO().readAll();
                        break;
                    case "Aujourd'hui":
                        paiements = new PaiementDAO().readAllByDate();
                        break;
                    case "Hebdomadaire":
                        paiements = new PaiementDAO().readAllByWeek();
                        break;
                    case "Mensuelle":
                        paiements = new PaiementDAO().readAllByMonth();
                        break;
                }
            } else {
                String rubrique = rubriqueChoiceBox.getSelectionModel().getSelectedItem();
                String periode = periodeChoiceBox.getSelectionModel().getSelectedItem();
                switch (periode) {
                    case "Toutes":
                        paiements = new PaiementDAO().readAllByRubrique(rubrique);
                        break;
                    case "Aujourd'hui":
                        paiements = new PaiementDAO().readAllByDateAndRubrique(rubrique);
                        break;
                    case "Hebdomadaire":
                        paiements = new PaiementDAO().readAllByWeekAndRubrique(rubrique);
                        break;
                    case "Mensuelle":
                        paiements = new PaiementDAO().readAllByMonthAndRubrique(rubrique);
                        break;
                }
            }
            List<PaiementTableViewModel> paiementTableViewModels = new ArrayList<>();
            for (Paiement paiement : paiements) {
                paiementTableViewModels.add(new PaiementTableViewModel(paiement, new RubriqueDAO().read(paiement.getId_rubrique()), new EleveDAO().read(paiement.getId_eleve())));
            }
            paiementTable.setItems(paiementTableViewModels.isEmpty() ? null : FXCollections.observableArrayList(paiementTableViewModels));
        } catch (Exception e) {
            System.out.println("In PaiementController-updatePaiementTable() method");
            e.printStackTrace();
            Alerts.showError(parentStage, e.getMessage()+e.getCause());
        }
    }
    
    public void initChoiceBoxes() {
        try {
            rubriqueChoiceBox.getItems().clear();
            List<String> rubriques = new RubriqueDAO().readAllIntitule();
            rubriqueChoiceBox.getItems().add("Toutes");
            rubriqueChoiceBox.getItems().addAll(rubriques);
            rubriqueChoiceBox.getSelectionModel().selectFirst();
            
            periodeChoiceBox.getItems().clear();
            periodeChoiceBox.getItems().add("Toutes");
            if(Constantes.CURRENT_YEAR.getId() == new AnneeScolaireDAO().readLastId()) {
                periodeChoiceBox.getItems().add("Aujourd'hui");
                periodeChoiceBox.getItems().add("Hebdomadaire");
                periodeChoiceBox.getItems().add("Mensuelle");
            }
            periodeChoiceBox.getSelectionModel().selectFirst();
        } catch (DAOException e) {
            System.out.println("In PaiementController-initRubriqueChoiceBox() method");
            e.printStackTrace();
            Alerts.showError(parentStage, e.getMessage()+e.getCause());
        }
    }
}
