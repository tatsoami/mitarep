package model;

import database.annotations.DbColumn;

public class Settings extends BaseModel{
    @DbColumn
    private String name;
    @DbColumn
    private String value;

    public Settings() throws Exception {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
