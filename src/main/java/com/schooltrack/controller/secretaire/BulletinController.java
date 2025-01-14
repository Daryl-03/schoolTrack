package com.schooltrack.controller.secretaire;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.PDFException;
import com.schooltrack.models.Bulletin;
import com.schooltrack.models.Classe;
import com.schooltrack.models.Eleve;
import com.schooltrack.models.Note;
import com.schooltrack.models.datasource.NoteDAO;
import com.schooltrack.pdf.BulletinGenerator;
import com.schooltrack.utils.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.io.File;

public class BulletinController {
    
    private Stage dialogStage;
    private Eleve eleve;
    private Classe classe;
    
    @FXML
    private Label PrenomLabel;
    
    @FXML
    private TableColumn<Note, Integer> coefColumn;
    
    @FXML
    private Label idLabel;
    
    @FXML
    private TableColumn<Note, String> matColumn;
    
    @FXML
    private TextField moyenneField;
    
    @FXML
    private Label nomLabel;
    
    @FXML
    private TableColumn<Note, Double> noteColumn;
    
    @FXML
    private TableView<Note> notesTable;
    
    @FXML
    private AnchorPane rootLayout;
    private int indexEleve;
    
    @FXML
    private ChoiceBox<String> trimestreChoiceBox;
    
    @FXML
    private void handleExit(ActionEvent event) {
        if (Alerts.showConfirmation(dialogStage, "Voulez-vous vraiment quitter ?")) {
            dialogStage.close();
        }
    }
    
    @FXML
    private void handleSave(ActionEvent event) {
        // vérifier si les notes sont des nombres et qu'elles sont comprises entre 0 et 20
        for(Note note : notesTable.getItems()) {
            if(note.getValeur() < 0 || note.getValeur() > 20) {
                Alerts.showError(dialogStage, "Valeur de note incorrecte :\n La note doit être comprise entre 0 et 20");
                return;
            }
        }
        noteColumn.setEditable(false);
        // sauvegarder les notes dans la base de données
        try {
            NoteDAO noteDAO = new NoteDAO();
            for(Note note : notesTable.getItems()) {
                noteDAO.update(note);
            }
            classe.getEleves().get(indexEleve).getBulletins().get(trimestreChoiceBox.getSelectionModel().getSelectedIndex()).setNotes(notesTable.getItems());
            // mise à jour de la moyenne
            moyenneField.setText(String.valueOf(eleve.getBulletins().get(trimestreChoiceBox.getSelectionModel().getSelectedIndex()).getMoyenne()));
            Alerts.showInfo(dialogStage, "Sauvegarde effectuée avec succès");
        } catch (DAOException e) {
            System.out.println("Erreur lors de la sauvegarde des notes");
            e.printStackTrace();
            Alerts.showError(dialogStage, "Erreur lors de la sauvegarde des notes");
        }
    }
    
    @FXML
    private void handlePrint(ActionEvent event) {
        // vérifier si le bulletin n'est pas vide
        if(notesTable.getItems().isEmpty()) {
            Alerts.showError(dialogStage, "Le bulletin est vide");
            return;
        }
        // Prompt user to choose a file name.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le bulletin");
        fileChooser.setInitialFileName("Bulletin_" + eleve.getNom() + "_" + eleve.getPrenom() + ".pdf");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        // Show save file dialog
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(dialogStage);
        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".pdf")) {
                file = new File(file.getPath() + ".pdf");
            }
            // générer le bulletin
            try {
                BulletinGenerator bulletinGenerator = new BulletinGenerator();
                Bulletin bulletin = eleve.getBulletins().get(trimestreChoiceBox.getSelectionModel().getSelectedIndex());
                String path = file.getAbsolutePath();
                bulletinGenerator.generate(path, bulletin);
                Alerts.showInfo(dialogStage, "Bulletin généré avec succès");
            } catch (PDFException e) {
                System.out.println("Erreur lors de la génération du bulletin");
                e.printStackTrace();
                Alerts.showError(dialogStage, "Erreur lors de la génération du bulletin");
            }
        }
    }
    
    @FXML
    private void handleEdit(ActionEvent event) {
        // rends les champs de la colonne notes editable
        notesTable.setEditable(true);
        noteColumn.setEditable(true);
        noteColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    }
    
    @FXML
    private void leftBulletin(ActionEvent event) {
        if(indexEleve > 0) {
            indexEleve--;
        } else {
            indexEleve = classe.getEleves().size() - 1;
        }
        eleve = classe.getEleves().get(indexEleve);
        initLayoutFeatures();
        updateNotesTable();
    }
    
    @FXML
    private void rightBulletin(ActionEvent event) {
        if(indexEleve < classe.getEleves().size() - 1) {
            indexEleve++;
        } else {
            indexEleve = 0;
        }
        eleve = classe.getEleves().get(indexEleve);
        initLayoutFeatures();
        updateNotesTable();
    }
    
    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    
    public void setIndexEleve(int indexEleve) {
        this.indexEleve = indexEleve;
    }
    
    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    @FXML
    private void initialize() {
        // faire le mapping des colonnes
        matColumn.setCellValueFactory(cellData -> cellData.getValue().getMatiere().nomProperty());
        noteColumn.setCellValueFactory(cellData -> cellData.getValue().valeurProperty().asObject());
        coefColumn.setCellValueFactory(cellData -> cellData.getValue().getMatiere().coefficientProperty().asObject());
    
        // ajout du listener sur le choix du trimestre
        trimestreChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateNotesTable();
        });
    
        trimestreChoiceBox.getItems().addAll("1er Trimestre", "2eme Trimestre", "3eme Trimestre");
        
        // vérifier la validité de la valeur saisie dans la colonne note lors de la soumission
        noteColumn.setOnEditCommit((TableColumn.CellEditEvent<Note, Double> t) -> {
            // vérifier que la note est comprise entre 0 et 20
            if(t.getNewValue() < 0 || t.getNewValue() > 20) {
                Alerts.showError(dialogStage, "Valeur de note incorrecte :\n La note doit être comprise entre 0 et 20");
                return;
            }
        });
    }
    
    private void updateNotesTable() {
        // charger la tableview avec les notes du trimestre choisi
        notesTable.getItems().clear();
        notesTable.getItems().addAll(eleve.getBulletins().get(trimestreChoiceBox.getSelectionModel().getSelectedIndex()).getNotes());
        // mise à jour de la moyenne
        moyenneField.setText(String.valueOf(eleve.getBulletins().get(trimestreChoiceBox.getSelectionModel().getSelectedIndex()).getMoyenne()));
    }
    
    public void initLayoutFeatures() {
        nomLabel.setText(eleve.getNom());
        PrenomLabel.setText(eleve.getPrenom());
        idLabel.setText(String.valueOf(eleve.getId()));
        trimestreChoiceBox.getSelectionModel().selectFirst();
        moyenneField.setText(String.valueOf(eleve.getBulletins().get(trimestreChoiceBox.getSelectionModel().getSelectedIndex()).getMoyenne()));
    }
}
