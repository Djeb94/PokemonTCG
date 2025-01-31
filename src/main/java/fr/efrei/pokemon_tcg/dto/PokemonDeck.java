package fr.efrei.pokemon_tcg.dto;

public class PokemonDeck {

    private String uuid;

    // Constructeur avec UUID
    public PokemonDeck(String uuid) {
        this.uuid = uuid;
    }

    // Getter et Setter
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
