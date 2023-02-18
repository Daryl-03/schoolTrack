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
	private IntegerProperty id;
	private ObjectProperty<LocalDate> date;
	private StringProperty observation;
	private FloatProperty montant;

	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public LocalDate getDate() {
		return date.get();
	}

	public ObjectProperty<LocalDate> dateProperty() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date.set(date);
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

	public float getMontant() {
		return montant.get();
	}

	public FloatProperty montantProperty() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant.set(montant);
	}

	public Paiement(Integer id, LocalDate date, String observation) {
		
		this.id = new SimpleIntegerProperty(id);
		this.date = new SimpleObjectProperty<LocalDate>(date);
		this.observation = new SimpleStringProperty(observation);
	}

	
}
