package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Adresse {
		/* ATTRIBUTES */
	@Id
	@GeneratedValue
	private Long id;
	private String pays;
	private String ville;
	private String rue;
	private String lieu_dit;
	private String position_gps;
	private int code_postale;


	/* CONSTRUCTOR */

	public Adresse() {}
	public Adresse(String pays, String ville, String rue, String lieu_dit, String position_gps, int code_postale) {
		this.pays = pays;
		this.ville = ville;
		this.rue = rue;
		this.lieu_dit = lieu_dit;
		this.position_gps = position_gps;
		this.code_postale = code_postale;
	}
	
		/* METHODES */
	public String getPays() {
		return pays;
	}
	
	public String getVille() {
		return ville;
	}
	
	public String getRue() {
		return rue;
	}
	
	public String getLieu_dit() {
		return lieu_dit;
	}
	
	
	public String getPosition_gps() {
		return position_gps;
	}
	
	public int getCode_postale() {
		return code_postale;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public void setLieu_dit(String lieu_dit) {
		this.lieu_dit = lieu_dit;
	}
	public void setPosition_gps(String position_gps) {
		this.position_gps = position_gps;
	}
	public void setCode_postale(int code_postale) {
		this.code_postale = code_postale;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
