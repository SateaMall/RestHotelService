package models;

import jakarta.persistence.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Objects;

@Entity
public class Chambre {


	/* ATTRIBUTES */
	@Id
	@GeneratedValue
	private Long id;
	private int num_chambre;
	private int capacite;
	private int prix_base;
	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;
	@Column(name = "image", length = 10000000)
	private String image;

	@OneToMany(mappedBy = "chambre")
	private List<Reservation> reservations = new ArrayList<>();
	
	
		/* CONSTRUCTOR */
	public Chambre(int num_chambre, int capacite, int prix_base, Hotel hotel, String file_path) throws IOException {
		setNum_chambre (num_chambre);
		setCapacite(capacite);
		setPrix_base(prix_base);
		setHotel(hotel);
		setImage(encodeImage(file_path));
		setReservations( new ArrayList<>()); 
	}
	public Chambre() {}
	
	
		/* METHODES */

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Chambre chambre = (Chambre) o;
		return num_chambre == chambre.num_chambre && capacite == chambre.capacite && prix_base == chambre.prix_base && Objects.equals(id, chambre.id) && Objects.equals(hotel, chambre.hotel) && Objects.equals(image, chambre.image) && Objects.equals(reservations, chambre.reservations);
	}

	@Override
	public int hashCode() {return Objects.hash(id, num_chambre, capacite, prix_base, hotel, image, reservations);}

	public String getImage() {
	        return image;
	    }
	public void setImage(String image) {
	        this.image = image;
	    }
	
	public void addReservation(Reservation r) {
		reservations.add(r);
	}
	public int getNum_chambre() {
		return num_chambre;
	}
	public void setNum_chambre(int num_chambre) {
		this.num_chambre = num_chambre;
	}
	public int getCapacite() {
		return capacite;
	}
	public int getPrix_base() {
		return prix_base;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public List<Reservation> getReservations() {
		return reservations;
	}
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}


	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}


	public void setPrix_base(int prix_base) {
		this.prix_base = prix_base;
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
    public static String encodeImage(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        byte[] fileContent = Files.readAllBytes(imageFile.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }

	
	
}
