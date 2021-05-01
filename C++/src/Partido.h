#ifndef PARTIDO_H
#define PARTIDO_H
#include "Candidato.h"
#include <vector>
#include <string>
#include <sstream>
#include <iostream>


class Partido
{
private:
    int numero_partido;
	int votos_legenda;
	string nome_partido;
	string sigla_partido;
	int n_eleitos;
	int votos_nominais;
	Candidato mais_votado;
	Candidato menos_votado;
public:
    /*Construtor da classe Partido*/
	/*Entradas: número do partido(int), nome do partido(string), sigla do partido(string), votos de legenda do partido(int)*/
	/*Saída: Objeto da classe Partido instanciado*/
	Partido(const int& num, const string& nome, const string& sigla, const int& votos);
	
	/*Getters*/

	int getNumero() const{
		return this->numero_partido;
	}
	
	int getVotos() const{
		return this->votos_legenda;
	}
	
	int getNEleitos() const{
		return this->n_eleitos;
	}

	string getNome() const{
		return this->nome_partido;
	}
	
	string getSigla() const{
		return this->sigla_partido;
	}

	int getVotosNominais() const{
		return this->votos_nominais;
	}

	Candidato getmaisVotado() const{
		return this->mais_votado;
	}

	Candidato getmenosVotado() const{
		return this->menos_votado;
	}

	/*Método que insere o mais votado(Candidato), menos votado(Candidato), o total de votos nominais(int) e o numero de eleitos de um partido (int) nos respectivos atributos*/
	/*Entrada: Lista de candidatos (vector<Candidato>)*/
	/*Saída: nenhuma*/
	void setEleitos(const vector<Candidato>& lista);

	/*Método cria uma string que representa o partido*/
	/*Entrada: nada*/
	/*Saída: string contendo dados importantes do Partido(string)*/
    string toString() const;
	
	/*Override do operador <<*/
    /*Envia o .toString() para o outstream*/
	friend ostream& operator<<(ostream &out, const Partido& partido);

    ~Partido(){}
};



#endif