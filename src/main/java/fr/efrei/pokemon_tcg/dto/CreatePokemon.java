package fr.efrei.pokemon_tcg.dto;

import fr.efrei.pokemon_tcg.constants.TypePokemon;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public class CreatePokemon {

	@Length(min = 3, max = 20)
	private String nom;

	@Positive
	private Integer pv;

	@Positive
	private Integer attaque1;

	@Positive
	private Integer attaque2;

	private TypePokemon type;

	public String getNom() {
		return nom;
	}


	public Integer getPv() {
		return pv;
	}


	public Integer getAttaque1() {
		return attaque1;
	}

	public Integer getAttaque2() {
		return attaque2;
	}

	public TypePokemon getType() {
		return type;
	}
}
