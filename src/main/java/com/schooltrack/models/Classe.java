package com.schooltrack.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Classe {
protected IntegerProperty id;
protected StringProperty nom;
protected ListProperty<Rubrique> rubriques;

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

public String getNom() {
	return nom.get();
}

public void setnom(String nom) {
	this.nom.set(nom);
}

public StringProperty nomProperty() {
	return nom ;
}
//getter et setter de rubrique
public ListProperty<Rubrique> rubriquesProperty() {
	return rubriques;
}
public void setRubriques(ListProperty<Rubrique> rubriques) {
	this.rubriques = rubriques;
}
public ListProperty<Rubrique> getRubriques() {
	return rubriques;
}

public Classe(Integer id, Section section, String nom) {

	this.id = new SimpleIntegerProperty(id);
	this.nom =new SimpleStringProperty(nom);
	this.rubriques = new SimpleListProperty<Rubrique>(rubriques);
}


}
