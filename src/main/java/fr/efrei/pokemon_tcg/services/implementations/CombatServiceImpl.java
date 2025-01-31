package fr.efrei.pokemon_tcg.services.implementations;

import fr.efrei.pokemon_tcg.dto.CombatDTO;
import fr.efrei.pokemon_tcg.models.Attaque;
import fr.efrei.pokemon_tcg.models.Combat;
import fr.efrei.pokemon_tcg.models.Dresseur;
import fr.efrei.pokemon_tcg.models.Pokemon;
import fr.efrei.pokemon_tcg.repositories.CombatRepository;
import fr.efrei.pokemon_tcg.services.ICombatService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CombatServiceImpl implements ICombatService {

    private final CombatRepository repository;

    public CombatServiceImpl(CombatRepository repository) {
        this.repository = repository;

    }

    @Override
    public List<Combat> findAll() {
        return repository.findAll();
    }

    @Override
    public Combat findById(String uuid) {
        return repository.findById(uuid).orElse(null);
    }


    @Override
    public Combat create(CombatDTO combatDTO) {
        Combat combat = new Combat();
        // Mapper les données de combatDTO vers combat
        combat.setDresseur1Uuid(combatDTO.getDresseur1Uuid());
        combat.setDresseur2Uuid(combatDTO.getDresseur2Uuid());
        combat.setEtat(combatDTO.getEtat());
        combat.setDebutCombat(LocalDateTime.now());

        return repository.save(combat);
    }

    //public Combat executerAttaque(Combat combat, Dresseur dresseur, Pokemon attaquant, Pokemon cible, Attaque attaque) {}
}
