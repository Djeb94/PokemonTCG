package fr.efrei.pokemon_tcg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.efrei.pokemon_tcg.models.Combat;

import java.util.List;

public interface CombatRepository extends JpaRepository<Combat, String> {
    List<Combat> findAll();
}





