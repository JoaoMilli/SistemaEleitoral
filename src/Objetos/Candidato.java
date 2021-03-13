package Objetos;

import java.util.Objects;

public class Candidato {

	private int numero;
	private int votosNominais;
	private String situacao;
	private String nome;
	private String nomeUrna;
	private String sexo;
	private String dataNasc;
	private String destinoVoto;
	private int numeroPartido;
    private String nomePartido;

	public Candidato(int num, int votosNominais, String situacao, String nome, String nomeUrna, String sexo, String dataNasc, String destinoVoto, int numeroPartido) {
		this.numero = num;
		this.votosNominais = votosNominais;
		this.situacao = situacao;
		this.nome = nome;
		this.nomeUrna = nomeUrna;
		this.sexo = sexo;
		this.dataNasc = dataNasc;
		this.destinoVoto = destinoVoto;
		this.numeroPartido = numeroPartido;
	}
	
	public String getNomePartido() {
        return nomePartido;
    }
    public int getNumero() {
		return this.numero;
	}
	public int getVotosNominais() {
		return this.votosNominais;
	}
	public String getSituacao() {
		return this.situacao;
	}
	public String getNome() {
		return this.nome;
	}
	public String getNomeUrna() {
		return this.nomeUrna;
	}
	public String getSexo() {
		return this.sexo;
	}
	public String getDataNasc() {
		return this.dataNasc;
	}
	public String getDestinoVoto() {
		return this.destinoVoto;
	}
	public int getNumeroPartido() {
		return this.numeroPartido;
	}

    public void setNomePartido(String nomePartido) {
        this.nomePartido = nomePartido;
    }
    
	void imprimeCandidato(String Partido) {
		System.out.println(this.nome + " / " + this.nomeUrna + " (" + Partido + ", " + this.votosNominais + " votos)");
	}
	public boolean foiEleito() {
		return (Objects.equals(this.getSituacao(), "Eleito"));
	}

}
