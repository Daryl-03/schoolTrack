package com.schooltrack.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Section{
	private final IntegerProperty id;
	private final StringProperty Intitule;
	private final ListProperty<Classe> classe;

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

	public ObservableList<Classe> getClasse() {
		return classe.get();
	}

	public ListProperty<Classe> classeProperty() {
		return classe;
	}

	public void setClasse(ObservableList<Classe> classe) {
		this.classe.set(classe);
	}

	public Section(int id, String intitule , ObservableList<Classe> classes) {
	
		this.id = new SimpleIntegerProperty(id);
		this.Intitule =new SimpleStringProperty(intitule);
		this.classe = new SimpleListProperty<Classe>(classes);
	}

}
