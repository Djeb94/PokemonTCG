package fr.efrei.pokemon_tcg.controllers;

import fr.efrei.pokemon_tcg.dto.AttaqueRequest;
import fr.efrei.pokemon_tcg.dto.CombatDTO;
import fr.efrei.pokemon_tcg.models.Attaque;
import fr.efrei.pokemon_tcg.models.Combat;
import fr.efrei.pokemon_tcg.models.Dresseur;
import fr.efrei.pokemon_tcg.models.Pokemon;
import fr.efrei.pokemon_tcg.services.ICombatService;
import fr.efrei.pokemon_tcg.services.IDresseurService;
import fr.efrei.pokemon_tcg.services.implementations.CombatServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/combats")
public class CombatController {

    private final ICombatService combatService;
    private final IDresseurService dresseurService;


    public CombatController(CombatServiceImpl combatService, IDresseurService dresseurService) {
        this.combatService = combatService;
        this.dresseurService = dresseurService;
    }

    @GetMapping
    public ResponseEntity<List<Combat>> findAll() {
        return new ResponseEntity<>(combatService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CombatDTO combatDTO) {

        String dresseur1uuid = combatDTO.getDresseur1Uuid();
        String dresseur2uuid = combatDTO.getDresseur2Uuid();
        Dresseur dresseur1 = dresseurService.findById(dresseur1uuid);
        Dresseur dresseur2 = dresseurService.findById(dresseur2uuid);


        if (dresseur1 == null || dresseur2 == null || dresseur1.equals(dresseur2) || dresseur1.getDeckCombat().size() == 4 || dresseur2.getDeckCombat().size() == 4 ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

            Combat combat = combatService.create(combatDTO);
            String combatUuid = combat.getUuid();
            dresseurService.creerCombat(dresseur1uuid, combatUuid);
            dresseurService.creerCombat(dresseur2uuid, combatUuid);

        return new ResponseEntity<>(HttpStatus.CREATED);


    }

    @GetMapping("/{uuid}")
    public ResponseEntity<List<List<Pokemon>>> getById(@PathVariable String uuid) {
        Combat combat = combatService.findById(uuid);
        Dresseur dresseur1 = dresseurService.findById(combat.getDresseur1Uuid());
        Dresseur dresseur2 = dresseurService.findById(combat.getDresseur2Uuid());

        if(combat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<List<Pokemon>> decks = List.of(dresseur1.getDeckCombat(), dresseur2.getDeckCombat());

        return ResponseEntity.ok(decks);

    }

    @PostMapping("/attaque")
    public ResponseEntity<?> attaque(
            @PathVariable String uuid,
            @PathVariable String uuid2,
            @RequestBody CombatDTO combatDTO,
            @RequestBody AttaqueRequest request) {
        Combat combat = combatService.findById(request.getCombatUuid());
        Dresseur dresseur = dresseurService.findById(request.getDresseurUuid());

        if (combat == null || dresseur == null) {
            return new ResponseEntity<>("Combat ou dresseur introuvable", HttpStatus.NOT_FOUND);
        }

        Pokemon attaquant = dresseur.getDeckCombat().stream()
                .filter(p -> p.getUuid().equals(request.getPokemonAttaquantUuid()))
                .findFirst()
                .orElse(null);

        if (attaquant == null) {
            return new ResponseEntity<>("Pokémon attaquant introuvable dans le deck du dresseur", HttpStatus.BAD_REQUEST);
        }

        Attaque attaque = attaquant.getAttaques().stream()
                .filter(a -> a.getNom().equalsIgnoreCase(request.getNomAttaque()))
                .findFirst()
                .orElse(null);

        if (attaque == null) {
            return new ResponseEntity<>("Attaque introuvable pour ce Pokémon", HttpStatus.BAD_REQUEST);
        }

        Dresseur adversaire = combat.getDresseur1Uuid().equals(dresseur.getUuid()) ?
                dresseurService.findById(combat.getDresseur2Uuid()) :
                dresseurService.findById(combat.getDresseur1Uuid());

        Pokemon cible = adversaire.getDeckCombat().stream()
                .filter(p -> p.getUuid().equals(request.getPokemonCibleUuid()))
                .findFirst()
                .orElse(null);

        if (cible == null) {
            return new ResponseEntity<>("Pokémon cible introuvable dans le deck adverse", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("Attaque exécutée avec succès !");
    }


}



