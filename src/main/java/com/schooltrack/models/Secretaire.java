package com.schooltrack.models;

public class Secretaire extends Utilisateur {

	public Secretaire() {
		super();
	}
	public Secretaire(String login, String code) {
		super(login, code);
	}
	
	public Secretaire(int id, String nom, String prenom, String login, String password, String email, String telephone) {
		super(id, nom, prenom, login, password, email, telephone);
	}
	
	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
	
}
