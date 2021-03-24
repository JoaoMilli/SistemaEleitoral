package Objetos;

import java.util.LinkedList;

public class Partido implements Comparable<Partido>{
	private int numero_partido;
	private int votos_legenda;
	private String nome_partido;
	private String sigla_partido;
	private int n_eleitos;
	private int votos_nominais;
	private Candidato mais_votado;
	private Candidato menos_votado;
	
	public Partido(int num, String nome, String sigla, int votos){
		this.nome_partido = nome;
		this.numero_partido = num;
		this.sigla_partido = sigla;
		this.votos_legenda = votos;
		this.votos_nominais = 0;
		this.n_eleitos = 0;
	}
	
	
	public int getNumero() {
		return this.numero_partido;
	}
	
	public int getVotos() {
		return this.votos_legenda;
	}
	
	public int getNEleitos() {
		return this.n_eleitos;
	}

	public String getNome() {
		return this.nome_partido;
	}
	
	public String getSigla() {
		return this.sigla_partido;
	}

	public int getVotosNominais() {
		return this.votos_nominais;
	}

	public Candidato getmaisVotado(){
		return this.mais_votado;
	}

	public Candidato getmenosVotado(){
		return this.menos_votado;
	}

	public void setEleitos(LinkedList<Candidato> lista){
		//Insere o mais votado, menos votado, o total de votos nominais e o numero de eleitos de um partido
		int i, n = 0;
		for(i=0; i < lista.size(); i++){
			if (lista.get(i).getNumeroPartido() == this.numero_partido){
				if (lista.get(i).foiEleito()) this.n_eleitos++;
				if (n == 0){
					this.mais_votado = lista.get(i);
					this.menos_votado = lista.get(i);
					n++;
				}
				else if (lista.get(i).getVotosNominais() > this.mais_votado.getVotosNominais()){
					this.mais_votado = lista.get(i);
				}
				else if (lista.get(i).getVotosNominais() < this.menos_votado.getVotosNominais()){
					this.menos_votado = lista.get(i);
				}
				this.votos_nominais += lista.get(i).getVotosNominais();
			}
		}
	}

	@Override
    public int compareTo(Partido o) {
        if (this.getNEleitos() > o.getNEleitos()){
            return -1;
        }
        else if (this.getNEleitos() < o.getNEleitos()){
            return 1;
        }
        else {
			if (this.votos_legenda + this.votos_nominais > o.votos_legenda + o.votos_nominais){
				return -1;
			}
			else {
				return 1;
			}
		}
    }

	@Override
    public String toString() {
		int totalVotos = this.votos_legenda + this.votos_nominais;
		String linha;
		if (this.votos_nominais <= 1 && totalVotos <= 1){
			linha = this.sigla_partido + " - " + this.numero_partido + ", " + totalVotos + " voto (" + this.votos_nominais + " nominal e " + this.votos_legenda + " de legenda), " + this.n_eleitos;
		}
		else if (this.votos_nominais <= 1){
			linha = this.sigla_partido + " - " + this.numero_partido + ", " + totalVotos + " votos (" + this.votos_nominais + " nominal e " + this.votos_legenda + " de legenda), " + this.n_eleitos;
		}
		else{
			linha = this.sigla_partido + " - " + this.numero_partido + ", " + totalVotos + " votos (" + this.votos_nominais + " nominais e " + this.votos_legenda + " de legenda), " + this.n_eleitos;
		}
		
        if (this.n_eleitos > 1) return linha + " candidatos eleitos";
		else return linha + " candidato eleito";
    }

}
