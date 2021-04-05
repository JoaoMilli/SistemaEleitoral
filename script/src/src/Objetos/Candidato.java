//Classe que representa um candidato

package Objetos;

import java.util.Objects;

public class Candidato implements Comparable<Candidato> {

	private int numero; 
	private int votosNominais;
	private String situacao; 
	private String nome;
	private String nomeUrna;
	private String sexo;
	private Data dataNasc;
    private int idade;
	private String destinoVoto;
	private int numeroPartido;
    private String nomePartido;


	/*Construtor da classe Candidato*/

	/*Entradas: número do candidato(int), votos nominais do candidato(int), situação do candidato(String), nome do candidato(String), nome do candidato na urna(String), 
	sexo do candidato(String), data de nascimento do candidato(Data), idade do candidato (int), validade da candidatura do candidato(String), número do partido(int)*/

	/*Saída: Objeto da classe Candidato instanciado*/

	public Candidato(int num, int votosNominais, String situacao, String nome, String nomeUrna, String sexo, Data dataNasc, int idade, String destinoVoto, int numeroPartido) {
		this.numero = num;
		this.votosNominais = votosNominais;
		this.situacao = situacao;
		this.nome = nome;
		this.nomeUrna = nomeUrna;
		this.sexo = sexo;
		this.dataNasc = dataNasc;
        this.idade = idade;
		this.destinoVoto = destinoVoto;
		this.numeroPartido = numeroPartido;
	}
	
	/*Getters dos atributos*/
	public String getNomePartido() {
        return this.nomePartido;
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
	public Data getDataNasc() {
		return this.dataNasc;
	}
    public int getIdade() {
        return idade;
    }
	public String getDestinoVoto() {
		return this.destinoVoto;
	}
	public int getNumeroPartido() {
		return this.numeroPartido;
	}

	/*Método que define o nome do partido do candidato*/

	/*Entrada: nome do partido do candidato(String)*/

	/*Saida: atributo nomePartido do objeto candidato definido como o conteúdo da String de entrada*/

    public void setNomePartido(String nomePartido) {
        this.nomePartido = nomePartido;
    }

	/*Método que verifica se o candidato foi eleito*/
	/*Entrada: nada*/
	/*Saída: true se foi eleito, false caso contrário(boolean)*/

	public boolean foiEleito() {
		return (Objects.equals(this.getSituacao(), "Eleito"));
	}


	/*Método toString() sobrescrito*/
	/*Entrada: nada*/
	/*Saída: String contendo dados importantes do candidato(String)*/

    @Override
    public String toString() {

		if (this.votosNominais <= 1){
			return this.nome + " / " + this.nomeUrna + " (" + this.nomePartido + ", " + this.votosNominais + " voto)";
		} else {
        	return this.nome + " / " + this.nomeUrna + " (" + this.nomePartido + ", " + this.votosNominais + " votos)";
		}
    }

	/*Método compareTo() da interface Comparable, compara dois Objetos candidatos de acordo com o número de votos nominais e por data de nascimento em caso de empate*/
	/*Entrada: O Objeto candidato a ser comparado(Candidato)*/
	/*Saída: -1 caso o objeto comparado tenha menor número de votos nominais e 1 caso contrário(int), 
	em caso de empate retorna 1 caso o objeto comparado tenha menor idade e -1 caso contrário(int), retorna 0 se tiverem a mesma idade e mesmo numero de votos nominais(int)*/

    @Override
    public int compareTo(Candidato o) {
        if(this.getVotosNominais() > o.getVotosNominais()){
            //Se tiver mais votos, retorna que é menor, para ser inserido mais para o ínicio da lista
            return -1;
        }
        if(this.getVotosNominais() < o.getVotosNominais()){
            //Se tiver menos votos, retorna que é maior, para ser inserido mais para o final da lista
            return 1;
        }
        //Se for igual, verifica a idade, sendo que o mais velho vem antes
		if(this.getDataNasc().compareTo(o.getDataNasc()) == 1){ //this é mais novo que o
			return 1; //Colocado mais para o fim da lista
		}
		if(this.getDataNasc().compareTo(o.getDataNasc()) == -1){ //this é mais velho que o
			return -1; //Colocado com maior prioridade na frente da lista
		}

		//Se têm a mesma quantidade de votos e nasceram no mesmo dia...
        return 0;
    }

}
