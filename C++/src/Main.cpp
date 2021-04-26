#include <iostream>
#include <fstream>
#include <string>
#include <list>

#include "Partido.h"
#include "Candidato.h"

using namespace std;

void getDadosPartidos(vector<Partido>& ListaPartidos, string path){

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
        getline(file, sigla_partido);


        Partido* partido = new Partido (atoi(numero_partido.c_str()),nome_partido, sigla_partido, atoi(votos_legenda.c_str()));

        cout << partido -> toString();

        ListaPartidos.push_back(*partido);
    }

    file.close();
}    


Partido getPartidoByNum(vector<Partido> ListaPartidos, int num) {
	int i;
    for(i = 0; i < ListaPartidos.size(); i++) {
		if (ListaPartidos[i].getNumero() == num) {
			return ListaPartidos[i];
		}
	}
    return ListaPartidos[i];
}

void defineNomesPartidos(vector<Candidato>& ListaCandidatos , vector<Partido> ListaPartidos){
    int i;
	for(i = 0; i < ListaCandidatos.size(); i++) {
        //Pega o candidato i e salva o nome do partido ao qual ele pertence
		ListaCandidatos[i].setNomePartido (getPartidoByNum(ListaPartidos, ListaCandidatos[i].getNumeroPartido()).getSigla());
	}
}

void getDadosCandidatos(vector<Candidato>& ListaCandidatos,string path){

    ifstream file (path);

    if (!file.is_open()){
        std::cout << "Erro: Não foi possível abrir o arquivo" << "\n";
        return;
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
            Candidato* candidato  = new Candidato(atoi(numero.c_str()), atoi(votos_nominais.c_str()), situacao, nome, nome_urna, 
            sexo, data_nasc, 0, destino_voto, atoi(numero_partido.c_str()));

            ListaCandidatos.push_back(*candidato);
        }
    }
    file.close();
}

int main(){

    vector<Partido> ListaPartidos;
    vector<Candidato> ListaCandidatos;

    string path = "partidos.csv";
    getDadosPartidos(ListaPartidos, path);
    path = "candidatos.csv";
    getDadosCandidatos(ListaCandidatos, path);
    defineNomesPartidos(ListaCandidatos, ListaPartidos);
}