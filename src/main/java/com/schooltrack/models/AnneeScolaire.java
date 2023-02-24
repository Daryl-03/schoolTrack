package com.schooltrack.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class AnneeScolaire {
    private IntegerProperty id;
    private StringProperty intitule;
    private ObjectProperty<LocalDate> dateDebut;
    private ObjectProperty<LocalDate> dateFin;
    
    public AnneeScolaire() {
        this.id = new SimpleIntegerProperty();
        this.intitule = new SimpleStringProperty();
        this.dateDebut = new SimpleObjectProperty<>();
        this.dateFin = new SimpleObjectProperty<>();
    }
    
    public int getId() {
        return id.get();
    }
    
    public IntegerProperty idProperty() {
        return id;
    }
    
    public void setId(int id) {
        this.id.set(id);
    }
    
    public String getIntitule() {
        return intitule.get();
    }
    
    public StringProperty intituleProperty() {
        return intitule;
    }
    
    public void setIntitule(String intitule) {
        this.intitule.set(intitule);
    }
    
    public LocalDate getDateDebut() {
        return dateDebut.get();
    }
    
    public ObjectProperty<LocalDate> dateDebutProperty() {
        return dateDebut;
    }
    
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut.set(dateDebut);
    }
    
    public LocalDate getDateFin() {
        return dateFin.get();
    }
    
    public ObjectProperty<LocalDate> dateFinProperty() {
        return dateFin;
    }
    
    public void setDateFin(LocalDate dateFin) {
        this.dateFin.set(dateFin);
    }
    
    public AnneeScolaire(int id, String intitule, LocalDate dateDebut, LocalDate dateFin) {
        this.id = new SimpleIntegerProperty(id);
        this.intitule = new SimpleStringProperty(intitule);
        this.dateDebut = new SimpleObjectProperty<>(dateDebut);
        this.dateFin = new SimpleObjectProperty<>(dateFin);
    }
    
    @Override
    public String toString() {
        return "AnneeScolaire{" +
                "id=" + id.get() +
                ", intitule=" + intitule.get() +
                ", dateDebut=" + dateDebut.get() +
                ", dateFin=" + dateFin.get() +
                '}';
    }
}
