package fr.efrei.pokemon_tcg.services;

import fr.efrei.pokemon_tcg.dto.CombatDTO;
import fr.efrei.pokemon_tcg.models.Attaque;
import fr.efrei.pokemon_tcg.models.Combat;
import fr.efrei.pokemon_tcg.models.Dresseur;
import fr.efrei.pokemon_tcg.models.Pokemon;

import java.util.List;


public interface ICombatService {


    List<Combat> findAll();

    Combat findById(String uuid);

    Combat create(CombatDTO combatDTO);


}
