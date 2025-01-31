package fr.efrei.pokemon_tcg.dto;

import fr.efrei.pokemon_tcg.constants.TypePokemon;
import fr.efrei.pokemon_tcg.models.Attaque;
import fr.efrei.pokemon_tcg.models.Dresseur;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class CreatePokemon {

	@Length(min = 3, max = 20)
	private String nom;

	@Positive
	private Integer pv;

	@Positive
	private Integer rarete;

	private List<Attaque> attaques;

	private TypePokemon type;

	private Dresseur dresseur;

	public String getNom() {
		return nom;
	}


	public Integer getPv() {
		return pv;
	}


	public Integer getRarete() {
		return rarete;
	}

	public TypePokemon getType() {
		return type;
	}

	public List<Attaque> getAttaques() { return attaques; }

	public Dresseur getDresseur() { return dresseur; }
}
