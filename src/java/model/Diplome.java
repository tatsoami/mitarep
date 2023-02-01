package model;

import database.annotations.DbColumn;

public class Diplome extends BaseModel{
    @DbColumn
    private String nom;

    public Diplome() throws Exception {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
