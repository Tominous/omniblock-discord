package net.omniblock.discord.manager.handler.type;

public enum RankType {

	NONE("NONE"),
	EVERYTHING("Everything"),
	ADMIN("Equipo de Administraci�n"),
	MODERATOR("Equipo de Moderaci�n"),
	DESIGNER("Equipo de Dise�o"),
	BUILDER("Equipo de Construcci�n"),
	DEVELOPER("Equipo de Desarrollo"),
	
	;
	
	private String title;
	
	RankType(String title){
		
		this.title = title;
		
	}

	public String getTitle() {
		return title;
	}
	
}
