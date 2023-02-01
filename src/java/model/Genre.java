package model;

import database.annotations.DbColumn;
import database.annotations.Sequence;

import java.sql.Connection;
import java.util.ArrayList;

@Sequence("genre_id_seq")
public class Genre extends BaseModel{
    @DbColumn
    private String nom;

    public Genre() throws Exception {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    

}