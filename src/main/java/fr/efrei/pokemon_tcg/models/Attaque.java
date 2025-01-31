package fr.efrei.pokemon_tcg.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Attaque {
    private String nom;
    private Integer degats;


    public Attaque() {}

    public Attaque(String nom, Integer degats) {
        this.nom = nom;
        this.degats = degats;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Integer getDegats() { return degats; }
    public void setDegats(Integer degats) { this.degats = degats; }
}
