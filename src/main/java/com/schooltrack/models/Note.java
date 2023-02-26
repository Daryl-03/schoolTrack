package com.schooltrack.models;

import javafx.beans.property.*;

public class Note {
	private IntegerProperty id;
	private DoubleProperty valeur;
	private ObjectProperty<Matiere> matiere;
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
	
	public Matiere getMatiere() {
		return matiere.get();
	}
	
	public ObjectProperty<Matiere> matiereProperty() {
		return matiere;
	}
	
	public void setMatiere(Matiere matiere) {
		this.matiere.set(matiere);
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
		this.matiere = new SimpleObjectProperty<Matiere>();
		this.id_bulletin = new SimpleIntegerProperty();
	}
	
	public Note(int id, Double valeur ) {
		this.id = new SimpleIntegerProperty(id);
		this.valeur =new SimpleDoubleProperty(valeur);
	}
	
	public Note(int id, Double valeur, Matiere matiere, int id_bulletin ) {
		this.id = new SimpleIntegerProperty(id);
		this.valeur =new SimpleDoubleProperty(valeur);
		this.matiere = new SimpleObjectProperty<Matiere>(matiere);
		this.id_bulletin = new SimpleIntegerProperty(id_bulletin);
	}
}
