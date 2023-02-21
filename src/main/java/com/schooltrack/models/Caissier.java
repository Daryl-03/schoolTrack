package com.schooltrack.models;

public class Caissier extends Secretaire{

	public Caissier(String login, String code) {
		super(login, code);
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
