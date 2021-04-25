#include <iostream>
#include <fstream>
#include <string>

#include "Partido.h"
#include "Candidato.h"

using namespace std;

void leCSV(string path){

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

        cout << " " << numero << " " << votos_nominais << " " << situacao << " " << nome << " " << nome_urna << " " << sexo << " " << data_nasc << " " << destino_voto << " " << numero_partido << " " << "\n";
    }

    file.close();
}

int main(){

    string path = "candidatos.csv";
    leCSV(path);

}