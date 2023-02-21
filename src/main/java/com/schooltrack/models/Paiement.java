package com.schooltrack.models;

import java.time.LocalDate;

import javafx.beans.property.*;

public class Paiement {
	private IntegerProperty id;
	private ObjectProperty<LocalDate> date;
	private StringProperty observation;
	private DoubleProperty montant;
	private IntegerProperty id_rubrique;
	private IntegerProperty id_eleve;
	private IntegerProperty id_annee;
	
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
	
	public int getId_rubrique() {
		return id_rubrique.get();
	}
	
	public IntegerProperty id_rubriqueProperty() {
		return id_rubrique;
	}
	
	public void setId_rubrique(int id_rubrique) {
		this.id_rubrique.set(id_rubrique);
	}
	
	public int getId_eleve() {
		return id_eleve.get();
	}
	
	public IntegerProperty id_eleveProperty() {
		return id_eleve;
	}
	
	public void setId_eleve(int id_eleve) {
		this.id_eleve.set(id_eleve);
	}
	
	public int getId_annee() {
		return id_annee.get();
	}
	
	public IntegerProperty id_anneeProperty() {
		return id_annee;
	}
	
	public void setId_annee(int id_annee) {
		this.id_annee.set(id_annee);
	}
	
	public Paiement(int id, LocalDate datePaiement, String observation, double montant) {
		this.id = new SimpleIntegerProperty(id);
		this.date = new SimpleObjectProperty<LocalDate>(datePaiement);
		this.observation = new SimpleStringProperty(observation);
		this.montant = new SimpleDoubleProperty(montant);
	}
	
	public Paiement(int id, LocalDate date, String observation, double montant, int id_rubrique, int id_eleve, int id_annee) {
		this.id = new SimpleIntegerProperty(id);
		this.date = new SimpleObjectProperty<LocalDate>(date);
		this.observation = new SimpleStringProperty(observation);
		this.montant = new SimpleDoubleProperty(montant);
		this.id_rubrique = new SimpleIntegerProperty(id_rubrique);
		this.id_eleve = new SimpleIntegerProperty(id_eleve);
		this.id_annee = new SimpleIntegerProperty(id_annee);
	}
}
