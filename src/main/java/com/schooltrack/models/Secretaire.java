package com.schooltrack.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Secretaire extends Utilisateur {

	public Secretaire(String login, String code) {
		super(login, code);
	}
	
	public Secretaire(IntegerProperty id, StringProperty nom, StringProperty prenom, StringProperty login, StringProperty password, StringProperty email, StringProperty telephone) {
		super(id, nom, prenom, login, password, email, telephone);
	}
	
	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
	
}
