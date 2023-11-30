package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Agence {
    @Id
    @GeneratedValue
    int id;
    private String nom;
    private String S1;
    private String S2;



    public Agence(String nom, String S1, String S2) {
        this.nom = nom;
        this.S1=S1;
        this.S2=S2;
    }

    public Agence() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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



}
