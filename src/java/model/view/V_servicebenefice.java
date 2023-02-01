package model.view;

import database.annotations.DbColumn;
import java.sql.Connection;
import java.util.ArrayList;

public class V_servicebenefice extends V_VenteService{
    @DbColumn
    private String name;

    public V_servicebenefice() throws Exception {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<V_servicebenefice> findAll() throws Exception{
        Connection c=this.getConnection(false);
        ArrayList<V_servicebenefice> services=this.findAll(c,"");
        c.close();
        return services;
    }
}
