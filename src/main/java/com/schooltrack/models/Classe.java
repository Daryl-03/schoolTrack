package com.schooltrack.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Classe {
    private IntegerProperty id;
    private StringProperty nom;
    private ListProperty<Rubrique> rubriques;
    
    public int getId() {
        return id.get();
    }
    
    public IntegerProperty idProperty() {
        return id;
    }
    
    public void setId(int id) {
        this.id.set(id);
    }
    
    public Classe(Integer id, Section section, String nom, ObservableList<Rubrique> rubriques) {
        
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.rubriques = new SimpleListProperty<Rubrique>(rubriques);
    }
    
    
}
