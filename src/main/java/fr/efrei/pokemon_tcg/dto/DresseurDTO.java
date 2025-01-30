package fr.efrei.pokemon_tcg.dto;

import java.time.LocalDateTime;

public class DresseurDTO {
    private String nom;

    private String prenom;

    private LocalDateTime dateDernierGacha;

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public LocalDateTime getDateDernierGacha() { return dateDernierGacha; }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateDernierGacha(LocalDateTime dateDernierGacha) {
        this.dateDernierGacha = dateDernierGacha;
    }
}