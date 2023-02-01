package model;

import database.annotations.DbColumn;
import database.annotations.Sequence;

@Sequence("operation_details_id_seq")
public class Service_details extends  BaseModel{
    @DbColumn
    private int specialite_id;
    @DbColumn
    private double work_hour;

    @DbColumn
    private int id_service;
    public Service_details() throws Exception {
    }

    public int getId_operation() {
        return specialite_id;
    }

    public void setId_operation(int specialite_id) {
        this.specialite_id = specialite_id;
    }

    public int getSpecialite_id() {
        return specialite_id;
    }

    public void setSpecialite_id(int specialite_id) {
        this.specialite_id = specialite_id;
    }

    public double getWork_hour() {
        return work_hour;
    }

    public void setWork_hour(double work_hour) {
        this.work_hour = work_hour;
    }

    public int getId_service() {
        return id_service;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }
}
