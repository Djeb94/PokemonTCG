package fr.efrei.pokemon_tcg.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Dresseur {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String nom;

    private String prenom;

    private LocalDateTime deletedAt;

    private LocalDateTime dateDernierGacha;

    private LocalDateTime dateDernierEchange;



    @ManyToMany
    private List<Pokemon> deckCombat;

    @ManyToMany
    private List<Pokemon> deckGlobal;

    @ManyToMany
    private List<Combat> combatEnCours;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }


    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<Pokemon> getDeckCombat() {
        return deckCombat;
    }
    public void setDeckCombat(List<Pokemon> deckCombat) {
        this.deckCombat = deckCombat;
    }

    public List<Pokemon> getDeckGlobal() {
        return deckGlobal;
    }

    public void setDeckGlobal(List<Pokemon> deckGlobal) {
        this.deckGlobal = deckGlobal;
    }


    public LocalDateTime getDateDernierGacha() {
        return dateDernierGacha;
    }

    public void setDateDernierGacha(LocalDateTime dateDernierGacha) {
        this.dateDernierGacha = dateDernierGacha;
    }

    public LocalDateTime getDateDernierEchange() {
        return dateDernierEchange;
    }

    public void setDateDernierEchange(LocalDateTime dateDernierEchange) {
        this.dateDernierEchange = dateDernierEchange;
    }


    public List<Combat> getCombatEnCours() {
        return combatEnCours;
    }

    public void setCombatEnCours(List<Combat> combatEnCours) {
        this.combatEnCours = combatEnCours;
    }
}