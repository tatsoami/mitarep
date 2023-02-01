package model.view;

import database.annotations.Tablename;

import java.sql.Connection;
import java.util.ArrayList;

@Tablename("v_venteservice")
public class V_VenteService extends V_PrixService{
    public V_VenteService() throws Exception {
    }

    @Override
    public void setServiceValue() throws Exception {
        Connection connection=this.getConnection(false);
        ArrayList<V_VenteService> services=this.findAll(connection, " where id_service="+this.getId_service());
        connection.close();
        if(services.size()>0){
            this.setPrix(services.get(0).getPrix());
            return;
        }
        throw new Exception("Service introuvable");
    }
}
