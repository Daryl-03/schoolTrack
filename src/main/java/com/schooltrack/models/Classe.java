package com.schooltrack.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.List;

public class Classe {
    private IntegerProperty id;
    private StringProperty nom;
    private ListProperty<Rubrique> rubriques;
    private ListProperty<Matiere> matieres;
    private ListProperty<Eleve> eleves;
    
    public int getId() {
        return id.get();
    }
    
    public IntegerProperty idProperty() {
        return id;
    }
    
    public void setId(int id) {
        this.id.set(id);
    }
    
    public String getNom() {
        return nom.get();
    }
    
    public StringProperty nomProperty() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom.set(nom);
    }
    
    public ObservableList<Rubrique> getRubriques() {
        return rubriques.get();
    }
    
    public ListProperty<Rubrique> rubriquesProperty() {
        return rubriques;
    }
    
    public void setRubriques(ObservableList<Rubrique> rubriques) {
        this.rubriques.set(rubriques);
    }
    
    public ObservableList<Matiere> getMatieres() {
        return matieres.get();
    }
    
    public ListProperty<Matiere> matieresProperty() {
        return matieres;
    }
    
    public void setMatieres(ObservableList<Matiere> matieres) {
        this.matieres.set(matieres);
    }
    
    public ObservableList<Eleve> getEleves() {
        return eleves.get();
    }
    
    public ListProperty<Eleve> elevesProperty() {
        return eleves;
    }
    
    public void setEleves(ObservableList<Eleve> eleves) {
        this.eleves.set(eleves);
    }
    
    public Classe(Integer id, Section section, String nom, ObservableList<Rubrique> rubriques) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.rubriques = new SimpleListProperty<Rubrique>(rubriques);
    }
    
    public Classe(Integer id, String nom, ObservableList<Rubrique> rubriques, ObservableList<Matiere> matieres, ObservableList<Eleve> eleves) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.rubriques = new SimpleListProperty<Rubrique>(rubriques);
        this.matieres = new SimpleListProperty<Matiere>(matieres);
        this.eleves = new SimpleListProperty<Eleve>(eleves);
    }
}
