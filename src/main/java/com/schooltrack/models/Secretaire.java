package com.schooltrack.models;

public class Secretaire extends Utilisateur {

	public Secretaire(String login, String code) {
		super(login, code);
	}
	
	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
	
}
