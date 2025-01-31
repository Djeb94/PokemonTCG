package fr.efrei.pokemon_tcg.dto;

public class CapturePokemon {

	private String uuid;

	public CapturePokemon(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
