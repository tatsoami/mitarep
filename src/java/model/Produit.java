/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.annotations.DbColumn;

/**
 *
 * @author Megane
 */
public class Produit extends BaseModel{
    @DbColumn
    private String nom;
    @DbColumn
    private double prixAchat;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Produit() throws Exception {
    }
    
}
