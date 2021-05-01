#include "Candidato.h"

Candidato::Candidato(const int& num, const int& votosNominais, const string& situacao, const string& nome, const string& nomeUrna, 
        const string& sexo, const Data& dataNasc, const int& idade, const string& destinoVoto, const int& numeroPartido){
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
    this->dataNasc = Data();
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

/*Override do operador <<*/
/*Envia o .toString() para o outstream*/
ostream& operator<<(ostream &out, const Candidato& candidato){
    return out << candidato.toString();
}

/*Override do operador < */
/*Define quando um candidato é "menor" que o outro*/
/*Compara dois Objetos candidatos de acordo com o número de votos nominais 
e por data de nascimento em caso de empate*/
/*Entrada: O Objeto candidato a ser comparado(Candidato)*/
/*Saída: true caso o objeto comparado tenha menor número de votos nominais e false caso contrário, 
em caso de empate retorna false caso o objeto comparado tenha menor idade e true caso contrário, 
retorna false se tiverem a mesma idade e mesmo numero de votos nominais*/
bool Candidato::operator<(const Candidato& candidato) const{
    if(this->getVotosNominais() > candidato.getVotosNominais()){
        //Se tiver mais votos, retorna que é menor, para ser inserido mais para o ínicio da lista
        return true;
    }
    if(this->getVotosNominais() < candidato.getVotosNominais()){
        //Se tiver menos votos, retorna que é maior, para ser inserido mais para o final da lista
        return false;
    }
    //Se for igual, verifica a idade, sendo que o mais velho vem antes
    if(this->getDataNasc().compareTo(candidato.getDataNasc()) == 1){ //this é mais novo que candidato
        return false; //Colocado mais para o fim da lista
    }
    if(this->getDataNasc().compareTo(candidato.getDataNasc()) == -1){ //this é mais velho que candidato
        return true; //Colocado com maior prioridade na frente da lista
    }

    //Se têm a mesma quantidade de votos e nasceram no mesmo dia...
    return false;
}
