package com.schooltrack.models;

import javafx.beans.property.*;

public class Rubrique {

	private IntegerProperty id;
	private StringProperty intitule;
	private StringProperty description;
	private DoubleProperty montant;
	private SimpleObjectProperty<EcheancierPaiement> echeancier;
	private IntegerProperty id_classe;

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
	
	public int getId_classe() {
		return id_classe.get();
	}
	
	public IntegerProperty id_classeProperty() {
		return id_classe;
	}
	
	public void setId_classe(int id_classe) {
		this.id_classe.set(id_classe);
	}
	
	public Rubrique() {
		this.id = new SimpleIntegerProperty();
		this.montant = new SimpleDoubleProperty();
		this.intitule =new SimpleStringProperty();
		this.description = new SimpleStringProperty();
		this.echeancier = new SimpleObjectProperty<EcheancierPaiement>();
		this.id_classe = new SimpleIntegerProperty();
	}
	
	public Rubrique(int id, double montant, String intitule) {
		this.id = new SimpleIntegerProperty(id);
		this.montant = new SimpleDoubleProperty(montant);
		this.intitule =new SimpleStringProperty(intitule);
	}
	
	public Rubrique(int id, double montant, String intitule,String description, EcheancierPaiement echeancier, int id_classe) {
		this.id = new SimpleIntegerProperty(id);
		this.montant = new SimpleDoubleProperty(montant);
		this.intitule =new SimpleStringProperty(intitule);
		this.description = new SimpleStringProperty(description);
		this.echeancier = new SimpleObjectProperty<EcheancierPaiement>(echeancier);
		this.id_classe = new SimpleIntegerProperty(id_classe);
	}
}
