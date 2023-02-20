package com.schooltrack.models;

public class Administrateur extends Utilisateur {

	public Administrateur(String login, String code) {
		super(login, code);
	}
	
	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}

}
