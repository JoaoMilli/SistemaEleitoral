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

    public void setNomePartido(String nomePartido) {
        this.nomePartido = nomePartido;
    }

	public boolean foiEleito() {
		return (Objects.equals(this.getSituacao(), "Eleito"));
	}

    @Override
    public String toString() {

		if (this.votosNominais <= 1){
			return this.nome + " / " + this.nomeUrna + " (" + this.nomePartido + ", " + this.votosNominais + " voto)";
		} else {
        	return this.nome + " / " + this.nomeUrna + " (" + this.nomePartido + ", " + this.votosNominais + " votos)";
		}
    }

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
