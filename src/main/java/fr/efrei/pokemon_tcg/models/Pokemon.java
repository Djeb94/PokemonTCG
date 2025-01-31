package fr.efrei.pokemon_tcg.models;

import fr.efrei.pokemon_tcg.constants.TypePokemon;
import jakarta.persistence.*;

import java.util.List;
import java.util.Random;

@Entity
public class Pokemon {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	private String nom;

	private Integer pv;

	@ElementCollection
	@CollectionTable(name = "pokemon_attaques", joinColumns = @JoinColumn(name = "pokemon_id"))
	private List<Attaque> attaques;


	@Enumerated(EnumType.STRING)
	private TypePokemon type;

	private Integer rarete;


	public Pokemon() {
		this.rarete = genererRarete();
	}

	private Integer genererRarete() {
		Random random = new Random();
		int randomValue = random.nextInt(100);

		if (randomValue < 50) {
			return 1;
		} else if (randomValue < 80) {
			return 2;
		} else if (randomValue < 95) {
			return 3;
		} else if (randomValue < 99) {
			return 4;
		} else {
			return 5;
		}
	}


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



	public TypePokemon getType() {
		return type;
	}

	public void setType(TypePokemon type) {
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public Integer getRarete() {
		return rarete;
	}

	public void setRarete(Integer rarete) {
		this.rarete = rarete;
	}

	public List<Attaque> getAttaques() { return attaques; }

	public void setAttaques(List<Attaque> attaques) { this.attaques = attaques; }

}


