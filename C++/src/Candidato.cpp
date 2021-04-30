#include "Candidato.h"

Candidato::Candidato(const int& num, const int& votosNominais, const string& situacao, const string& nome, const string& nomeUrna, 
        const string& sexo, const string& dataNasc, const int& idade, const string& destinoVoto, const int& numeroPartido){
    this->numero = num;
    this->votosNominais = votosNominais;
    this->situacao = situacao;
    this->nome = nome;
    this->nomeUrna = nomeUrna;
    this->sexo = sexo;
    this->dataNasc = dataNasc;
    this->idade = idade;
    this->destinoVoto = destinoVoto;
    this->numeroPartido = numeroPartido;    
}

Candidato::Candidato(/* Construtor padrão */){
    this->numero = 0;
    this->votosNominais = 0;
    this->situacao = "";
    this->nome = "";
    this->nomeUrna = "";
    this->sexo = "";
    this->dataNasc = "";
    this->idade = 0;
    this->destinoVoto = "";
    this->numeroPartido = 0; 
}

/*Método que define o nome do partido do candidato*/
/*Entrada: nome do partido do candidato(string)*/
/*Saida: atributo nomePartido do objeto candidato definido como o conteúdo da string de entrada*/
void Candidato::setNomePartido(const string& nomePartido){
    this->nomePartido = nomePartido;
}

/*Método que verifica se o candidato foi eleito*/
/*Entrada: nada*/
/*Saída: true se foi eleito, false caso contrário(bool)*/
bool Candidato::foiEleito() const{
    return (this->getSituacao().compare("Eleito") == 0);
}


/*Método que retorna uma string representando o objeto*/
/*Entrada: nada*/
/*Saída: string contendo dados importantes do candidato(string)*/
string Candidato::toString() const{
    ostringstream texto;
    texto << this->getNome() << " / " << this->getNomeUrna() << " (" + this->getNomePartido() << ", " << this->getVotosNominais();
    if (this->getVotosNominais() <= 1){
        texto << " voto)";
    } else {
        texto << " votos)";
    }
    return texto.str();
}