package com.schooltrack.models;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class Rubrique {

	private IntegerProperty id;
	private StringProperty intitule;
	private StringProperty description;
	private DoubleProperty montant;
	private SimpleObjectProperty<EcheancierPaiement> echeancier;
	

	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
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
	
	public EcheancierPaiement getEcheancier() {
		return echeancier.get();
	}
	
	SimpleObjectProperty<EcheancierPaiement> echeancierProperty() {
		return echeancier;
	}
	
	public void setEcheancier(EcheancierPaiement echeancier) {
		this.echeancier.set(echeancier);
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
	
	public String getDescription() {
		return description.get();
	}
	
	public StringProperty descriptionProperty() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description.set(description);
	}
	
	public Rubrique(int id, double montant, String intitule) {
		this.id = new SimpleIntegerProperty(id);
		this.montant = new SimpleDoubleProperty(montant);
		this.intitule =new SimpleStringProperty(intitule);
	}
	
	public Rubrique(int id, double montant, String intitule,String description, EcheancierPaiement echeancier) {
		this.id = new SimpleIntegerProperty(id);
		this.montant = new SimpleDoubleProperty(montant);
		this.intitule =new SimpleStringProperty(intitule);
		this.description = new SimpleStringProperty(description);
		this.echeancier = new SimpleObjectProperty<EcheancierPaiement>(echeancier);
	}
}
