package fr.efrei.pokemon_tcg.services.implementations;

import fr.efrei.pokemon_tcg.dto.CapturePokemon;
import fr.efrei.pokemon_tcg.dto.DresseurDTO;
import fr.efrei.pokemon_tcg.dto.EchangeDTO;
import fr.efrei.pokemon_tcg.dto.PokemonDeck;
import fr.efrei.pokemon_tcg.models.Combat;
import fr.efrei.pokemon_tcg.models.Dresseur;
import fr.efrei.pokemon_tcg.models.Pokemon;
import fr.efrei.pokemon_tcg.repositories.DresseurRepository;
import fr.efrei.pokemon_tcg.services.ICombatService;
import fr.efrei.pokemon_tcg.services.IDresseurService;
import fr.efrei.pokemon_tcg.services.IPokemonService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DresseurServiceImpl implements IDresseurService {

    private final DresseurRepository repository;
    private final IPokemonService pokemonService;
    private final ICombatService combatService;

    public DresseurServiceImpl(DresseurRepository repository, PokemonServiceImpl pokemonService, ICombatService combatService) {
        this.repository = repository;
        this.pokemonService = pokemonService;
        this.combatService = combatService;
    }

    @Override
    public List<Dresseur> findAll() {
        return repository.findAllByDeletedAtNull();
    }

    @Override
    public Dresseur findById(String uuid) {
        return repository.findById(uuid).orElse(null);
    }

    public void capturerPokemon(String uuid, CapturePokemon capturePokemon) {
        Dresseur dresseur = findById(uuid);
        Pokemon pokemon = pokemonService.findById(capturePokemon.getUuid());
        dresseur.getDeckGlobal().add(pokemon);
        repository.save(dresseur);
    }

    public void creerDeck(String uuid, PokemonDeck transferePokemon) {
        Dresseur dresseur = findById(uuid);
        Pokemon pokemon = pokemonService.findById(transferePokemon.getUuid());
        dresseur.getDeckCombat().add(pokemon);
        dresseur.getDeckGlobal().remove(pokemon);
        repository.save(dresseur);
    }

    public void creerCombat(String uuid, String combatUuid) {
        Dresseur dresseur = findById(uuid);
        Combat combat = combatService.findById(combatUuid);
        dresseur.getCombatEnCours().add(combat);
        repository.save(dresseur);
    }

    @Override
    public void create(DresseurDTO dresseurDTO) {
        Dresseur dresseur = new Dresseur();
        dresseur.setNom(dresseurDTO.getNom());
        dresseur.setPrenom(dresseurDTO.getPrenom());
        dresseur.setDeletedAt(null);
        dresseur.setDateDernierGacha(null);
        dresseur.setDateDernierEchange(null);

        repository.save(dresseur);
    }
@Override
    public boolean update(String uuid, DresseurDTO dresseurDTO) {
        // Récupérer le dresseur existant par son UUID
        Dresseur dresseurAModifier = findById(uuid); // Trouver le dresseur dans la base de données (ou ton repository)

        // Vérifier si le dresseur existe
        if (dresseurAModifier == null) {
            return false; // Si le dresseur n'existe pas, retourner false
        }

        // Mettre à jour les propriétés du dresseur avec les informations provenant du DTO
        dresseurAModifier.setNom(dresseurDTO.getNom());
        dresseurAModifier.setPrenom(dresseurDTO.getPrenom());

        // Mettre à jour la date du dernier gacha (ici, tu peux la mettre à la date actuelle)


        // Sauvegarder le dresseur modifié dans la base de données
        repository.save(dresseurAModifier);

        return true; // Retourner true si la mise à jour a réussi
    }

    @Override
    public boolean delete(String uuid) {
        Dresseur dresseur = findById(uuid);
        dresseur.setDeletedAt(LocalDateTime.now());
        repository.save(dresseur);
        return true;
    }

    @Override
    @Transactional
    public boolean echangerPokemon(String dresseurUuid, EchangeDTO echangeDTO) {
        // Trouver les dresseurs
        Dresseur dresseur1 = repository.findById(dresseurUuid).orElse(null);
        Dresseur dresseur2 = repository.findById(echangeDTO.getAutreDresseurUuid()).orElse(null);

        if (dresseur1 == null || dresseur2 == null) {
            return false; // Un des dresseurs n'existe pas
        }

        // Trouver les Pokémon
        Pokemon pokemon1 = findPokemonByUuid(dresseur1, echangeDTO.getPokemonUuid());
        Pokemon pokemon2 = findPokemonByUuid(dresseur2, echangeDTO.getAutrePokemonUuid());

        if (pokemon1 == null || pokemon2 == null) {
            return false; // Un des Pokémon n'existe pas
        }

        // Effectuer l'échange
        dresseur1.getDeckGlobal().remove(pokemon1);
        dresseur2.getDeckGlobal().remove(pokemon2);

        dresseur1.getDeckCombat().add(pokemon2);
        dresseur2.getDeckCombat().add(pokemon1);

        // Sauvegarder les modifications
        repository.save(dresseur1);
        repository.save(dresseur2);

        return true;
    }

    private Pokemon findPokemonByUuid(Dresseur dresseur, String pokemonUuid) {
        return dresseur.getDeckGlobal().stream()
                .filter(p -> p.getUuid().equals(pokemonUuid))
                .findFirst()
                .orElse(null);
    }

}