package model;

import database.annotations.DbColumn;
import database.annotations.Sequence;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;

@Sequence("employe_id_seq")
public class Employe extends BaseModel{
    @DbColumn
    private String nom;
    @DbColumn
    private String prenom;
    @DbColumn
    private Date date_naissance;
    @DbColumn
    private int id_genre;

    @DbColumn
    private int id_diplome;

    @DbColumn
    private double salaire;

    public Employe() throws Exception {
    }


    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        if (nom == null || nom.isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        if (prenom == null || prenom.isEmpty()) {
            throw new IllegalArgumentException("Le prenom est obligatoire");
        }
        this.prenom = prenom;
    }
    public Date getDate_naissance() {
        return date_naissance;
    }
    public void setDate_naissance(Date date_naissance) throws Exception{
        if (date_naissance == null) {
            throw new IllegalArgumentException("La date de naissance est obligatoire");
        }
        LocalDate dat=LocalDate.now();
        Date d=Date.valueOf(dat);
        Period period=Period.between(date_naissance.toLocalDate(),d.toLocalDate());
        if(period.getYears() < 18){
            throw new Exception("Age non valide");
        }
            this.date_naissance = date_naissance;
    }
    public int getId_genre() {
        return id_genre;
    }
    public void setId_genre(int id_genre) {
        if (id_genre < 0) {
            throw new IllegalArgumentException("Le genre est incorrect");
        }
        this.id_genre = id_genre;
    }

    public int getId_diplome() {
        return id_diplome;
    }

    public void setId_diplome(int id_diplome) {
        if (id_diplome <= 0) {
            throw new IllegalArgumentException("Le diplome est incorrect");
        }
        this.id_diplome = id_diplome;
    }

    public int save(Connection c) throws Exception{
       return this.create(c);
    }


    public void saveSpecialite(Connection connection, int idEmploye,int specialiteId) throws Exception {
        Specialite_employe specialiteEmploye=new Specialite_employe();
        specialiteEmploye.setIdEmploye(idEmploye);
        specialiteEmploye.setIdSpecialite(specialiteId);
        specialiteEmploye.save(connection);
    }

    public void checkSpecialite(String[] specialites) {
        if(specialites.length == 0){
            throw new RuntimeException("Aucune specialite selectionnée");
        }else if(this.hasSame(specialites)){
            throw new RuntimeException("Une specialite ne peut etre selectionnée qu'une seule fois");
        }
    }

    private boolean hasSame(String[] specialites) {
        for(int i=0;i<specialites.length;i++){
            for(int j=i+1;j<specialites.length;j++){
                if(specialites[i].equals(specialites[j])){
                    return true;
                }
            }
        }
        return false;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public void setEmploye(HashMap<String, Object> data) throws Exception {
        this.setNom((String) data.get("nom"));
        this.setPrenom((String) data.get("prenom"));
        this.setDate_naissance(Date.valueOf((String) data.get("date_naissance")));
        this.setId_genre(Integer.parseInt((String) data.get("genre")));
        this.setId_diplome(Integer.parseInt((String) data.get("diplome")));
        this.setSalaire(Double.parseDouble((String)data.get("salaire")));
    }
}