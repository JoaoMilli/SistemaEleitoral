#ifndef CANDIDATO_H
#define CANDIDATO_H
#include <cstdio>
#include <string>
#include <sstream>
#include <iostream>

using namespace std;


class Candidato{
private:
    /* data */
    int numero; 
	int votosNominais;
	string situacao; 
	string nome;
	string nomeUrna;
	string sexo;
	string dataNasc;
    int idade;
	string destinoVoto;
	int numeroPartido;
    string nomePartido;
public:
    /*Construtor da classe Candidato*/
	/*Entradas: número do candidato(int), votos nominais do candidato(int), situação do candidato(string), nome do candidato(string), nome do candidato na urna(string), 
	sexo do candidato(string), data de nascimento do candidato(Data), idade do candidato (int), validade da candidatura do candidato(string), número do partido(int)*/
	/*Saída: Objeto da classe Candidato instanciado*/
	Candidato(const int& num, const int& votosNominais, const string& situacao, const string& nome, const string& nomeUrna, 
        const string& sexo, const string& dataNasc, const int& idade, const string& destinoVoto, const int& numeroPartido);

    Candidato(/* Construtor padrão */);
	
	/*Getters dos atributos. Implementados aqui para serem conisderados inline*/
	string getNomePartido() const{
        return this->nomePartido;
    }
    int getNumero() const{
        return this->numero;
    }
	int getVotosNominais() const{
        return this->votosNominais;
    }
	string getSituacao() const{
        return this->situacao;
    }
	string getNome() const{
        return this->nome;
    }
	string getNomeUrna() const{
        return this->nomeUrna;
    }
	string getSexo() const{
        return this->sexo;
    }
	string getDataNasc() const{
        return this->dataNasc;
    }
    int getIdade() const{
        return this->idade;
    }
	string getDestinoVoto() const{
        return this->destinoVoto;
    }
	int getNumeroPartido() const{
        return this->numeroPartido;
    }

	/*Método que define o nome do partido do candidato*/
	/*Entrada: nome do partido do candidato(string)*/
	/*Saida: atributo nomePartido do objeto candidato definido como o conteúdo da string de entrada*/
    void setNomePartido(const string& nomePartido);

	/*Método que verifica se o candidato foi eleito*/
	/*Entrada: nada*/
	/*Saída: true se foi eleito, false caso contrário(bool)*/
	bool foiEleito() const;


	/*Método que retorna uma string representando o objeto*/
	/*Entrada: nada*/
	/*Saída: string contendo dados importantes do candidato(string)*/
    string toString() const;

    /*Override do operador <<*/
    /*Envia o .toString() para o outstream*/
    friend ostream& operator<<(ostream &out, const Candidato& candidato);

    ~Candidato(){}
};





#endif