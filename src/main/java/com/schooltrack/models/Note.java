package com.schooltrack.models;

import javafx.beans.property.*;

public class Note {
	private final IntegerProperty id;
	private final DoubleProperty valeur;
	private ObjectProperty<Matiere> matiere;

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
	
	public Matiere getMatiere() {
		return matiere.get();
	}
	
	public ObjectProperty<Matiere> matiereProperty() {
		return matiere;
	}
	
	public void setMatiere(Matiere matiere) {
		this.matiere.set(matiere);
	}
	
	public Note(int id, Double valeur ) {
		this.id = new SimpleIntegerProperty(id);
		this.valeur =new SimpleDoubleProperty(valeur);
	}
	
	public Note(IntegerProperty id, DoubleProperty valeur, ObjectProperty<Matiere> matiere) {
		this.id = id;
		this.valeur = valeur;
		this.matiere = matiere;
	}
}
