package com.schooltrack.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reduction {
	protected IntegerProperty id;
	protected StringProperty observation;
	protected StringProperty Intitule;
	protected DoubleProperty montant;

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

	public String getIntitule() {
		return Intitule.get();
	}

	public void setIntitule(String Intitule) {
		this.Intitule.set(Intitule);
	}

	public StringProperty IntituleProperty() {
		return Intitule;
	}

	public String getObservation() {
		return observation.get();
	}

	public void setObservation(String observation) {
		this.observation.set(observation);
	}

	public StringProperty obseProperty() {
		return observation;
	}

	public Double getMontant() {
		return montant.get();
	}

	public void setMontant(Double montant) {
		this.montant.set(montant);
	}

	public DoubleProperty montantProperty() {
		return montant;
	}

	public Reduction(Integer id, String observation, String intitule, Double montant) {
		
		this.id = new SimpleIntegerProperty(id);
		this.observation =new SimpleStringProperty(observation);
		Intitule = new SimpleStringProperty(intitule);
		this.montant = new SimpleDoubleProperty(montant);
	}
	
}
