package fr.efrei.pokemon_tcg.dto;

public class AttaqueRequest {
    private String combatUuid;
    private String dresseurUuid;
    private String pokemonAttaquantUuid;
    private String pokemonCibleUuid;
    private String nomAttaque;

    // Getters et Setters
    public String getCombatUuid() { return combatUuid; }
    public void setCombatUuid(String combatUuid) { this.combatUuid = combatUuid; }

    public String getDresseurUuid() { return dresseurUuid; }
    public void setDresseurUuid(String dresseurUuid) { this.dresseurUuid = dresseurUuid; }

    public String getPokemonAttaquantUuid() { return pokemonAttaquantUuid; }
    public void setPokemonAttaquantUuid(String pokemonAttaquantUuid) { this.pokemonAttaquantUuid = pokemonAttaquantUuid; }

    public String getPokemonCibleUuid() { return pokemonCibleUuid; }
    public void setPokemonCibleUuid(String pokemonCibleUuid) { this.pokemonCibleUuid = pokemonCibleUuid; }

    public String getNomAttaque() { return nomAttaque; }
    public void setNomAttaque(String nomAttaque) { this.nomAttaque = nomAttaque; }
}
