package com.schooltrack.models;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Note {
	private IntegerProperty id;
	private FloatProperty valeur;

	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public float getValeur() {
		return valeur.get();
	}

	public FloatProperty valeurProperty() {
		return valeur;
	}

	public void setValeur(float valeur) {
		this.valeur.set(valeur);
	}

	public Note(Integer id, Float valeur ) {
	
		this.id = new SimpleIntegerProperty(id);
		this.valeur =new SimpleFloatProperty(valeur);
	}

}
