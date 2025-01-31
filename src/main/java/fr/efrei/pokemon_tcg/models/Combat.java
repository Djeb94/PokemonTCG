package fr.efrei.pokemon_tcg.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class Combat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    private String dresseur1Uuid;
    private String dresseur2Uuid;
    private String etat;
    private LocalDateTime debutCombat;



    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDresseur1Uuid() {
        return dresseur1Uuid;
    }

    public void setDresseur1Uuid(String dresseur1Uuid) {
        this.dresseur1Uuid = dresseur1Uuid;
    }

    public String getDresseur2Uuid() {
        return dresseur2Uuid;
    }

    public void setDresseur2Uuid(String dresseur2Uuid) {
        this.dresseur2Uuid = dresseur2Uuid;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public LocalDateTime getDebutCombat() {
        return debutCombat;
    }

    public void setDebutCombat(LocalDateTime debutCombat) {
        this.debutCombat = debutCombat;
    }
}


