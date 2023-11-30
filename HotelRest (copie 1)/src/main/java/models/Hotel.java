package models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Entity
public class Hotel {
	
		/* ATTRIBUTES */
	@Id
	private Long id;
	private String nom;
	private String category;
	private int etoiles;
	@OneToOne
	private Adresse adresse;
	@OneToMany(mappedBy = "hotel")
	private List<Agence> agences = new ArrayList<Agence>(); //(id,mot de pass)
	@OneToMany(mappedBy = "hotel")
	private List<Chambre> chambres= new ArrayList<> ();


	/* CONSTRUCTORS */
	public Hotel(String nom, String category, int etoiles, Adresse adresse, Long id) {
		setNom(nom);
		setCategory(category);
		setEtoiles(etoiles);
		setAdresse(adresse);
		setId(id);
	}
	public Hotel() {}

	

	
	public Chambre findChambre(int idChambre) {

		for(Chambre c: chambres) {
			if (c.getNum_chambre()==idChambre) {
				return c;
			}
		}
		return null;
	}

	public List<Agence> getAgences() {
		return agences;
	}

	public void setAgences(List<Agence> agences) {
		this.agences = agences;
	}

	public void addChambre(Chambre chambre) {
		chambres.add(chambre);
	}
	public void addAgences(Agence agence) {
		agences.add(agence);
	}


	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getEtoiles() {
		return etoiles;
	}

	public void setEtoiles(int etoiles) {
		this.etoiles = etoiles;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public List<Chambre> getChambres() {
		return chambres;
	}

	public void setChambres(List<Chambre> chambres) {
		this.chambres = chambres;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	
}
