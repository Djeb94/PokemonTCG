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
			@PathVariable String uuid,
			@RequestParam(required = false) TypePokemon type) {

		Dresseur dresseur = dresseurService.findById(uuid);

		if (dresseur.getDateDernierGacha() == null || dresseur.getDateDernierGacha().isBefore(LocalDateTime.now().minusDays(1))) {

			List<Pokemon> pokemons = pokemonService.findAll(type);

			if (pokemons.size() < 5) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			Collections.shuffle(pokemons);
			List<Pokemon> randomPokemons = pokemons.subList(0, 5);

			for (Pokemon randomPokemon : randomPokemons) {
				CapturePokemon capturePokemon = new CapturePokemon(randomPokemon.getUuid());
				dresseurService.capturerPokemon(uuid, capturePokemon);
			}

			// Mise à jour du dernier gacha
			DresseurDTO dresseurDTO = new DresseurDTO();
			dresseurDTO.setNom(dresseur.getNom());
			dresseurDTO.setPrenom(dresseur.getPrenom());

			dresseur.setDateDernierGacha(LocalDateTime.now());

			boolean isModifier = dresseurService.update(uuid, dresseurDTO);

			if (!isModifier) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<>(randomPokemons, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}



	@PatchMapping("/{uuid}/echange")
	public ResponseEntity<?> echangerPokemon(
			@PathVariable String uuid,
			@RequestBody EchangeDTO echangeDTO) {

		Dresseur dresseur = dresseurService.findById(uuid);

		// Vérifier si le dresseur peut échanger
		if (dresseur.getDateDernierEchange() == null || dresseur.getDateDernierEchange().isBefore(LocalDateTime.now().minusDays(1))) {

			boolean echangeReussi = dresseurService.echangerPokemon(uuid, echangeDTO);

			if (!echangeReussi) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			dresseur.setDateDernierEchange(LocalDateTime.now());
			DresseurDTO dresseurDTO = new DresseurDTO();
			dresseurDTO.setNom(dresseur.getNom());
			dresseurDTO.setPrenom(dresseur.getPrenom());


			boolean isModifier = dresseurService.update(uuid, dresseurDTO);

			if (!isModifier) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<>(HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{uuid}/deck")
	public ResponseEntity<List<Pokemon>> deck(
			@PathVariable String uuid,
			@RequestBody List<String> pokemonUuids) {

		Dresseur dresseur = dresseurService.findById(uuid);
		if (dresseur == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (pokemonUuids.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		List<Pokemon> pokemonsADeplacer = new ArrayList<>();

		for (String pokemonUuid : pokemonUuids) {
			PokemonDeck transferePokemon = new PokemonDeck(pokemonUuid);

			dresseurService.creerDeck(uuid, transferePokemon);

			Pokemon pokemon = pokemonService.findById(pokemonUuid);
			pokemonsADeplacer.add(pokemon);
		}

		return new ResponseEntity<>(pokemonsADeplacer, HttpStatus.OK);
	}

}

