package fr.efrei.pokemon_tcg.dto;

import java.time.LocalDateTime;

public class DresseurDTO {
    private String nom;

    private String prenom;

    private LocalDateTime dateDernierGacha;

    private LocalDateTime dateDernierEchange;


    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public LocalDateTime getDateDernierGacha() { return dateDernierGacha; }

    public LocalDateTime getDateDernierEchange() { return dateDernierEchange; }


    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateDernierGacha(LocalDateTime dateDernierGacha) {
        this.dateDernierGacha = dateDernierGacha;
    }

    public void setDateDernierEchange(LocalDateTime dateDernierEchange) {
        this.dateDernierEchange = dateDernierEchange;
    }

}