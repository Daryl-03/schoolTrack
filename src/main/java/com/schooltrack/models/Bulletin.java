package com.schooltrack.models;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bulletin {
	protected IntegerProperty id;
	protected StringProperty trimestre;
	protected FloatProperty moyenne;
	protected ListProperty<Note> notes;
	
	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
	}

	public IntegerProperty IdProperty() {
		
		return id;
	}


	public Float getMoyenne() {
		return moyenne.get();
	}

	public void setMoyenne(Float moyenne ) {
		this.moyenne.set(moyenne);
	}

	public FloatProperty moyenneProperty() {
		return moyenne;
	}

	public String getTrimestre() {
		return trimestre.get();
	}

	public void setTrimestre(String trimestre) {
		this.trimestre.set(trimestre);
	}
	public StringProperty trimestre()
	{
		return trimestre;
	}

	public ListProperty<Note> getNote() {
		return notes;
	}
	public void setNote(ListProperty<Note> note) {
		this.notes = note;
	}
	public ListProperty<Note> noteProperty() {
		return notes;
	}

	public Bulletin(Integer id, String trimestre, Float moyenne, ListProperty<Note> note) {
		
		this.id =new SimpleIntegerProperty(id);
		this.trimestre = new SimpleStringProperty(trimestre);
		this.moyenne = new SimpleFloatProperty(moyenne);
		this.notes = new SimpleListProperty<Note>(note);
	}
	
	
}
