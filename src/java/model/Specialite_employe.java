package model;

import database.annotations.DbColumn;
import database.annotations.Sequence;

import java.sql.Connection;

@Sequence("specialite_employe_id_seq")
public class Specialite_employe extends BaseModel{
    @DbColumn
    private int idSpecialite;
    @DbColumn
    private int idEmploye;

    public Specialite_employe() throws Exception {
    }

    public int getIdSpecialite() {
        return idSpecialite;
    }
    public void setIdSpecialite(int idSpecialite) {
        if (idSpecialite<=0) throw new IllegalArgumentException("L'Id de la specialite est incorrect");
        this.idSpecialite = idSpecialite;
    }
    public int getIdEmploye() {
        return idEmploye;
    }
    public void setIdEmploye(int idEmploye) {
        if (idEmploye <= 0) {
            throw new IllegalArgumentException("L'id de l'employe est incorrect");
        }
        this.idEmploye = idEmploye;
    }

    public int save(Connection c) throws Exception{
       return this.create(c);
    }
  
}
