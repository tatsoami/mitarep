/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.view;

import database.annotations.DbColumn;
import database.dao.ObjectBdd;
import java.sql.Connection;
import java.util.ArrayList;
import model.BaseModel;

/**
 *
 * @author Megane
 */
public class V_facture extends ObjectBdd{
    
    @DbColumn
    private String nom;
    @DbColumn
    private String prenom;
    @DbColumn
    private double quantite;
    @DbColumn
    private double reste_a_payer;
    @DbColumn
    private double cout;
    @DbColumn
    private double montant_payer;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public double getReste_a_payer() {
        return reste_a_payer;
    }

    public void setReste_a_payer(double reste_a_payer) {
        this.reste_a_payer = reste_a_payer;
    }

    public double getCout() {
        return cout;
    }
      public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    public double getMontant_payer() {
        return montant_payer;
    }

    public void setMontant_payer(double montant_payer) {
        this.montant_payer = montant_payer;
    }

    public V_facture() throws Exception {
    }
    
       public ArrayList<V_facture> findAll() throws Exception{
        Connection c=this.getConnection(false);
        ArrayList<V_facture> factures=this.findAll(c,"");
        c.close();
        return factures;
    }

  
        
}
