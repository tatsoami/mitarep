package model.view;

import database.annotations.DbColumn;
import database.dao.ObjectBdd;
import model.Service;

import java.sql.Connection;
import java.util.ArrayList;

public class V_PrixService extends ObjectBdd {
    @DbColumn
    private int id_service;
    @DbColumn
    private double prix;

    public V_PrixService() throws Exception {
    }

    public int getId_service() {
        return id_service;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setServiceValue() throws Exception {
        Connection connection=this.getConnection(false);
        ArrayList<V_PrixService> services=this.findAll(connection, " where id_service="+this.getId_service());
        connection.close();
        if(services.size()>0){
            this.prix=services.get(0).getPrix();
            return;
        }
        throw new Exception("Service introuvable");
    }
}
