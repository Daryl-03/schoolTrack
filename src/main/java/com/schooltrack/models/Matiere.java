package com.schooltrack.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Matiere {
	private IntegerProperty id;
	private StringProperty nom;
	private IntegerProperty coefficient;

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

	public Matiere(Integer id, String nom, Integer coefficient ) {
	
		this.id = new SimpleIntegerProperty(id);
		this.nom=new SimpleStringProperty(nom);
		this.coefficient = new SimpleIntegerProperty(coefficient);
	}

}
