/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.view;

import database.annotations.DbColumn;
import java.sql.Connection;
import java.util.ArrayList;
import model.BaseModel;

/**
 *
 * @author Megane
 */
public class V_PrixProduit extends BaseModel{
    @DbColumn
    private String nom;
    @DbColumn
    private double prixAchat;
    @DbColumn
    private double prix_vente;
    @DbColumn
    private double marge_beneficiaire;

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

    public double getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(double prix_vente) {
        this.prix_vente = prix_vente;
    }

    public double getMarge_beneficiaire() {
        return marge_beneficiaire;
    }

    public void setMarge_beneficiaire(double marge_beneficiaire) {
        this.marge_beneficiaire = marge_beneficiaire;
    }

    public V_PrixProduit() throws Exception {
    }

    public void setServiceValue() throws Exception {
       Connection c=this.getConnection(false);
       ArrayList<V_PrixProduit> produits = this.findAll(c, " where id="+this.getId());
       if(produits.size() > 0){
           this.setNom(produits.get(0).getNom());
           this.setPrixAchat(produits.get(0).getPrixAchat());
           this.setMarge_beneficiaire(produits.get(0).getMarge_beneficiaire());
           this.setPrix_vente(produits.get(0).getPrix_vente());
           return;
       }
       throw  new Exception("Aucun produit ne correspond");
    }
    
}   
