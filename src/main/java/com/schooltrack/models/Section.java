package com.schooltrack.models;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class Section{
	private IntegerProperty id;
	private StringProperty Intitule;
	private ListProperty<Classe> classes;

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
		return Intitule.get();
	}

	public StringProperty intituleProperty() {
		return Intitule;
	}

	public void setIntitule(String intitule) {
		this.Intitule.set(intitule);
	}

	public ObservableList<Classe> getClasses() {
		return classes.get();
	}

	public ListProperty<Classe> classesProperty() {
		return classes;
	}

	public void setClasses(ObservableList<Classe> classes) {
		this.classes.set(classes);
	}
	
	public Section() {
		this.id = new SimpleIntegerProperty();
		this.Intitule =new SimpleStringProperty();
		this.classes = new SimpleListProperty<Classe>();
	}
	
	public Section(int id, String intitule , ObservableList<Classe> classes) {
	
		this.id = new SimpleIntegerProperty(id);
		this.Intitule =new SimpleStringProperty(intitule);
		this.classes = new SimpleListProperty<Classe>(classes);
	}
	
	public long getEffectif(){
		long effectif = 0;
		for (Classe classe : classes) {
			effectif += classe.getEleves().size();
		}
		return effectif;
	}
}
