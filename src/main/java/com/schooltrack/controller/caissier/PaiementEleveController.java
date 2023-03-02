package com.schooltrack.controller.caissier;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.models.Eleve;
import com.schooltrack.models.Paiement;
import com.schooltrack.models.PaiementTableViewModel;
import com.schooltrack.models.Rubrique;
import com.schooltrack.models.datasource.PaiementDAO;
import com.schooltrack.models.datasource.RubriqueDAO;
import com.schooltrack.utils.Alerts;
import com.schooltrack.utils.Constantes;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaiementEleveController {
    @FXML
    private TableColumn<PaiementTableViewModel, LocalDate> dateColumn;

    @FXML
    private TableView<PaiementTableViewModel> elevePaiementTable;

    @FXML
    private Label inscriptionCountLabel;

    @FXML
    private Label inscriptionStatusLabel;

    @FXML
    private TableColumn<PaiementTableViewModel, Double> montantColumn;

    @FXML
    private TableColumn<PaiementTableViewModel, String> numeroColumn;

    @FXML
    private TableColumn<PaiementTableViewModel, String> observationColumn;

    @FXML
    private TableColumn<PaiementTableViewModel, String> rubriqueColumn;

    @FXML
    private Label scolariteCountLabel;

    @FXML
    private Label scolariteStatusLabel;
    
    private PaiementDAO paiementDAO = new PaiementDAO();
    private RubriqueDAO rubriqueDAO = new RubriqueDAO();
    
    private Stage dialogStage;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    private Eleve eleve;
    
    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }
    
    @FXML
    private void handleClose(){
        dialogStage.close();
    }
    
    @FXML
    private void handlePrint(){
    }
    
    @FXML
    private void initialize() {
        // faire le mapping des données
        numeroColumn.setCellValueFactory(cellData -> cellData.getValue().getPaiement().numeroProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getPaiement().dateProperty());
        rubriqueColumn.setCellValueFactory(cellData -> cellData.getValue().getRubrique().intituleProperty());
        montantColumn.setCellValueFactory(cellData -> cellData.getValue().getPaiement().montantProperty().asObject());
        observationColumn.setCellValueFactory(cellData -> cellData.getValue().getPaiement().observationProperty());
    }
    
    public void loadFeatures() {
        try {
            // inscription
            if(paiementDAO.isPayedInscription(eleve.getId())) {
                inscriptionStatusLabel.setText("Payé");
                inscriptionStatusLabel.setStyle("-fx-text-fill: green");
                inscriptionCountLabel.setText("1");
                if(eleve.getId_classe()!= Constantes.ID_CRECHE){
                    scolariteStatusLabel.setText("Payé");
                    scolariteStatusLabel.setStyle("-fx-text-fill: green");
                    scolariteCountLabel.setText("10");
                }
            }
            else {
                inscriptionCountLabel.setText("0");
                inscriptionStatusLabel.setText("Non payé");
                inscriptionStatusLabel.setStyle("-fx-text-fill: red");
                scolariteCountLabel.setText("0");
                scolariteStatusLabel.setText("");
            }
            
            // scolarité
            if(eleve.getId_classe()!= Constantes.ID_CRECHE) {
                int nombrePaiementScolarite = paiementDAO.countScolarite(eleve.getId());
                scolariteCountLabel.setText(String.valueOf(nombrePaiementScolarite));
                if (nombrePaiementScolarite < 9) {
                    Rubrique rubriqueScolarite = rubriqueDAO.readByIntituleAndMatricule("Scolarité", eleve.getMatricule());
                    if (LocalDate.now().isAfter(rubriqueScolarite.getEcheancier().getEcheances().get(nombrePaiementScolarite))) {
                        scolariteStatusLabel.setText("En retard");
                        scolariteStatusLabel.setStyle("-fx-text-fill: red");
                    } else {
                        scolariteStatusLabel.setText("En règle");
                        scolariteStatusLabel.setStyle("-fx-text-fill: green");
                    }
                } else {
                    scolariteStatusLabel.setText("Payé");
                    scolariteStatusLabel.setStyle("-fx-text-fill: green");
                }
            }
            
            // remplissage de la table
            List<PaiementTableViewModel> paiementTableViewModels = new ArrayList<>();
            for (Paiement paiement : eleve.getPaiements()){
                PaiementTableViewModel paiementTableViewModel = new PaiementTableViewModel(paiement, rubriqueDAO.read(paiement.getId_rubrique()), eleve);
                paiementTableViewModels.add(paiementTableViewModel);
            }
            elevePaiementTable.setItems(paiementTableViewModels.size()==0? FXCollections.observableArrayList():FXCollections.observableArrayList(paiementTableViewModels));
        } catch (DAOException e) {
            System.out.println("Erreur lors du chargement des paiements d'inscription de l'élève");
            e.printStackTrace();
            Alerts.showError(dialogStage, "Erreur lors du chargement des paiements d'inscription de l'élève :"+e.getMessage());
        }
    }
}
