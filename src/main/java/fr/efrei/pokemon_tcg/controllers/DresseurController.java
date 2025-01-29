package fr.efrei.pokemon_tcg.controllers;

import fr.efrei.pokemon_tcg.constants.TypePokemon;
import fr.efrei.pokemon_tcg.dto.CapturePokemon;
import fr.efrei.pokemon_tcg.dto.DresseurDTO;
import fr.efrei.pokemon_tcg.models.Dresseur;
import fr.efrei.pokemon_tcg.models.Pokemon;
import fr.efrei.pokemon_tcg.services.IDresseurService;
import fr.efrei.pokemon_tcg.services.IPokemonService;
import fr.efrei.pokemon_tcg.services.implementations.DresseurServiceImpl;
import fr.efrei.pokemon_tcg.services.implementations.PokemonServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
		List<Pokemon> pokemons = pokemonService.findAll(type); // Récupère la liste des Pokémon, filtrée si type est fourni

		// Vérifier qu'il y a au moins 5 Pokémon
		if (pokemons.size() < 5) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Retourner une erreur si pas assez de Pokémon
		}

		// Sélectionner 5 Pokémon au hasard
		List<Pokemon> randomPokemons = new ArrayList<>();
		Random rand = new Random();

		// Assurer qu'on n'ajoute pas un Pokémon déjà sélectionné
		while (randomPokemons.size() < 5) {
			int randomIndex = rand.nextInt(pokemons.size());
			Pokemon randomPokemon = pokemons.get(randomIndex);

			// Vérifier si le Pokémon est déjà sélectionné
			if (!randomPokemons.contains(randomPokemon)) {
				randomPokemons.add(randomPokemon);

				// Capturer le Pokémon (passer l'UUID du Pokémon au service)
				CapturePokemon capturePokemon = new CapturePokemon(randomPokemon.getUuid());
				dresseurService.capturerPokemon(uuid, capturePokemon);
			}
		}

		return new ResponseEntity<>(randomPokemons, HttpStatus.OK);
	}

}
