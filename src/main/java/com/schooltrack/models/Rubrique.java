package com.schooltrack.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Rubrique {

	private IntegerProperty id;
	private DoubleProperty montant;
	private StringProperty Intitule;

	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public double getMontant() {
		return montant.get();
	}

	public DoubleProperty montantProperty() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant.set(montant);
	}

	public String getIntitule() {
		return Intitule.get();
	}

	public StringProperty intituleProperty() {
		return Intitule;
	}

	public void setIntitule(String intitule) {
		this.Intitule.set(intitule);
	}

	public Rubrique(Integer id, Double montant, String intitule) {
	
		this.id = new SimpleIntegerProperty(id);
		this.montant = new SimpleDoubleProperty(montant);
		this.Intitule =new SimpleStringProperty(intitule);
	}
	

}
