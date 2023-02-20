package com.schooltrack.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reduction {
	private final IntegerProperty id;
	private final StringProperty observation;
	private final StringProperty Intitule;
	private final DoubleProperty montant;

	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getObservation() {
		return observation.get();
	}

	public StringProperty observationProperty() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation.set(observation);
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

	public double getMontant() {
		return montant.get();
	}

	public DoubleProperty montantProperty() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant.set(montant);
	}

	public Reduction(Integer id, String observation, String intitule, Double montant) {
		
		this.id = new SimpleIntegerProperty(id);
		this.observation =new SimpleStringProperty(observation);
		Intitule = new SimpleStringProperty(intitule);
		this.montant = new SimpleDoubleProperty(montant);
	}
	
}
