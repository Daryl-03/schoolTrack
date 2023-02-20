package com.schooltrack.models;

import java.time.LocalDate;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class EcheancierPaiement {
	private final IntegerProperty id;
	private final DoubleProperty montant;
	private final ListProperty<LocalDate> echeances;

	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}
	
	public ObservableList<LocalDate> getEcheances() {
		return echeances.get();
	}

	public ListProperty<LocalDate> echeancesProperty() {
		return echeances;
	}

	public void setEcheances(ObservableList<LocalDate> echeances) {
		this.echeances.set(echeances);
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
	
	public EcheancierPaiement(int id, double montant, ObservableList<LocalDate> echeances) {
		this.id = new SimpleIntegerProperty(id);
		this.montant = new SimpleDoubleProperty(montant);
		this.echeances = new SimpleListProperty<LocalDate>(echeances);
	}
	
}
