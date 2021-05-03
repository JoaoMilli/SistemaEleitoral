#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>

#include "Data.h"
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
    for(i = 0; i < (int) ListaPartidos.size(); i++) {
		if (ListaPartidos[i].getNumero() == num) {
			return ListaPartidos[i];
		}
	}
    return ListaPartidos[i];
}

void defineNomesPartidos(vector<Candidato>& ListaCandidatos, const vector<Partido>& ListaPartidos){
    int i;
	for(i = 0; i < (int) ListaCandidatos.size(); i++) {
        //Pega o candidato i e salva o nome do partido ao qual ele pertence
		ListaCandidatos[i].setNomePartido (getPartidoByNum(ListaPartidos, ListaCandidatos[i].getNumeroPartido()).getSigla());
	}
}

int getDadosCandidatos(vector<Candidato>& ListaCandidatos, const string& path, const Data& dataEleicao){

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
    string s_data_nasc;
    Data data_nascimento;
    int idade;
    string destino_voto;
    string numero_partido;

    getline(file, numero);

    while(getline(file, numero, ',')){

        getline(file, votos_nominais, ',');
        getline(file, situacao, ',');
        getline(file, nome, ',');
        getline(file, nome_urna, ',');
        getline(file, sexo, ',');
        getline(file, s_data_nasc, ',');
        getline(file, destino_voto, ',');
        getline(file, numero_partido);

        //Trata a data de nascimento para definir a idade
        if(Data::validDate(s_data_nasc)){
            data_nascimento = Data(s_data_nasc);
        } else {
            cout << "Erro ao manipular a data de nascimento do candidato: " << nome << endl;
        }
        //encontra a quantos anos de diferença entre as datas (idade)
        idade = dataEleicao.anosPassados(data_nascimento);        
        

        if(!destino_voto.compare("Válido")){
            Candidato candidato (atoi(numero.c_str()), atoi(votos_nominais.c_str()), situacao, nome, nome_urna, 
            sexo, data_nascimento, idade, destino_voto, atoi(numero_partido.c_str()));

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
	for(i=0; i < (int) listaCandidatos.size(); i++) {
		if (listaCandidatos[i].foiEleito()) {
			cout << n << " - " << listaCandidatos[i].toString() << '\n';
            n++;
		}
	}
    cout << "\n";
}


void ImprimeCandidatosMaisVotados( const vector<Candidato>& listaCandidatos, const int& nvagas) {
    cout << "Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):" << '\n';
        
    int i;
    for (i=0; i<nvagas; i++){
        cout << (i+1) << " - " << listaCandidatos[i] << '\n';
    }
}

void ImprimeCandidatosPrejudicados(const vector<Candidato>& listaCandidatos, int nvagas) {
    cout << "\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)" << '\n';
        
    int i;
    for (i=0; i<nvagas; i++){
        Candidato candidato = listaCandidatos[i];
        if(!candidato.foiEleito()) 
            cout << i+1 << " - " << candidato << '\n';
    }
}

void ImprimeCandidatosBeneficiados(const vector<Candidato>& listaCandidatos, int nvagas) {
    cout << "\nEleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)" << '\n';
        
    int i;
    for (i=nvagas; i < (int) listaCandidatos.size(); i++){
        Candidato candidato = listaCandidatos[i];
        if(candidato.foiEleito()) 
            cout << i+1 << " - " << candidato << '\n';
    }
}

void setPartidosEleitos(vector<Partido>& listaPartidos, const vector<Candidato>& lista){
	int i;
	for(i=0; i < (int) listaPartidos.size(); i++){			
		listaPartidos[i].setEleitos(lista);
	}
}

void ImprimePartidos(vector<Partido>& listaPartidos){
    sort(listaPartidos.begin(), listaPartidos.end());
    cout << "\nVotação dos partidos e número de candidatos eleitos:" << '\n';
    int i, n = 1;
    for (i=0; i < (int) listaPartidos.size(); i++){
        cout << n << " - " << listaPartidos[i] << '\n';
        n++;
    }
}


bool comparePartido(Partido a, Partido b){
    //Colocar os partidos sem candidatos válidos em posições menores
    if(a.getNCandidatos() == 0 && b.getNCandidatos() == 0) return false;
    if(a.getNCandidatos() == 0) return true;
    if(b.getNCandidatos() == 0) return false;

    //Compara qual partido tem um candidato com mais votos
    if(a.getmaisVotado().getVotosNominais() > b.getmaisVotado().getVotosNominais()) return true;
    if(a.getmaisVotado().getVotosNominais() < b.getmaisVotado().getVotosNominais()) return false;
    //Se a quantidade de votos nominais no mais votado forem iguais nos dois partidos verifica o com menor número partidário
    if(a.getNumero() < b.getNumero()) return true;
    else return false;
}

void ImprimePrimeiroeUltimo(vector<Partido>& listaPartidos){
    sort(listaPartidos.begin(), listaPartidos.end(), comparePartido);
    cout << "\nPrimeiro e último colocados de cada partido:" << '\n';
    int i, n = 1;
    for (i=0; i < (int) listaPartidos.size(); i++){
        Partido partido = listaPartidos[i];
        if (partido.getNCandidatos() != 0) {

            Candidato maisVotado = partido.getmaisVotado();
            Candidato menosVotado = partido.getmenosVotado();

            if (maisVotado.getVotosNominais() <= 1 && menosVotado.getVotosNominais() <= 1){
                cout << n << " - " << partido.getSigla() << " - " << partido.getNumero() << ", " <<  maisVotado.getNomeUrna() << " (" << maisVotado.getNumero() + ", " << maisVotado.getVotosNominais() << " voto) / " << menosVotado.getNomeUrna() << " (" + menosVotado.getNumero() << ", " << menosVotado.getVotosNominais() << " voto)" << '\n';
                n++;
            }
            else if (maisVotado.getVotosNominais() <= 1){
                cout << n << " - " << partido.getSigla() << " - " << partido.getNumero() << ", " <<  maisVotado.getNomeUrna() << " (" << maisVotado.getNumero() + ", " << maisVotado.getVotosNominais() << " voto) / " << menosVotado.getNomeUrna() << " (" + menosVotado.getNumero() << ", " << menosVotado.getVotosNominais() << " voto)" << '\n';                    
                n++;
            }
            else if (menosVotado.getVotosNominais() <= 1){
                cout << n << " - " << partido.getSigla() << " - " << partido.getNumero() << ", " <<  maisVotado.getNomeUrna() << " (" << maisVotado.getNumero() + ", " << maisVotado.getVotosNominais() << " voto) / " << menosVotado.getNomeUrna() << " (" + menosVotado.getNumero() << ", " << menosVotado.getVotosNominais() << " voto)" << '\n';
                n++;
            }
            else {
                cout << n << " - " << partido.getSigla() << " - " << partido.getNumero() << ", " <<  maisVotado.getNomeUrna() << " (" << maisVotado.getNumero() + ", " << maisVotado.getVotosNominais() << " voto) / " << menosVotado.getNomeUrna() << " (" + menosVotado.getNumero() << ", " << menosVotado.getVotosNominais() << " voto)" << '\n';
                n++;
            }
        }
    }
}

int main(int argc, char** argv){

    //Verifica se os parâmetros de entrada foram inseridos corretamente
    if(argc != 4){
        cout << "Erro! É necessário passar 3 argumentos: arquivocandidatos.csv arquivopartidos.csv dataEleição (em formato dd/mm/aaaa)" << endl;
        return 1;
    }

    vector<Partido> ListaPartidos;
    vector<Candidato> ListaCandidatos;
    int nvagas = 0;

    //Cria objeto com a data da eleição:
    if(Data::validDate(argv[3])){
        Data dataEleicao(argv[3]);
        nvagas = getDadosCandidatos(ListaCandidatos, argv[1], dataEleicao);
    } else {
        cout << "Erro! Data da eleição passada em formato errado. O formato esperado é dd/mm/aaaa" << endl;
        return 2;
    }

    getDadosPartidos(ListaPartidos, argv[2]);    
    
    defineNomesPartidos(ListaCandidatos, ListaPartidos);
    setPartidosEleitos(ListaPartidos, ListaCandidatos);

    sort(ListaCandidatos.begin(), ListaCandidatos.end());

    cout << "Número de vagas: " << nvagas << "\n\n";
    imprimeEleitos (ListaCandidatos);
    ImprimeCandidatosMaisVotados(ListaCandidatos, nvagas);
    ImprimeCandidatosPrejudicados(ListaCandidatos, nvagas);
    ImprimeCandidatosBeneficiados(ListaCandidatos, nvagas);
    ImprimePartidos(ListaPartidos);
    ImprimePrimeiroeUltimo(ListaPartidos);
}