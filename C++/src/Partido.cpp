#include "Partido.h"

/*Construtor da classe Partido*/
/*Entradas: número do partido(int), nome do partido(string), sigla do partido(string), votos de legenda do partido(int)*/
/*Saída: Objeto da classe Partido instanciado*/
Partido::Partido(const int& num, const string& nome, const string& sigla, const int& votos){
    this->nome_partido = nome;
    this->numero_partido = num;
    this->sigla_partido = sigla;
    this->votos_legenda = votos;
    this->votos_nominais = 0;
    this->n_eleitos = 0;
}


/*Método que insere o mais votado(Candidato), menos votado(Candidato), o total de votos nominais(int) e o numero de eleitos de um partido (int) nos respectivos atributos*/
/*Entrada: Lista de candidatos (vector<Candidato>)*/
/*Saída: nenhuma*/
void Partido::setEleitos(const vector<Candidato>& lista){
    int i, n = 0;
    for(i=0; i < (int) lista.size(); i++){
        if (lista[i].getNumeroPartido() == this->numero_partido){
            if (lista[i].foiEleito()) this->n_eleitos++;
            if (n == 0){
                this->mais_votado = lista[i];
                this->menos_votado = lista[i];
                n++;
            }
            else if (lista[i].getVotosNominais() > this->mais_votado.getVotosNominais() 
                || ( lista[i].getVotosNominais() == this->mais_votado.getVotosNominais() 
                //////////////////////////////////
                ////////////////////////////////
                ///////////// ESSE COMPARE ESTÁ ERRADO //////////////////
                    && lista[i].getDataNasc().compareTo(this->mais_votado.getDataNasc()) == -1)){ 
                this->mais_votado = lista[i];
            }
            else if (lista[i].getVotosNominais() < this->menos_votado.getVotosNominais()
                || ( lista[i].getVotosNominais() == this->menos_votado.getVotosNominais() 
                //////////////////////////////////
                ////////////////////////////////
                ///////////// ESSE COMPARE ESTÁ ERRADO //////////////////
                    && lista[i].getDataNasc().compareTo(this->menos_votado.getDataNasc()) == 1 )){
                this->menos_votado = lista[i];
            }
            this->votos_nominais += lista[i].getVotosNominais();
        }
    }
}

/*Método cria uma string que representa o partido*/
/*Entrada: nada*/
/*Saída: string contendo dados importantes do Partido(string)*/
string Partido::toString() const {
    int totalVotos = this->getVotos() + this->getVotosNominais();
    ostringstream texto;
    texto << this->sigla_partido << " - " << this->numero_partido << ", " << totalVotos;
    if (totalVotos <= 1){
        texto << " voto (";
    } else {
        texto << " votos (";
    }
    texto << this->votos_nominais;
    if(this->getVotosNominais() <= 1){
        texto << " nominal e ";
    } else {
        texto << " nominais e ";
    }
    texto << this->votos_legenda << " de legenda), " << this->n_eleitos;
    if (this->getNEleitos() <= 1){
        texto << " candidato eleito";
    } else {
        texto << " candidatos eleitos";
    } 
    return texto.str();
}

/*Override do operador <<*/
/*Envia o .toString() para o outstream*/
ostream& operator<<(ostream &out, const Partido& partido){
    return out << partido.toString();
}


bool Partido::operator<(const Partido& partido) const{

	if (this -> getVotos() + this -> getVotosNominais() > partido.getVotos() + partido.getVotosNominais()){
		return true;
	}
	else if (this -> getVotos() + this -> getVotosNominais() < partido.getVotos() + partido.getVotosNominais()){
		return false;
	} 

	else if (this -> getNumero() < partido.getNumero()){
		return true;
	}
	else {
		return false;
	}	
}
