package com.schooltrack.models;

public class Caissier extends Utilisateur{

	public Caissier() {
		super();
	}
	public Caissier(String login, String code) {
		super(login, code);
	}
	
	public Caissier(int id, String nom, String prenom, String login, String password, String email, String telephone) {
		super(id, nom, prenom, login, password, email, telephone);
	}
	
	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
