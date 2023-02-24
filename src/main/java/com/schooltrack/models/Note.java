package com.schooltrack.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Note {
	private IntegerProperty id;
	private DoubleProperty valeur;
	private IntegerProperty id_matiere;
	private IntegerProperty id_bulletin;

	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public double getValeur() {
		return valeur.get();
	}

	public DoubleProperty valeurProperty() {
		return valeur;
	}

	public void setValeur(float valeur) {
		this.valeur.set(valeur);
	}
	
	public void setValeur(double valeur) {
		this.valeur.set(valeur);
	}
	
	public int getId_matiere() {
		return id_matiere.get();
	}
	
	public IntegerProperty id_matiereProperty() {
		return id_matiere;
	}
	
	public void setId_matiere(int id_matiere) {
		this.id_matiere.set(id_matiere);
	}
	
	public int getId_bulletin() {
		return id_bulletin.get();
	}
	
	public IntegerProperty id_bulletinProperty() {
		return id_bulletin;
	}
	
	public void setId_bulletin(int id_bulletin) {
		this.id_bulletin.set(id_bulletin);
	}
	
	public Note() {
		this.id = new SimpleIntegerProperty();
		this.valeur =new SimpleDoubleProperty();
		this.id_matiere = new SimpleIntegerProperty();
		this.id_bulletin = new SimpleIntegerProperty();
	}
	
	public Note(int id, Double valeur ) {
		this.id = new SimpleIntegerProperty(id);
		this.valeur =new SimpleDoubleProperty(valeur);
	}
	
	public Note(int id, Double valeur, int id_matiere, int id_bulletin ) {
		this.id = new SimpleIntegerProperty(id);
		this.valeur =new SimpleDoubleProperty(valeur);
		this.id_matiere = new SimpleIntegerProperty(id_matiere);
		this.id_bulletin = new SimpleIntegerProperty(id_bulletin);
	}
}
