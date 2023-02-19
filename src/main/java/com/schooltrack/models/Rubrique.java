package com.schooltrack.models;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.util.List;

public class Rubrique {

	private IntegerProperty id;
	private DoubleProperty montant;
	private StringProperty intitule;
	
	private ListProperty<EcheancierPaiement> echeanciers;

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
		return intitule.get();
	}

	public StringProperty intituleProperty() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule.set(intitule);
	}
	
	public ObservableList<EcheancierPaiement> getEcheanciers() {
		return echeanciers.get();
	}
	
	public ListProperty<EcheancierPaiement> echeanciersProperty() {
		return echeanciers;
	}
	
	public void setEcheanciers(ObservableList<EcheancierPaiement> echeanciers) {
		this.echeanciers.set(echeanciers);
	}
	
	public Rubrique(Integer id, Double montant, String intitule) {
		this.id = new SimpleIntegerProperty(id);
		this.montant = new SimpleDoubleProperty(montant);
		this.intitule =new SimpleStringProperty(intitule);
	}
	
	public Rubrique(Integer id, Double montant, String intitule, ObservableList<EcheancierPaiement> echeanciers) {
		this.id = new SimpleIntegerProperty(id);
		this.montant = new SimpleDoubleProperty(montant);
		this.intitule =new SimpleStringProperty(intitule);
		this.echeanciers = new SimpleListProperty<EcheancierPaiement>(echeanciers);
	}
}
