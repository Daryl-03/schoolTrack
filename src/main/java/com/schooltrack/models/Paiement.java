package com.schooltrack.models;

import java.time.LocalDate;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Paiement {
	protected IntegerProperty id;
	protected ObjectProperty<LocalDate> date;
	protected StringProperty observation; 
	protected FloatProperty montant;
	
	
	public Float getMontant() {
		return montant.get();
	}
	public void setMontant(float montant) {
		this.montant.set(montant);
	}
	public FloatProperty montantProperty() {
		return montant;
	}

	public void setMontant(FloatProperty montant) {
		this.montant = montant;
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
	}
	public IntegerProperty IdProperty() {
		return id;
	}

	public LocalDate getDate() {
		return date.get();
	}

	public void setDate(LocalDate date) {
		this.date.set(date);;
	}
	
	public ObjectProperty<LocalDate> dateProperty() {
		return date;
		
	}

	public String getObservation() {
		return observation.get();
	}

	public void setObservation(String observation) {
		this.observation.set(observation);
	}
	public StringProperty ObservationProperty() {
		return observation;
	}

	public Paiement(Integer id, LocalDate date, String observation) {
		
		this.id = new SimpleIntegerProperty(id);
		this.date = new SimpleObjectProperty<LocalDate>(date);
		this.observation = new SimpleStringProperty(observation);
	}

	
}
