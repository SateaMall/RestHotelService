package models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;

@Entity
public class Agence {
	@Id
	private Long id;
	private String mdp;
	private String nom;
	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;
	public Agence(String nom, Long id, String mdp, Hotel hotel) {
		this.id=id;
		this.nom = nom;
		this.mdp = mdp;
		this.hotel= hotel;
	}

	public Agence() {}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	public Long getId() {
	return id;
}
	public void setId(Long id) {
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
}
