package models;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class Reservation {
		/* ATTRIBUTES */
	@Id
	@GeneratedValue
	private  int id;
	private LocalDate date_arrive;
	private LocalDate date_depart;
	@ManyToOne
	@JoinColumn(name = "chambre_id") // This creates a foreign key column in the reservation table
	private Chambre chambre;
	@ManyToOne
	private Client client;

	
		/* CONSTRUCTOR */
	public Reservation( LocalDate date_arrive, LocalDate date_depart, Chambre chambre, Client client) {// avec agence
		setDate_arrive(date_arrive);
		setDate_depart(date_depart);
		setClient(client);
		setChambre(chambre);
	}
	

	public Reservation() {
	}
	
		/* METHODES */
		public boolean dateOverlap(LocalDate dep, LocalDate arv) {
			Reservation r1 = this;
			return (arv.isAfter(r1.date_arrive) && arv.isBefore(r1.date_depart)) ||  // New arrival is during the existing reservation
					(dep.isAfter(r1.date_arrive) && dep.isBefore(r1.date_depart)) ||  // New departure is during the existing reservation
					(arv.isBefore(r1.date_arrive) && dep.isAfter(r1.date_depart)) ||  // New reservation completely overlaps existing reservation
					(arv.isAfter(r1.date_arrive) && dep.isBefore(r1.date_depart)); // Existing reservation is within new reservation dates
		}
	

	public LocalDate getDate_arrive() {
		return date_arrive;
	}
	public LocalDate getDate_depart() {
		return date_depart;
	}
	public Chambre getChambre() {
		return chambre;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}

	public void setDate_arrive(LocalDate date_arrive) {
		this.date_arrive = date_arrive;
	}
	public void setDate_depart(LocalDate date_depart) {
		this.date_depart = date_depart;
	}
	public void setChambre(Chambre chambre) {
		this.chambre = chambre;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
