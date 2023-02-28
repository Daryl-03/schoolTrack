package com.schooltrack.models;

public class PaiementTableViewModel {

    private Paiement paiement;
    private Rubrique rubrique;
    private Eleve eleve;

    public PaiementTableViewModel(Paiement paiement, Rubrique rubrique, Eleve eleve) {
        this.paiement = paiement;
        this.rubrique = rubrique;
        this.eleve = eleve;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public Rubrique getRubrique() {
        return rubrique;
    }

    public void setRubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
    }

    public Eleve getEleve() {
        return eleve;
    }
    
    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }
}

