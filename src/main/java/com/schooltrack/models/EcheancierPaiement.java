package com.schooltrack.models;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;

public class EcheancierPaiement {
	protected IntegerProperty id;
	protected IntegerProperty nbPaiements;
	protected ObjectProperty<LocalDate> premiereEcheance;
	//attribut liste de date de paiement
	protected ListProperty<LocalDate> echeances;
	
	//getters and setters de 
	
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

	public LocalDate getPremiereEcheance() {
		return premiereEcheance.get();
	}

	public void setDate(LocalDate PremierEch) {
		this.premiereEcheance.set(PremierEch);
		;
	}

	public ObjectProperty<LocalDate> PremierEchProperty() {
		return premiereEcheance;

	}
//getters and setters de nbPaiements
	public Integer getNbPaiements() {
		return nbPaiements.get();
	}

	public void setNbPaiements(Integer nbPaiements) {
		this.nbPaiements.set(nbPaiements);
		;
	}

	public IntegerProperty nbPaiementsProperty() {
		return nbPaiements;
	}

	//getters and setters de echeances
	public ListProperty<LocalDate> getEcheances() {
		return echeances;
	}
	
	public ListProperty<LocalDate> echeancesProperty() {
		return echeances;
	}
	//constructeur
	public EcheancierPaiement(Integer id , Integer nbPaiements, LocalDate premiereEcheance, ListProperty<LocalDate> echeances) {
		this.id = new SimpleIntegerProperty(id);
		this.nbPaiements = new SimpleIntegerProperty(nbPaiements);
		this.premiereEcheance = new SimpleObjectProperty<LocalDate>(premiereEcheance);
		this.echeances = new SimpleListProperty<LocalDate>(echeances);
	}
	
}
