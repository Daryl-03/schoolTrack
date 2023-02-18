package com.schooltrack.models;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

public class EcheancierPaiement {
	private  IntegerProperty id;
	private  IntegerProperty nbPaiements;
	private  ObjectProperty<LocalDate> premiereEcheance;
	//attribut liste de date de paiement
	private  ListProperty<LocalDate> echeances;

	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public LocalDate getPremiereEcheance() {
		return premiereEcheance.get();
	}

	public ObjectProperty<LocalDate> premiereEcheanceProperty() {
		return premiereEcheance;
	}

	public void setPremiereEcheance(LocalDate premiereEcheance) {
		this.premiereEcheance.set(premiereEcheance);
	}

	public int getNbPaiements() {
		return nbPaiements.get();
	}

	public IntegerProperty nbPaiementsProperty() {
		return nbPaiements;
	}

	public void setNbPaiements(int nbPaiements) {
		this.nbPaiements.set(nbPaiements);
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

	public EcheancierPaiement(Integer id , Integer nbPaiements, LocalDate premiereEcheance, ObservableList<LocalDate> echeances) {
		this.id = new SimpleIntegerProperty(id);
		this.nbPaiements = new SimpleIntegerProperty(nbPaiements);
		this.premiereEcheance = new SimpleObjectProperty<LocalDate>(premiereEcheance);
		this.echeances = new SimpleListProperty<LocalDate>(echeances);
	}
	
}
