package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Client {
	@Id
	@GeneratedValue
	private Long id;
    private String nom;
    private String prenom;
    private String portable;
    private int creditCard;



	public Client(String nom, String prenom, String portable, int creditCard ) {
	this.nom = nom;
	this.prenom = prenom;
	this.portable = portable;
	this.setCreditCard(creditCard);
	}
	public Client() {}


	public String getNom() {
	return nom;
}
	public String getPrenom() {
	return prenom;
}
	public String getPortable() {
	return portable;
}
	public int getCreditCard() {
	return creditCard;
}
	public void setCreditCard(int creditCard) {
	this.creditCard = creditCard;
}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
}
