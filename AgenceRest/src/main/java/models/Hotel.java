package models;

import jakarta.persistence.*;

@Entity
public class Hotel {
    @GeneratedValue
    @Id
    @Column(unique=true)
    private long  id;
    private String nom;
    private String S1;
    private String S2;
    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agence agence;


    public Hotel(String nom, String S1, String S2){
        this.nom=nom;
        this.S1=S1;
        this.S2=S2;
    }
    public Hotel() {}


    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getS1() {
        return S1;
    }
    public void setS1(String s1) {
        S1 = s1;
    }
    public String getS2() {
        return S2;
    }
    public void setS2(String s2) {
        S2 = s2;
    }
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public Agence getAgence() {return agence;}
    public void setAgence(Agence agence) {this.agence = agence;}
}
