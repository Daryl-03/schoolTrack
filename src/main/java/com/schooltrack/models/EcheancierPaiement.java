package com.schooltrack.models;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class EcheancierPaiement {
	private IntegerProperty id;
	private ListProperty<LocalDate> echeances;
	private IntegerProperty id_classe;
	private IntegerProperty id_rubrique;
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
	
	public ObservableList<LocalDate> getEcheances() {
		return echeances.get();
	}

	public ListProperty<LocalDate> echeancesProperty() {
		return echeances;
	}

	public void setEcheances(ObservableList<LocalDate> echeances) {
		this.echeances.set(echeances);
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
	
	public int getId_rubrique() {
		return id_rubrique.get();
	}
	
	public IntegerProperty id_rubriqueProperty() {
		return id_rubrique;
	}
	
	public void setId_rubrique(int id_rubrique) {
		this.id_rubrique.set(id_rubrique);
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
	
	public EcheancierPaiement() {
		this.id = new SimpleIntegerProperty();
		this.echeances = new SimpleListProperty<LocalDate>();
		this.id_classe = new SimpleIntegerProperty();
		this.id_rubrique = new SimpleIntegerProperty();
		this.id_annee = new SimpleIntegerProperty();
	}
	
	public EcheancierPaiement(int id, ObservableList<LocalDate> echeances, int id_classe, int id_rubrique, int id_annee) {
		this.id = new SimpleIntegerProperty(id);
		this.echeances = new SimpleListProperty<LocalDate>(echeances);
		this.id_classe = new SimpleIntegerProperty(id_classe);
		this.id_rubrique = new SimpleIntegerProperty(id_rubrique);
		this.id_annee = new SimpleIntegerProperty(id_annee);
	}
	
}
