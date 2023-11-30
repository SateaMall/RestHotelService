package models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Agence {
    @Id
    int id;
    private String nom;
    private String mdp;
    private double taux_reduc;
    @OneToMany(mappedBy = "agence")
    private List<Hotel> hotels ;


    public Agence(String nom, double taux_reduc,int id, String mdp) {
        this.id=id;
        this.nom = nom;
        this.mdp = mdp;
        this.taux_reduc = taux_reduc;
        hotels = new ArrayList<Hotel>();
    }

    public Agence() {hotels = new ArrayList<Hotel>();}

    public double reducPrix(double prix) {
        return prix- prix/taux_reduc;
    }

    public void addhotel(Hotel hotel) {
        hotels.add(hotel);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getTaux_reduc() {
        return taux_reduc;
    }

    public void setTaux_reduc(double taux_reduc) {
        this.taux_reduc = taux_reduc;
    }





}
