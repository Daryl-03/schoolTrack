package com.schooltrack.controller.secretaire;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.models.*;
import com.schooltrack.models.datasource.*;
import com.schooltrack.utils.Alerts;
import com.schooltrack.utils.Constantes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private TableColumn<Eleve, String> matriculeColumn;

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
    
    private int getClasseId(){
        return sections.get(getSectionId()-1).getClasses().get(getClasseIndex()).getId();
    }
    
    private int getClasseIndex(){
        return classeChoiceBox.getSelectionModel().getSelectedIndex();
    }
    
    private int getSectionId(){
        return sectionChoiceBox.getSelectionModel().getSelectedIndex()+1;
    }
    
    private Stage getParentStage() {
        return (Stage) sectionLayout.getScene().getWindow();
    }

    @FXML
    private void addMat(ActionEvent event) {
        try{
            if (Constantes.CURRENT_YEAR.getId() == new AnneeScolaireDAO().readLastId())  {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/schooltrack/view/secretaire/MatiereEdit.fxml"));
                AnchorPane matiereEdit = loader.load();
        
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Ajouter une matière");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(getParentStage());
                Scene scene = new Scene(matiereEdit);
                dialogStage.setScene(scene);
                dialogStage.setResizable(false);
        
                MatiereEditController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                dialogStage.showAndWait();
                if (controller.isOkClicked()) {
                    Matiere matiere = controller.getMatiere();
                    matiere.setId_classe(getClasseId());
                    MatiereDAO matiereDAO = new MatiereDAO();
                    matiereDAO.create(matiere);
                    updateMatTable();
                    // mettre à jour les notes des bulletins des élèves de la classe
                    for (Eleve eleve : sections.get(getSectionId()-1).getClasses().get(getClasseIndex()).getEleves()){
                        for (Bulletin bulletin : eleve.getBulletins()){
                            new NoteDAO().generateNotes(bulletin.getId());
                        }
                    }
                    updateEleveTable();
                    Alerts.showInfo(getParentStage(), "La matière a été ajoutée avec succès");
                }
            } else {
                Alerts.showError(getParentStage(), "Vous ne pouvez pas ajouter une matière à une année scolaire passée");
            }
        } catch (Exception e){
            System.out.println("In Secretaire-addMat() method");
            e.printStackTrace();
            Alerts.showError(getParentStage(), e.getMessage()+e.getCause());
        }
    }

    @FXML
    private void deleteMat(ActionEvent event) {
        if (matTable.getSelectionModel().getSelectedItem() != null){
            if(!Alerts.showConfirmation(getParentStage(), "Voulez-vous vraiment supprimer cette matière?"))
                return;
            try {
                    if (Constantes.CURRENT_YEAR.getId() == new AnneeScolaireDAO().readLastId()) {
                        Matiere matiere = matTable.getSelectionModel().getSelectedItem();
                        if (matiere != null) {
                            MatiereDAO matiereDAO = new MatiereDAO();
                            matiereDAO.delete(matiere.getId());
                            updateMatTable();
                            Alerts.showInfo(getParentStage(), "La matière a été supprimée avec succès");
                        } else {
                            Alerts.showError(getParentStage(), "Veuillez sélectionner une matière");
                        }
                    } else {
                        Alerts.showError(getParentStage(), "Vous ne pouvez pas supprimer une matière d'une année scolaire passée");
                    }
            } catch (Exception e) {
                System.out.println("In Secretaire-deleteMat() method");
                e.printStackTrace();
                Alerts.showError(getParentStage(), e.getMessage() + e.getCause());
            }
        } else {
            Alerts.showError(getParentStage(), "Veuillez sélectionner une matière");
        }
    }

    @FXML
    private void editMat(ActionEvent event) {
        if (matTable.getSelectionModel().getSelectedItem() != null){
            try {
                if (Constantes.CURRENT_YEAR.getId() == new AnneeScolaireDAO().readLastId()) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/schooltrack/view/secretaire/MatiereEdit.fxml"));
                    AnchorPane matiereEdit = loader.load();
        
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Modifier une matière");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.initOwner(getParentStage());
                    Scene scene = new Scene(matiereEdit);
                    dialogStage.setScene(scene);
                    dialogStage.setResizable(false);
        
                    MatiereEditController controller = loader.getController();
                    controller.setDialogStage(dialogStage);
                    controller.setMatiere(matTable.getSelectionModel().getSelectedItem());
                    dialogStage.showAndWait();
                    if (controller.isOkClicked()) {
                        Matiere matiere = controller.getMatiere();
                        matiere.setId_classe(getClasseId());
                        MatiereDAO matiereDAO = new MatiereDAO();
                        matiereDAO.update(matiere);
                        updateMatTable();
                        Alerts.showInfo(getParentStage(), "La matière a été modifiée avec succès");
                    }
                } else {
                    Alerts.showError(getParentStage(), "Vous ne pouvez pas modifier une matière d'une année scolaire passée");
                }
            } catch (Exception e) {
                System.out.println("In Secretaire-editMat() method");
                e.printStackTrace();
                Alerts.showError(getParentStage(), e.getMessage() + e.getCause());
            }
        } else {
            Alerts.showError(getParentStage(), "Veuillez sélectionner une matière");
        }
    }

    @FXML
    private void handleAddEleve(ActionEvent event) {
        try {
            if(Constantes.CURRENT_YEAR.getId() == new AnneeScolaireDAO().readLastId()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/schooltrack/view/secretaire/EleveEdit.fxml"));
                AnchorPane eleveEdit = loader.load();
        
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Ajouter un élève");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(getParentStage());
                Scene scene = new Scene(eleveEdit);
                dialogStage.setScene(scene);
                dialogStage.setResizable(false);
        
                EleveEditController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                dialogStage.showAndWait();
                if (controller.isOkClicked()) {
                    Eleve eleve = controller.getEleve();
                    eleve.setId_classe(getClasseId());
                    new EleveDAO().create(eleve);
                    updateEleveTable();
                    Alerts.showInfo(getParentStage(),"L'élève a été ajouté avec succès");
                }
            } else {
                Alerts.showError(getParentStage(), "Vous ne pouvez pas ajouter d'élève pour cette année scolaire");
            }
        } catch (Exception e){
            System.out.println("In Secretaire-handleAddEleve() method");
            e.printStackTrace();
            Alerts.showError(getParentStage(), e.getMessage()+e.getCause());
        }
    }

    @FXML
    private void handleBulletin(ActionEvent event) {
        try {
            if (eleveTable.getSelectionModel().getSelectedItem() != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/schooltrack/view/secretaire/Bulletin.fxml"));
                AnchorPane bulletin = loader.load();
        
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Bulletin de l'élève");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(getParentStage());
                Scene scene = new Scene(bulletin);
                dialogStage.setScene(scene);
                dialogStage.setResizable(false);
        
                BulletinController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                Eleve eleve = eleveTable.getSelectionModel().getSelectedItem();
                // générer les notes des bulletins de l'élève
                for (int i = 0; i < eleve.getBulletins().size(); i++) {
                    new NoteDAO().generateNotes(eleve.getBulletins().get(i).getId());
                }
                eleve.setBulletins((ObservableList<Bulletin>) new BulletinDAO().readAllByYear(eleve.getId(), Constantes.CURRENT_YEAR.getId()));
                controller.setEleve(eleve);
                controller.setClasse(new ClasseDAO().read(getClasseId()));
                controller.initLayoutFeatures();
                dialogStage.showAndWait();
            } else {
                Alerts.showError(getParentStage(), "Veuillez sélectionner un élève");
            }
        } catch (Exception e){
            System.out.println("In Secretaire-handleBulletin() method");
            e.printStackTrace();
            Alerts.showError(getParentStage(), e.getMessage()+e.getCause());
        }
    }

    @FXML
    private void handleDeleteEleve(ActionEvent event) {
        try {
            if(eleveTable.getSelectionModel().getSelectedItem() != null && Constantes.CURRENT_YEAR.getId() == new AnneeScolaireDAO().readLastId() && eleveTable.getSelectionModel().getSelectedItem() != null) {
                if (Alerts.showConfirmation(getParentStage(), "Voulez-vous vraiment supprimer cet élève?")) {
                    try {
                        Eleve eleve = eleveTable.getSelectionModel().getSelectedItem();
                        new EleveDAO().retirer(eleve.getId());
                        updateEleveTable();
                        Alerts.showInfo(getParentStage(), "L'élève a été supprimé avec succès");
                    } catch (DAOException e) {
                        Alerts.showError(getParentStage(), e.getMessage());
                    }
                }
            } else if(eleveTable.getSelectionModel().getSelectedItem() == null){
                Alerts.showError(getParentStage(), "Veuillez sélectionner un élève");
            } else {
                Alerts.showError(getParentStage(), "Vous ne pouvez pas supprimer un élève d'une année scolaire passée");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showError(getParentStage(), e.getMessage()+" in "+getClass().getSimpleName());
        }
    }

    @FXML
    private void handleEditEleve(ActionEvent event) {
        try {
            if(eleveTable.getSelectionModel().getSelectedItem() != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/schooltrack/view/secretaire/EleveEdit.fxml"));
                AnchorPane eleveEdit = loader.load();
                
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Modifier un élève");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(getParentStage());
                Scene scene = new Scene(eleveEdit);
                dialogStage.setScene(scene);
                dialogStage.setResizable(false);
                
                EleveEditController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setEleve(eleveTable.getSelectionModel().getSelectedItem());
                dialogStage.showAndWait();
                if(controller.isOkClicked()){
                    Eleve eleve = controller.getEleve();
                    eleve.setId_classe(getClasseId());
                    new EleveDAO().update(eleve);
                    updateEleveTable();
                    Alerts.showInfo(getParentStage(), "L'élève a été modifié avec succès");
                }
            } else {
                Alerts.showError(getParentStage(), "Veuillez sélectionner un élève");
            }
        } catch (Exception e){
            System.out.println("In Secretaire-handleEditEleve() method");
            e.printStackTrace();
            Alerts.showError(getParentStage(), e.getMessage()+e.getCause());
        }
    }
    
    @FXML
    private void handlePaiement(){
    }

    @FXML
    private void importCsv(ActionEvent event) {

    }
    
    @FXML
    private void initialize() {
        // Map the columns of the eleveTable to the properties of the Eleve class
        mapEleveTableToData();
        // Map the columns of the matTable to the properties of the Matiere class
        mapMatTableToData();
        // add listener to sectionChoiceBox and update the classeChoiceBox when the section is changed then update the eleveTable
        sectionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("Section changed from "+oldValue+" to "+newValue);
            // update the classeChoiceBox
            updateClasseChoiceBox();
        });
    
        // add listener to classeChoiceBox and update the eleveTable when the classe is changed
        classeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("Classe changed from "+oldValue+" to "+newValue);
            if (newValue != null){
                updateEleveTable();
                updateMatTable();
            }
        });
    }
    
    private void mapMatTableToData() {
        idMatColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        matColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        coefColumn.setCellValueFactory(cellData -> cellData.getValue().coefficientProperty().asObject());
    }
    
    private void mapEleveTableToData() {
        matriculeColumn.setCellValueFactory(cellData -> cellData.getValue().matriculeProperty());
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
        int sectionId = getSectionId();
        int classeId = getClasseId();
        List<Matiere> matieres = null;
        try {
            matieres = new MatiereDAO().readAllByClasse(classeId); //récupérer les matières de la classe
            sections.get(sectionId-1).getClasses().get(getClasseIndex()).setMatieres(matieres!=null?FXCollections.observableArrayList(matieres):FXCollections.observableArrayList()); //mettre à jour la liste des matières de la classe
            matTable.getItems().clear();
            matTable.getItems().addAll(sections.get(sectionId-1).getClasses().get(getClasseIndex()).getMatieres());
        } catch (Exception e) {
            Alerts.showError(getParentStage(), e.getMessage()+e.getCause());
        }
    }
    
    private void updateClasseChoiceBox() {
        List<String> classes = new ArrayList<>();
        int sectionId = getSectionId();
        for (Classe classe : sections.get(sectionId-1).getClasses()) {
            classes.add(classe.getNom());
        }
        classeChoiceBox.setItems(FXCollections.observableArrayList(classes));
        //choisir la première classe par défaut
        classeChoiceBox.getSelectionModel().selectFirst();
    }
    
    private void updateEleveTable() {
        int sectionId = getSectionId();
        List<Eleve> eleves = null;
        try {
            eleves = new EleveDAO().readAllByClasse(getClasseId(),Constantes.CURRENT_YEAR.getId());
             //récupérer les élèves de la classe
            sections.get(sectionId-1).getClasses().get(getClasseIndex()).setEleves(eleves!=null?FXCollections.observableArrayList(eleves):FXCollections.observableArrayList()); //mettre à jour la liste des élèves de la classe
            eleveTable.getItems().clear();
            eleveTable.getItems().addAll(sections.get(sectionId-1).getClasses().get(getClasseIndex()).getEleves()); //mettre à jour la table des élèves
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showError(getParentStage(), e.getMessage()+" in "+getClass().getSimpleName());
        }
    }
    
    public void initSectionChoiceBox() {
        try {
            sections = new SectionDAO().readAll(Constantes.CURRENT_YEAR.getId());
        } catch (DAOException e) {
            Alerts.showError(getParentStage(), "Erreur lors de la lecture des sections"+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showError(getParentStage(), e.getMessage()+" in "+getClass().getSimpleName());
        }
        for (Section section : sections) {
            sectionChoiceBox.getItems().add(section.getIntitule());
        }
        //choisir la première section par défaut
        sectionChoiceBox.getSelectionModel().selectFirst();
    }
}
