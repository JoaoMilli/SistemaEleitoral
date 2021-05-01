#ifndef DATA_H_
#define DATA_H_

#include <ctime>
#include <string>
#include <iostream>

using namespace std;
class Data
{
private:
    int dia;
    int mes;
    int ano;
public:
    static string DATE_FORMAT_PT_BR_SHORT;
    
    /*Construtor da classe Data*/
	/*Entradas: String representado uma data separada em dia, mês e ano por / (String)*/
	/*Saída: Objeto da classe Data instanciado*/
    Data (const string& data);
    Data (/* Construtor padrão */);

    /*Getters*/
	int getAno() const{
		return this->ano;
	}
	
	int getMes() const{
		return this->mes;
	}
	
	int getDia() const{
		return this->dia;
	}



    /*Verifica se a string passada é uma data válida. Utiliza format DATE_FORMAT_PT_BR_SHORT*/
    static bool validDate(const string& str);

    /*Método que retorna o número de anos passados entre datas (int)*/
	/*Entradas: Data de referência(Data)*/
	/*Saída: Diferença de anos entre a data representada no objeto pela data de referência(int)*/
    int anosPassados(const Data& dataDepois) const;

    /*Método compareTo() da interface Comparable, compara dois Objetos Data*/
	/*Entrada: O Objeto Data a ser comparado(Data)*/
	/*Saída: 1 caso a data representada pelo objeto for posterior à data comparada, -1 caso seja anterior e 0 caso as datas sejam iguais*/
    int compareTo(const Data& outraData);

    ~Data();
};


#endif
