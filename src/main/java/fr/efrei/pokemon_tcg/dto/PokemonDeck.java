package fr.efrei.pokemon_tcg.dto;

public class PokemonDeck {

    private String uuid;

    public PokemonDeck(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
