#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>

#include "Partido.h"
#include "Candidato.h"

using namespace std;

void getDadosPartidos(vector<Partido>& ListaPartidos, const string& path){

    ifstream file (path);

    if (!file.is_open()){
        std::cout << "Erro: Não foi possível abrir o arquivo" << "\n";
        return;
    }

    string numero_partido;
    string votos_legenda;
    string nome_partido;
    string sigla_partido;

    getline(file, numero_partido);

    while(getline(file, numero_partido, ',')){

        getline(file, votos_legenda, ',');
        getline(file, nome_partido, ',');
        getline(file, sigla_partido, '\n');


        Partido partido (atoi(numero_partido.c_str()),nome_partido, sigla_partido, atoi(votos_legenda.c_str()));

        ListaPartidos.push_back(partido);
    }

    file.close();
}    


Partido getPartidoByNum(const vector<Partido>& ListaPartidos, const int& num) {
	int i;
    for(i = 0; i < ListaPartidos.size(); i++) {
		if (ListaPartidos[i].getNumero() == num) {
			return ListaPartidos[i];
		}
	}
    return ListaPartidos[i];
}

void defineNomesPartidos(vector<Candidato>& ListaCandidatos, const vector<Partido>& ListaPartidos){
    int i;
	for(i = 0; i < ListaCandidatos.size(); i++) {
        //Pega o candidato i e salva o nome do partido ao qual ele pertence
		ListaCandidatos[i].setNomePartido (getPartidoByNum(ListaPartidos, ListaCandidatos[i].getNumeroPartido()).getSigla());
	}
}

int getDadosCandidatos(vector<Candidato>& ListaCandidatos, const string& path){

    ifstream file (path);
    int nvagas = 0;

    if (!file.is_open()){
        std::cout << "Erro: Não foi possível abrir o arquivo" << "\n";
        return 0;
    }

    string numero;
    string votos_nominais;
    string situacao;
    string nome;
    string nome_urna;
    string sexo;
    string data_nasc;
    string destino_voto;
    string numero_partido;

    getline(file, numero);

    while(getline(file, numero, ',')){

        getline(file, votos_nominais, ',');
        getline(file, situacao, ',');
        getline(file, nome, ',');
        getline(file, nome_urna, ',');
        getline(file, sexo, ',');
        getline(file, data_nasc, ',');
        getline(file, destino_voto, ',');
        getline(file, numero_partido);


        if(!destino_voto.compare("Válido")){
            Candidato candidato (atoi(numero.c_str()), atoi(votos_nominais.c_str()), situacao, nome, nome_urna, 
            sexo, data_nasc, 0, destino_voto, atoi(numero_partido.c_str()));

            ListaCandidatos.push_back(candidato);
            
            if (candidato.foiEleito()) nvagas++;
        }
    }
    file.close();
    return nvagas;
}


void imprimeEleitos (const vector<Candidato>& listaCandidatos) {
	int i, n=1;
	cout << "Vereadores eleitos:" << "\n";
	for(i=0; i < listaCandidatos.size(); i++) {
		if (listaCandidatos[i].foiEleito()) {
			cout << n << " - " << listaCandidatos[i].toString() << '\n';
            n++;
		}
	}
    cout << "\n";
}

bool comparaCandidatos(const Candidato& candidato1, const Candidato& candidato2){
    return candidato1.getVotosNominais() > candidato2.getVotosNominais();
}

void ImprimeCandidatosMaisVotados( const vector<Candidato>& listaCandidatos, const int& nvagas) {
    cout << "Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):" << '\n';
        
    int i;
    for (i=0; i<nvagas; i++){
        cout << (i+1) << " - " << listaCandidatos[i].toString() << '\n';
    }
}

int main(){

    vector<Partido> ListaPartidos;
    vector<Candidato> ListaCandidatos;
    int nvagas = 0;

    string path = "partidos.csv";
    getDadosPartidos(ListaPartidos, path);
    path = "candidatos.csv";
    nvagas = getDadosCandidatos(ListaCandidatos, path);
    defineNomesPartidos(ListaCandidatos, ListaPartidos);
    sort(ListaCandidatos.begin(), ListaCandidatos.end(), comparaCandidatos);

    cout << "Número de vagas: " << nvagas << "\n\n";
    imprimeEleitos (ListaCandidatos);
    ImprimeCandidatosMaisVotados(ListaCandidatos, nvagas);
}