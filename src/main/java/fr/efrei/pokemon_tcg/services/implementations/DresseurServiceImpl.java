package fr.efrei.pokemon_tcg.services.implementations;

import fr.efrei.pokemon_tcg.dto.CapturePokemon;
import fr.efrei.pokemon_tcg.dto.DresseurDTO;
import fr.efrei.pokemon_tcg.models.Dresseur;
import fr.efrei.pokemon_tcg.models.Pokemon;
import fr.efrei.pokemon_tcg.repositories.DresseurRepository;
import fr.efrei.pokemon_tcg.services.IDresseurService;
import fr.efrei.pokemon_tcg.services.IPokemonService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DresseurServiceImpl implements IDresseurService {

    private final DresseurRepository repository;
    private final IPokemonService pokemonService;
    public DresseurServiceImpl(DresseurRepository repository, PokemonServiceImpl pokemonService) {
        this.repository = repository;
        this.pokemonService = pokemonService;
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
        dresseur.getPokemonList().add(pokemon);
        repository.save(dresseur);
    }

    @Override
    public void create(DresseurDTO dresseurDTO) {
        Dresseur dresseur = new Dresseur();
        dresseur.setNom(dresseurDTO.getNom());
        dresseur.setPrenom(dresseurDTO.getPrenom());
        dresseur.setDeletedAt(null);
        dresseur.setDateDernierGacha(null);

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
        dresseurAModifier.setDateDernierGacha(LocalDateTime.now());

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
}