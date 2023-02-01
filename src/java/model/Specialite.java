package model;

import database.annotations.DbColumn;
import database.annotations.Sequence;

import java.sql.Connection;
import java.util.ArrayList;

@Sequence("specialite_id_seq")
public class Specialite extends BaseModel{
    @DbColumn
    private String role;

    @DbColumn
    private double taux_horaire;

    public Specialite() throws Exception {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getTaux_horaire() {
        return taux_horaire;
    }

    public void setTaux_horaire(double taux_horaire) {
        this.taux_horaire = taux_horaire;
    }
}