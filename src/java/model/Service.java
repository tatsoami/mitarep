package model;

import database.annotations.DbColumn;
import database.annotations.Sequence;

import java.sql.Connection;
import java.util.ArrayList;

@Sequence("operation_id_seq")
public class Service extends BaseModel{
    @DbColumn
    private String name;
    @DbColumn
    private double cost;

    public Service() throws Exception {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Service getService() throws Exception {
        Connection connection = this.getConnection(false);
        ArrayList<Service> services = this.findAll(connection, " where id=" + this.getId());
        connection.close();
        if (services.size() > 0) {
            return services.get(0);
        }
        throw new Exception("Service introuvable");
    }
}
