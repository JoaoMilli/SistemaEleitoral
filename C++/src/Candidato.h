#ifndef CANDIDATO_H
#define CANDIDATO_H
#include <cstdio>
#include <string>
#include <sstream>
#include <iostream>

#include "Data.h"

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
	Data dataNasc;
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
        const string& sexo, const Data& dataNasc, const int& idade, const string& destinoVoto, const int& numeroPartido);

    Candidato(/* Construtor padrão */);
	
	/*Getters dos atributos. Implementados aqui para serem conisderados inline*/
	const string& getNomePartido() const{
        return this->nomePartido;
    }
    const int& getNumero() const{
        return this->numero;
    }
	const int& getVotosNominais() const{
        return this->votosNominais;
    }
	const string& getSituacao() const{
        return this->situacao;
    }
	const string& getNome() const{
        return this->nome;
    }
	const string& getNomeUrna() const{
        return this->nomeUrna;
    }
	const string& getSexo() const{
        return this->sexo;
    }
	const Data& getDataNasc() const{
        return this->dataNasc;
    }
    const int& getIdade() const{
        return this->idade;
    }
	const string& getDestinoVoto() const{
        return this->destinoVoto;
    }
	const int& getNumeroPartido() const{
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

    /*Override do operador << */
    /*Envia o .toString() para o outstream*/
    friend ostream& operator<<(ostream &out, const Candidato& candidato);

    /*Override do operador < */
    /*Define quando um candidato é "menor" que o outro*/
    /*Compara dois Objetos candidatos de acordo com o número de votos nominais 
    e por data de nascimento em caso de empate*/
    /*Entrada: O Objeto candidato a ser comparado(Candidato)*/
    /*Saída: true caso o objeto comparado tenha menor número de votos nominais e false caso contrário, 
	em caso de empate retorna false caso o objeto comparado tenha menor idade e true caso contrário, 
    retorna false se tiverem a mesma idade e mesmo numero de votos nominais*/
    bool operator<(const Candidato& candidato) const;

    ~Candidato(){}
};





#endif