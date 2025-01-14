package com.schooltrack.models;

import javafx.beans.property.*;

public class Reduction {
	private IntegerProperty id;
	private StringProperty observation;
	private StringProperty Intitule;
	private DoubleProperty montant;

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
