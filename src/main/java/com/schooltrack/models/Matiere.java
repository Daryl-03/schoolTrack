package com.schooltrack.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Matiere {
	private IntegerProperty id;
	private StringProperty nom;
	private StringProperty description;
	private IntegerProperty coefficient;
	private IntegerProperty id_classe;

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

	public int getCoefficient() {
		return coefficient.get();
	}

	public IntegerProperty coefficientProperty() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient.set(coefficient);
	}
	
	public int getId_classe() {
		return id_classe.get();
	}
	
	public IntegerProperty id_classeProperty() {
		return id_classe;
	}
	
	public void setId_classe(int id_classe) {
		this.id_classe.set(id_classe);
	}
	
	public String getDescription() {
		return description.get();
	}
	
	public StringProperty descriptionProperty() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description.set(description);
	}
	
	public Matiere() {
		this.id = new SimpleIntegerProperty();
		this.nom = new SimpleStringProperty();
		this.description = new SimpleStringProperty();
		this.coefficient = new SimpleIntegerProperty();
		this.id_classe = new SimpleIntegerProperty();
	}
	
	public Matiere(int id, String nom, String description, int coefficient, int id_classe) {
		this.id = new SimpleIntegerProperty(id);
		this.nom = new SimpleStringProperty(nom);
		this.description = new SimpleStringProperty(description);
		this.coefficient = new SimpleIntegerProperty(coefficient);
		this.id_classe = new SimpleIntegerProperty(id_classe);
	}
}
