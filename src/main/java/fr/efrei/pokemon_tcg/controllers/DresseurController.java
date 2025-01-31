package fr.efrei.pokemon_tcg.controllers;

import fr.efrei.pokemon_tcg.constants.TypePokemon;
import fr.efrei.pokemon_tcg.dto.CapturePokemon;
import fr.efrei.pokemon_tcg.dto.DresseurDTO;
import fr.efrei.pokemon_tcg.dto.EchangeDTO;
import fr.efrei.pokemon_tcg.dto.PokemonDeck;
import fr.efrei.pokemon_tcg.models.Dresseur;
import fr.efrei.pokemon_tcg.models.Pokemon;
import fr.efrei.pokemon_tcg.services.IDresseurService;
import fr.efrei.pokemon_tcg.services.IPokemonService;
import fr.efrei.pokemon_tcg.services.implementations.DresseurServiceImpl;
import fr.efrei.pokemon_tcg.services.implementations.PokemonServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dresseurs")
public class DresseurController {

	private final IDresseurService dresseurService;
	private final IPokemonService pokemonService;


	public DresseurController(DresseurServiceImpl dresseurService, PokemonServiceImpl pokemonService) {
		this.dresseurService = dresseurService;
		this.pokemonService = pokemonService;

	}

	@GetMapping
	public ResponseEntity<List<Dresseur>> findAll() {
		return new ResponseEntity<>(dresseurService.findAll(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody DresseurDTO dresseurDTO) {
		dresseurService.create(dresseurDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("/{uuid}")
	public ResponseEntity<?> delete(@PathVariable String uuid) {
		dresseurService.delete(uuid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping("/{uuid}/capturer")
	public ResponseEntity<?> capturer(
			@PathVariable String uuid,
			@RequestBody CapturePokemon capturePokemon
	) {
		dresseurService.capturerPokemon(uuid, capturePokemon);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping("/{uuid}/acheter")
	public ResponseEntity<?> acheter() {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping("/{uuid}/gacha")
	public ResponseEntity<List<Pokemon>> gacha(
			@PathVariable String uuid, // Récupère l'UUID du dresseur
			@RequestParam(required = false) TypePokemon type) { // Récupère le type de Pokémon si spécifié

		// Récupérer le dresseur par UUID
		Dresseur dresseur = dresseurService.findById(uuid);

		// Vérifier si le dresseur peut faire un gacha (s'il a fait un gacha avant hier)
		if (dresseur.getDateDernierGacha() == null || dresseur.getDateDernierGacha().isBefore(LocalDateTime.now().minusDays(1))) {

			// Récupère la liste des Pokémon disponibles pour le gacha
			List<Pokemon> pokemons = pokemonService.findAll(type);

			// Vérifier qu'il y a au moins 5 Pokémon
			if (pokemons.size() < 5) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Retourner une erreur si pas assez de Pokémon
			}

			// Mélanger la liste pour obtenir 5 Pokémon uniques
			Collections.shuffle(pokemons);
			List<Pokemon> randomPokemons = pokemons.subList(0, 5);

			// Capturer les Pokémon sélectionnés
			for (Pokemon randomPokemon : randomPokemons) {
				CapturePokemon capturePokemon = new CapturePokemon(randomPokemon.getUuid());
				dresseurService.capturerPokemon(uuid, capturePokemon);
			}

			// Mise à jour du dernier gacha
			DresseurDTO dresseurDTO = new DresseurDTO();
			dresseurDTO.setNom(dresseur.getNom());
			dresseurDTO.setPrenom(dresseur.getPrenom());

			// Met à jour la date du dernier gacha
			dresseur.setDateDernierGacha(LocalDateTime.now());

			// Sauvegarde des modifications
			boolean isModifier = dresseurService.update(uuid, dresseurDTO);

			if (!isModifier) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<>(randomPokemons, HttpStatus.OK);
		} else {
			// Le dresseur a déjà effectué un gacha récemment (hier ou aujourd'hui)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}



	@PatchMapping("/{uuid}/echange")
	public ResponseEntity<?> echangerPokemon(
			@PathVariable String uuid,
			@RequestBody EchangeDTO echangeDTO) {

		Dresseur dresseur = dresseurService.findById(uuid); // Ou dresseurRepository.findById(uuid)

		// Vérifier si le dresseur peut échanger
		if (dresseur.getDateDernierEchange() == null || dresseur.getDateDernierEchange().isBefore(LocalDateTime.now().minusDays(1))) {

			boolean echangeReussi = dresseurService.echangerPokemon(uuid, echangeDTO);

			if (!echangeReussi) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Si l'échange échoue
			}

			// Si l'échange réussit, mettre à jour la date du dernier échange du dresseur
			dresseur.setDateDernierEchange(LocalDateTime.now());
			DresseurDTO dresseurDTO = new DresseurDTO();
			dresseurDTO.setNom(dresseur.getNom());
			dresseurDTO.setPrenom(dresseur.getPrenom());


			boolean isModifier = dresseurService.update(uuid, dresseurDTO);

			if (!isModifier) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Si la mise à jour échoue
			}

			return new ResponseEntity<>(HttpStatus.OK); // Réponse si l'échange et la mise à jour sont réussis

		} else {
			// Le dresseur a déjà effectué un échange récemment (hier ou aujourd'hui)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{uuid}/deck")
	public ResponseEntity<List<Pokemon>> deck(
			@PathVariable String uuid, // Récupère l'UUID du dresseur
			@RequestBody List<String> pokemonUuids) { // Liste d'UUID de Pokémon à transférer

		// Récupérer le dresseur par UUID
		Dresseur dresseur = dresseurService.findById(uuid);
		if (dresseur == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si dresseur introuvable
		}

		// Vérifier que la liste contient des UUID de Pokémon
		if (pokemonUuids.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Si la liste est vide
		}

		// Liste pour stocker les Pokémon transférés
		List<Pokemon> pokemonsADeplacer = new ArrayList<>();

		// Ajouter les Pokémon au deckCombat
		for (String pokemonUuid : pokemonUuids) {
			// Récupérer le Pokémon par son UUID
			PokemonDeck transferePokemon = new PokemonDeck(pokemonUuid);

			// Appeler la méthode creerDeck pour transférer le Pokémon
			dresseurService.creerDeck(uuid, transferePokemon);

			// Ajouter à la liste des Pokémon transférés
			Pokemon pokemon = pokemonService.findById(pokemonUuid);
			pokemonsADeplacer.add(pokemon);
		}

		// Retourner les Pokémon transférés
		return new ResponseEntity<>(pokemonsADeplacer, HttpStatus.OK);
	}

}

