package model;

import database.annotations.DbColumn;
import database.annotations.PrimaryKey;
import database.dao.ObjectBdd;

import java.sql.Connection;
import java.util.ArrayList;


public class BaseModel extends ObjectBdd{
    @DbColumn
    @PrimaryKey
    private int id;

    public BaseModel() throws Exception {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}