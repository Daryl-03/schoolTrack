package com.schooltrack.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Rubrique {

	protected IntegerProperty id;
	protected DoubleProperty montant;
	protected StringProperty Intitule;

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
	//getter and setter for montant
	public Double getMontant() {
		return montant.get();
	}
	public void setMontant(Double montant) {
		this.montant.set(montant);
		;
	}
	public DoubleProperty montantProperty() {
		return montant;
	}
	public String getIntitule() {
		return Intitule.get();
	}

	public void setIntitule(String Intitule) {
		this.Intitule.set(Intitule);
	}

	public StringProperty IntituleProperty() {
		return Intitule;
	}

	public Rubrique(Integer id, Double montant, String intitule) {
	
		this.id = new SimpleIntegerProperty(id);
		this.montant = new SimpleDoubleProperty(montant);
		this.Intitule =new SimpleStringProperty(intitule);
	}
	

}
