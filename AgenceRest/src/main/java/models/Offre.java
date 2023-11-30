package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Offre {
	
	
	/*  ATTRIBUTES  */
	@Id
	@GeneratedValue
	private Long id;
	private int capacite;
	private double prix;
	private LocalDate date_arrive;
	private LocalDate date_depart;
	private Long chambreId;
	private Long hotelId;
	private String nomHotel;
	private int etoileHotel;
	private long agenceId;
	private String nomAgence;
	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	private String ville;
	@Column(name = "image", length = 10000000)
	private String image;
	
	
	/*  CONSTRUCTOR  */
	public Offre(int capacite, LocalDate date_arrive, LocalDate date_depart, double prix, Long chambreId, Long hotelId, String image, int etoileHotel, String nomHotel, String ville) {
		setImage(image);
		setCapacite(capacite);
		setDate_arrive(date_arrive);
		setDate_depart(date_depart);
		setPrix(prix);
		setHotelId(hotelId);
		setChambreId(chambreId);
		setEtoileHotel(etoileHotel);
		setNomHotel(nomHotel);
		setVille(ville);
	}
	public Offre() {}



	/*  METHODS  */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Offre offre = (Offre) o;
		return id == offre.id && capacite == offre.capacite && Double.compare(prix, offre.prix) == 0 && chambreId == offre.chambreId && hotelId == offre.hotelId && etoileHotel == offre.etoileHotel && Objects.equals(date_arrive, offre.date_arrive) && Objects.equals(date_depart, offre.date_depart) && Objects.equals(nomHotel, offre.nomHotel) && Objects.equals(image, offre.image);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, capacite, prix, date_arrive, date_depart, chambreId, hotelId, nomHotel, etoileHotel, image);
	}
	
	public Long getHotelId() {
		return hotelId;
	}
	public void setHotelId(Long hotelId) {this.hotelId = hotelId;}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public void setDate_arrive(LocalDate date_arrive) {
		this.date_arrive = date_arrive;
	}
	public void setDate_depart(LocalDate date_depart) {
		this.date_depart = date_depart;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	public int getCapacite() {
		return capacite;
	}
	public LocalDate getDate_arrive() {
		return date_arrive;
	}
	public LocalDate getDate_depart() {
		return date_depart;
	}
	public double getPrix() { return prix;}
	public Long getChambreId() {
		return chambreId;
	}
	public void setChambreId(Long chambreId) {
		this.chambreId = chambreId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getNomHotel() {return nomHotel;}
	public void setNomHotel(String nomHotel) {this.nomHotel = nomHotel;}
	public int getEtoileHotel() {return etoileHotel;}
	public void setEtoileHotel(int etoileHotel) {this.etoileHotel = etoileHotel;}
	public long getAgenceId(){return agenceId;}
	public void setAgenceId(long agenceId){this.agenceId=agenceId;}
	public String getNomAgence() {
		return nomAgence;
	}

	public void setNomAgence(String nomAgence) {
		this.nomAgence = nomAgence;
	}
}
