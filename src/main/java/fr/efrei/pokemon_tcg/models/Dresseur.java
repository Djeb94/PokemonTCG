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


    @ManyToMany
    List<Pokemon> pokemonList;

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

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }


    public LocalDateTime getDateDernierGacha() {
        return dateDernierGacha;
    }

    public void setDateDernierGacha(LocalDateTime dateDernierGacha) {
        this.dateDernierGacha = dateDernierGacha;
    }


    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }
}