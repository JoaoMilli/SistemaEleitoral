#include <iostream>
#include "Data.h"

using namespace std;

string Data::DATE_FORMAT_PT_BR_SHORT = "%d/%m/%Y";

Data::Data (const string& data) {
    if(Data::validDate(data)){
        sscanf(data.c_str(), "%d/%d/%d", &this->dia, &this->mes, &this->ano);
    } else {
        cout << "Erro! Data inválida" << endl;
    }
}

Data::Data (/* Construtor padrão */){}

Data::~Data(){}

/*Verifica se a string passada é uma data válida. Utiliza format DATE_FORMAT_PT_BR_SHORT*/
bool Data::validDate(const string& str) {
	//Lógica feita originalmente pelo professor Vitor Souza
	struct tm tm;
	return strptime(str.c_str(), DATE_FORMAT_PT_BR_SHORT.c_str(), &tm);
}

/*Método que retorna o número de anos passados entre datas (int)*/
/*Entradas: Data de referência(Data)*/
/*Saída: Diferença de anos entre a data representada no objeto pela data de referência(int)*/
int Data::anosPassados(const Data& dataDepois) const{
	int anos = dataDepois.getAno() - this->ano - 1;
	if(dataDepois.getMes() > this->mes){
		anos++;
	} else if(dataDepois.getMes() == this->mes && dataDepois.getDia() > this->dia){
		anos++;
	}
	return anos;
}


/*Método compareTo() da interface Comparable, compara dois Objetos Data*/
/*Entrada: O Objeto Data a ser comparado(Data)*/
/*Saída: 1 caso a data representada pelo objeto for posterior à data comparada, -1 caso seja anterior e 0 caso as datas sejam iguais*/
int Data::compareTo(const Data& outraData) const{
	if(this->ano > outraData.getAno()){ //Vem depois
		return 1;
	} else if (this->ano < outraData.getAno()){ //Vem antes da outra data
		return -1;
	} else if (this->mes > outraData.getMes()){ //Vem depois da outra data
		return 1;
	} else if (this->mes < outraData.getMes()){ //Vem antes da outra data
		return -1;
	} else if (this->dia > outraData.getDia()){ //Vem depois da outra data
		return 1;
	} else if (this->dia < outraData.getDia()){ //Vem antes da outra data
		return -1;
	} else { //Mesma data
		return 0;
	}
}


