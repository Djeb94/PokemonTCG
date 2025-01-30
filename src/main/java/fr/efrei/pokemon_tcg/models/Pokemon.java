package fr.efrei.pokemon_tcg.models;

import fr.efrei.pokemon_tcg.constants.TypePokemon;
import jakarta.persistence.*;
import java.util.Random;

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

	// Ajout de la variable rareté
	private Integer rarete;

	// Constructeur pour générer la rareté lors de l'instanciation
	public Pokemon() {
		this.rarete = genererRarete();
	}

	private Integer genererRarete() {
		Random random = new Random();
		int randomValue = random.nextInt(100); // Génère un nombre entre 0 et 99

		if (randomValue < 50) {
			return 1; // 50% de chance pour la rareté 1
		} else if (randomValue < 80) {
			return 2; // 30% de chance pour la rareté 2
		} else if (randomValue < 95) {
			return 3; // 15% de chance pour la rareté 3
		} else if (randomValue < 99) {
			return 4; // 4% de chance pour la rareté 4
		} else {
			return 5; // 1% de chance pour la rareté 5
		}
	}


	// Getters et Setters
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

	public void setAttaque2(Integer attaque2) {
		this.attaque2 = attaque2;
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
}
