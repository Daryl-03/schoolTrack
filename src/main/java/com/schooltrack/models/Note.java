package com.schooltrack.models;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Note {
	protected IntegerProperty id;
	protected FloatProperty valeur;

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


	public Float getValeur() {
		return valeur.get();
	}

	public void setValeur(Float Valeur ) {
		this.valeur.set(Valeur);
		if( Valeur < 0) {
			this.valeur.set(0);
		}if( Valeur > 20) {
			this.valeur.set(20);
		}
			
		
	}

	public FloatProperty valeurProperty() {
		return valeur;
	}

	public Note(Integer id, Float valeur ) {
	
		this.id = new SimpleIntegerProperty(id);
		this.valeur =new SimpleFloatProperty(valeur);
	}

}
