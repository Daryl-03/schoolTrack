package com.schooltrack.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Caissier extends Secretaire{

	public Caissier() {
		super();
	}
	public Caissier(String login, String code) {
		super(login, code);
	}
	
	public Caissier(IntegerProperty id, StringProperty nom, StringProperty prenom, StringProperty login, StringProperty password, StringProperty email, StringProperty telephone) {
		super(id, nom, prenom, login, password, email, telephone);
	}
	
	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
