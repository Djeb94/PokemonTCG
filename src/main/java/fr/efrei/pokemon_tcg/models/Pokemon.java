package fr.efrei.pokemon_tcg.models;

import fr.efrei.pokemon_tcg.constants.TypePokemon;
import jakarta.persistence.*;

@Entity
public class Pokemon {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	private String nom;

	private Integer pv;

	private Integer attaque1;

	private Integer attaque2;


	@Enumerated(EnumType.STRING)
	private TypePokemon type;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}



	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}


	public Integer getAttaque1() {
		return attaque1;
	}

	public void setAttaque1(Integer attaque1) {
		this.attaque1 = attaque1;
	}



	public Integer getAttaque2() {
		return attaque2;
	}

	public void setAttaque2(Integer attaque2) { this.attaque2 = attaque2; }



	public TypePokemon getType() {
		return type;
	}

	public void setType(TypePokemon type) {
		this.type = type;
	}



	public String getUuid() {
		return uuid;
	}
}
