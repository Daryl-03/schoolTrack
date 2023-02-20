package com.schooltrack.models;

import java.time.LocalDate;

import javafx.beans.property.*;

public class Paiement {
	private IntegerProperty id;
	private ObjectProperty<LocalDate> date;
	private StringProperty observation;
	private DoubleProperty montant;
	private ObjectProperty<Rubrique> rubrique;

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

	public double getMontant() {
		return montant.get();
	}

	public DoubleProperty montantProperty() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant.set(montant);
	}
	
	public void setMontant(double montant) {
		this.montant.set(montant);
	}
	
	public Rubrique getRubrique() {
		return rubrique.get();
	}
	
	public ObjectProperty<Rubrique> rubriqueProperty() {
		return rubrique;
	}
	
	public void setRubrique(Rubrique rubrique) {
		this.rubrique.set(rubrique);
	}
	
	public Paiement(int id, LocalDate datePaiement, String observation, double montant, Rubrique rubrique) {
		this.id = new SimpleIntegerProperty(id);
		this.date = new SimpleObjectProperty<LocalDate>(datePaiement);
		this.observation = new SimpleStringProperty(observation);
		this.montant = new SimpleDoubleProperty(montant);
		this.rubrique = new SimpleObjectProperty<Rubrique>(rubrique);
	}
}
