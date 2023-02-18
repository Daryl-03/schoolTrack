package com.schooltrack.models;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Bulletin {
	private   IntegerProperty id;
	private  StringProperty trimestre;
	private  FloatProperty moyenne;
	private  ListProperty<Note> notes;
	
	public int getId() {
		return id.get();
	}
	
	public IntegerProperty idProperty() {
		return id;
	}
	
	public void setId(int id) {
		this.id.set(id);
	}
	
	public String getTrimestre() {
		return trimestre.get();
	}
	
	public StringProperty trimestreProperty() {
		return trimestre;
	}
	
	public void setTrimestre(String trimestre) {
		this.trimestre.set(trimestre);
	}
	
	public float getMoyenne() {
		return moyenne.get();
	}
	
	public FloatProperty moyenneProperty() {
		return moyenne;
	}
	
	public void setMoyenne(float moyenne) {
		this.moyenne.set(moyenne);
	}
	
	public ObservableList<Note> getNotes() {
		return notes.get();
	}
	
	public ListProperty<Note> notesProperty() {
		return notes;
	}
	
	public void setNotes(ObservableList<Note> notes) {
		this.notes.set(notes);
	}
	
	public Bulletin(int id, String trimestre, float moyenne, ObservableList<Note> note) {
		
		this.id =new SimpleIntegerProperty(id);
		this.trimestre = new SimpleStringProperty(trimestre);
		this.moyenne = new SimpleFloatProperty(moyenne);
		this.notes = new SimpleListProperty<Note>(note);
	}
}
