package Objetos;

public class Partido {
	private int numero_partido;
	private int votos_legenda;
	private String nome_partido;
	private String sigla_partido;
	
	public Partido(int num, String nome, String sigla, int votos){
		this.nome_partido = nome;
		this.numero_partido = num;
		this.sigla_partido = sigla;
		this.votos_legenda = votos;
	}
	
	
	int getNumero() {
		return this.numero_partido;
	}
	
	public int getVotos() {
		return this.votos_legenda;
	}
	
	String getNome() {
		return this.nome_partido;
	}
	
	String getSigla() {
		return this.sigla_partido;
	}

}
