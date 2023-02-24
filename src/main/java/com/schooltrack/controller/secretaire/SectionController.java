package com.schooltrack.controller.secretaire;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.models.Classe;
import com.schooltrack.models.Eleve;
import com.schooltrack.models.Matiere;
import com.schooltrack.models.Section;
import com.schooltrack.models.datasource.SectionDAO;
import com.schooltrack.utils.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class SectionController {
    @FXML
    private TableColumn<Eleve, String> adresseColumn;

    @FXML
    private ChoiceBox<String> classeChoiceBox;

    @FXML
    private TableColumn<Matiere, Integer> coefColumn;

    @FXML
    private TableColumn<Eleve, LocalDate> dtNaissColumn;

    @FXML
    private Tab eleveTab;

    @FXML
    private TableView<Eleve> eleveTable;

    @FXML
    private TableColumn<Eleve, String> emailColumn;

    @FXML
    private TableColumn<Eleve, Integer> idColumn;

    @FXML
    private TableColumn<Matiere, Integer> idMatColumn;

    @FXML
    private TableColumn<Eleve, String> lieuNaissColumn;

    @FXML
    private TableColumn<Matiere, String> matColumn;

    @FXML
    private TableView<Matiere> matTable;

    @FXML
    private TableColumn<Eleve, String> nomColumn;

    @FXML
    private TableColumn<Eleve, String> numColumn;

    @FXML
    private TableColumn<Eleve, String> prenomColumn;

    @FXML
    private Tab programmeTab;

    @FXML
    private ChoiceBox<String> sectionChoiceBox;

    @FXML
    private TableColumn<Eleve, String> sexeColumn;
    
    @FXML
    private AnchorPane sectionLayout;
    private List<Section> sections;
    
    
    

    @FXML
    void addMat(ActionEvent event) {

    }

    @FXML
    void deleteMat(ActionEvent event) {

    }

    @FXML
    void editMat(ActionEvent event) {

    }

    @FXML
    void handleAddEleve(ActionEvent event) {

    }

    @FXML
    void handleBulletin(ActionEvent event) {

    }

    @FXML
    void handleCertificat(ActionEvent event) {

    }

    @FXML
    void handleDeleteEleve(ActionEvent event) {

    }

    @FXML
    void handleEditEleve(ActionEvent event) {

    }

    @FXML
    void importCsv(ActionEvent event) {

    }
    
    @FXML
    private void initialize() {
        // Map the columns of the eleveTable to the properties of the Eleve class
        mapEleveTableToData();
        // Map the columns of the matTable to the properties of the Matiere class
        mapMatTableToData();
        // add listener to sectionChoiceBox and update the classeChoiceBox when the section is changed then update the eleveTable
        sectionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        
            // update the classeChoiceBox
            updateClasseChoiceBox();
            // update the eleveTable
            updateEleveTable();
            // update the matTable
            updateMatTable();
        });
    
        // add listener to classeChoiceBox and update the eleveTable when the classe is changed
        classeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null)
                updateEleveTable();
        });
    }
    
    private void mapMatTableToData() {
        idMatColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        matColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        coefColumn.setCellValueFactory(cellData -> cellData.getValue().coefficientProperty().asObject());
    }
    
    private void mapEleveTableToData() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        sexeColumn.setCellValueFactory(cellData -> cellData.getValue().sexeProperty());
        dtNaissColumn.setCellValueFactory(cellData -> cellData.getValue().dateDeNaissanceProperty());
        lieuNaissColumn.setCellValueFactory(cellData -> cellData.getValue().lieuDeNaissanceProperty());
        adresseColumn.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
        numColumn.setCellValueFactory(cellData -> cellData.getValue().numtelephoneProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
    }
    
    private void updateMatTable() {
        matTable.getItems().clear();
        matTable.getItems().addAll(sections.get(sectionChoiceBox.getSelectionModel().getSelectedIndex()).getClasses().get(classeChoiceBox.getSelectionModel().getSelectedIndex()).getMatieres());
    }
    
    private void updateClasseChoiceBox() {
        classeChoiceBox.getItems().clear();
        for(Classe classe : sections.get(sectionChoiceBox.getSelectionModel().getSelectedIndex()).getClasses()) {
            classeChoiceBox.getItems().add(classe.getNom());
        }
        //choisir la première classe par défaut
        classeChoiceBox.getSelectionModel().selectFirst();
    }
    
    private void updateEleveTable() {
        eleveTable.getItems().clear();
        eleveTable.getItems().addAll(sections.get(sectionChoiceBox.getSelectionModel().getSelectedIndex()).getClasses().get(classeChoiceBox.getSelectionModel().getSelectedIndex()).getEleves());
    }
    
    public void initSectionChoiceBox() {
        try {
            sections = new SectionDAO().readAll();
        } catch (DAOException e) {
            Alerts.showError((Stage) sectionLayout.getScene().getWindow(), "Erreur lors de la lecture des sections"+e.getMessage());
        } catch (Exception e) {
            Alerts.showError((Stage) sectionLayout.getScene().getWindow(), e.getMessage());
        }
        for (Section section : sections) {
            sectionChoiceBox.getItems().add(section.getIntitule());
        }
        //choisir la première section par défaut
        sectionChoiceBox.getSelectionModel().selectFirst();
    }
}
