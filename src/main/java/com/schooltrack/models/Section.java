package com.schooltrack.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Section{
	protected IntegerProperty id;
	protected StringProperty Intitule;
	protected ListProperty<Classe> classe;

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


	public String getIntitule() {
		return Intitule.get();
	}

	public void setIntitule(String Intitule) {
		this.Intitule.set(Intitule);
	}

	public StringProperty IntituleProperty() {
		return Intitule;
	}
//getter and setter for classe

	public ListProperty<Classe> classeProperty() {
		return classe;
	}
	public void setClasse(ListProperty<Classe> classe) {
		this.classe.set(classe);
	}
	public ObservableList<Classe> getClasse() {
		return classe.get();
	}
	public Section(Integer id, String intitule , ListProperty<Classe> classe) {
	
		this.id = new SimpleIntegerProperty(id);
		this.Intitule =new SimpleStringProperty(intitule);
		this.classe = new SimpleListProperty<Classe>(classe);
	}

}
