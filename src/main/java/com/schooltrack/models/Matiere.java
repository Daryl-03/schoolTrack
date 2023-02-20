package com.schooltrack.models;

import javafx.beans.property.*;

public class Matiere {
	private IntegerProperty id;
	private StringProperty nom;
	private StringProperty description;
	private IntegerProperty coefficient;
	private ObjectProperty<Classe> classe;

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
	
	public Classe getClasse() {
		return classe.get();
	}
	
	public ObjectProperty<Classe> classeProperty() {
		return classe;
	}
	
	public void setClasse(Classe classe) {
		this.classe.set(classe);
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
	}
	
	public Matiere(int id, String nom, String description, int coefficient, Classe classe) {
		this.id = new SimpleIntegerProperty(id);
		this.nom = new SimpleStringProperty(nom);
		this.description = new SimpleStringProperty(description);
		this.coefficient = new SimpleIntegerProperty(coefficient);
		this.classe = new SimpleObjectProperty<>(classe);
	}
}
