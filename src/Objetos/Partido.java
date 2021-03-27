//Classe que representa um partido

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
	

	/*Construtor da classe Partido*/
	/*Entradas: número do partido(int), nome do partido(String), sigla do partido(String), votos de legenda do partido(int)*/
	/*Saída: Objeto da classe Partido instanciado*/

	public Partido(int num, String nome, String sigla, int votos){
		this.nome_partido = nome;
		this.numero_partido = num;
		this.sigla_partido = sigla;
		this.votos_legenda = votos;
		this.votos_nominais = 0;
		this.n_eleitos = 0;
	}
	
	/*Getters*/

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

	/*Método que insere o mais votado(Candidato), menos votado(Candidato), o total de votos nominais(int) e o numero de eleitos de um partido (int) nos respectivos atributos*/
	/*Entrada: Lista de candidatos (LinkedList<Candidato>)*/
	/*Saída: nenhuma*/

	public void setEleitos(LinkedList<Candidato> lista){
		int i, n = 0;
		for(i=0; i < lista.size(); i++){
			if (lista.get(i).getNumeroPartido() == this.numero_partido){
				if (lista.get(i).foiEleito()) this.n_eleitos++;
				if (n == 0){
					this.mais_votado = lista.get(i);
					this.menos_votado = lista.get(i);
					n++;
				}
				else if (lista.get(i).getVotosNominais() > this.mais_votado.getVotosNominais() 
					|| ( lista.get(i).getVotosNominais() == this.mais_votado.getVotosNominais() 
						&& lista.get(i).getDataNasc().compareTo(this.mais_votado.getDataNasc()) == -1)){
					this.mais_votado = lista.get(i);
				}
				else if (lista.get(i).getVotosNominais() < this.menos_votado.getVotosNominais()
					|| ( lista.get(i).getVotosNominais() == this.menos_votado.getVotosNominais() 
						&& lista.get(i).getDataNasc().compareTo(this.menos_votado.getDataNasc()) == 1 )){
					this.menos_votado = lista.get(i);
				}
				this.votos_nominais += lista.get(i).getVotosNominais();
			}
		}
	}


	/*Método compareTo() da interface Comparable, compara dois Objetos Partido de acordo com o número de votos de legenda e por número de partido em caso de empate*/
	/*Entrada: O Objeto Partido a ser comparado(Partido)*/
	/*Saída: -1 caso o objeto comparado tenha menor número de votos de legenda e 1 caso contrário(int), em caso de empate retorna 1 caso o objeto comparado tenha o número de partido menor e -1 caso contrário(int)*/

	@Override
    public int compareTo(Partido o) {
		//Compara o número de votos totais do partido. O partido com mais votos tem prioridade
		if (this.votos_legenda + this.votos_nominais > o.votos_legenda + o.votos_nominais){
			return -1;
		}
		else if (this.votos_legenda + this.votos_nominais < o.votos_legenda + o.votos_nominais){
			return 1;
		} 
		//Caso partidos tenham a mesma quantidade de votos, o partido com número de legenda menor tem prioridade
		else if (this.getNumero() < o.getNumero()){
			return -1;
		}
		else {
			return 1;
		}	
    }

	/*Método toString() sobrescrito*/
	/*Entrada: nada*/
	/*Saída: String contendo dados importantes do Partido(String)*/

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
