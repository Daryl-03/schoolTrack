package com.schooltrack.models;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class Classe {
    private IntegerProperty id;
    private StringProperty nom;
    private ListProperty<Rubrique> rubriques;
    private ListProperty<Matiere> matieres;
    private ListProperty<Eleve> eleves;
    private IntegerProperty id_section;
    
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
    
    public int getId_section() {
        return id_section.get();
    }
    
    public IntegerProperty id_sectionProperty() {
        return id_section;
    }
    
    public void setId_section(int id_section) {
        this.id_section.set(id_section);
    }
    
    public Classe() {
        this.id = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty();
        this.rubriques = new SimpleListProperty<Rubrique>();
        this.matieres = new SimpleListProperty<Matiere>();
        this.eleves = new SimpleListProperty<Eleve>();
        this.id_section = new SimpleIntegerProperty();
        
    }
    
    public Classe(Integer id, Section section, String nom, ObservableList<Rubrique> rubriques) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.rubriques = new SimpleListProperty<Rubrique>(rubriques);
    }
    
    public Classe(int id, String nom, ObservableList<Rubrique> rubriques, ObservableList<Matiere> matieres, ObservableList<Eleve> eleves, int id_section) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.rubriques = new SimpleListProperty<Rubrique>(rubriques);
        this.matieres = new SimpleListProperty<Matiere>(matieres);
        this.eleves = new SimpleListProperty<Eleve>(eleves);
        this.id_section = new SimpleIntegerProperty(id_section);
    }
    
    @Override
    public String toString() {
        return "Classe{" +
                "id=" + id +
                ", nom=" + nom +
                ", rubriques=" + rubriques +
                ", matieres=" + matieres +
                ", eleves=" + eleves +
                ", id_section=" + id_section +
                '}';
    }
}
