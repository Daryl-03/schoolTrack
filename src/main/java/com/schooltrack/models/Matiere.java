package com.schooltrack.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Matiere {
	protected IntegerProperty id;
	protected StringProperty nom;
	protected IntegerProperty coefficient;

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
		;
	}

	public IntegerProperty IdProperty() {
		return id;
	}
	
	public Integer getcoefficient() {
		return coefficient.get();
	}

	public void setcoefficient(Integer coefficient) {
		this.coefficient.set(coefficient);
		;
	}

	public IntegerProperty coefficientProperty() {
		return coefficient;
	}


	public String getIntitule() {
		return nom.get();
	}

	public void setIntitule(String nom) {
		this.nom.set(nom);
	}

	public StringProperty IntituleProperty() {
		return nom;
	}

	public Matiere(Integer id, String nom, Integer coefficient ) {
	
		this.id = new SimpleIntegerProperty(id);
		this.nom=new SimpleStringProperty(nom);
		this.coefficient = new SimpleIntegerProperty(coefficient);
	}

}
