package fr.efrei.pokemon_tcg.dto;

public class EchangeDTO {
    private String pokemonUuid;
    private String autreDresseurUuid;
    private String autrePokemonUuid;

    public String getPokemonUuid() { return pokemonUuid; }
    public void setPokemonUuid(String pokemonUuid) { this.pokemonUuid = pokemonUuid; }

    public String getAutreDresseurUuid() { return autreDresseurUuid; }
    public void setAutreDresseurUuid(String autreDresseurUuid) { this.autreDresseurUuid = autreDresseurUuid; }

    public String getAutrePokemonUuid() { return autrePokemonUuid; }
    public void setAutrePokemonUuid(String autrePokemonUuid) { this.autrePokemonUuid = autrePokemonUuid; }
}
