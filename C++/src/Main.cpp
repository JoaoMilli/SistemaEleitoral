#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
#include <locale>

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
        idade = data_nascimento.anosPassados(dataEleicao);        
        

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


///////////////////////////

void imprimeEleitos (const vector<Candidato>& listaCandidatos) {
	int i, n=1;
	cout << "Vereadores eleitos:" << endl;
	for(i=0; i < (int) listaCandidatos.size(); i++) {
		if (listaCandidatos[i].foiEleito()) {
			cout << n << " - " << listaCandidatos[i] << endl;
            n++;
		}
	}
    cout << endl;
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

bool comparePartido( Partido& a,  Partido& b){
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
    //sort(listaPartidos.begin(), listaPartidos.end(), comparePartido);
    cout << "\nPrimeiro e último colocados de cada partido:" << '\n';
    int i, n = 1;
    for (i=0; i < (int) listaPartidos.size(); i++){
        Partido partido = listaPartidos[i];
        if (partido.getNCandidatos() != 0) {

            Candidato maisVotado = partido.getmaisVotado();
            Candidato menosVotado = partido.getmenosVotado();

            //cout << n << " - " << partido.getSigla() << " - " << partido.getNumero() << ", " 
            //    << maisVotado.getNomeUrna() << " (" << maisVotado.getNumero() << maisVotado.getVotosNominais() << endl;
            //cout << "Menos Votado: " << menosVotado << endl;

            cout << n << " - " << partido.getSigla() << " - " << partido.getNumero() << ", " 
                << maisVotado.getNomeUrna() << " (" << maisVotado.getNumero() << ", " << maisVotado.getVotosNominais(); 
            if (maisVotado.getVotosNominais() > 1){
                cout << " votos) / "; 
            }
            else {
                cout << " voto) / "; 
            }
            
            cout << menosVotado.getNomeUrna() << " (" << menosVotado.getNumero() << ", " << menosVotado.getVotosNominais();
            if(menosVotado.getVotosNominais() > 1){
                cout << " votos)" << endl;
            } else {
                cout << " voto)" << endl;
            }
            n++;
        }
    }
}


/*Função que imprime a distribuição de idade dos candidatos eleitos por faixa etária*/
/*Entradas: lista dos candidatos (LinkedList<Candidato>)*/
/*Saída: Nada*/
void ImprimeDistribuicaoIdade(const vector<Candidato>& ListaCandidatos){
    cout << endl << "Eleitos, por faixa etária (na data da eleição):" << endl;
        int nMenor30=0, n30_40=0, n40_50=0, n50_60=0, nMaiorIgual60=0;
        float pMenor30, p30_40, p40_50, p50_60, pMaiorIgual60;

        int i;
        for (i=0; i < (int) ListaCandidatos.size(); i++){
            Candidato candidato = ListaCandidatos[i];
            if(candidato.foiEleito()){
                //Verifica em qual faixa etária o candidato está incluído e incrementa o contador dela
                if(candidato.getIdade() < 30) nMenor30++;
                else if(candidato.getIdade() < 40) n30_40++; 
                else if(candidato.getIdade() < 50) n40_50++;
                else if(candidato.getIdade() < 60) n50_60++;
                else nMaiorIgual60++;
            }
        }
        //Verificar a quantidade total de candidatos
        int numTotal = nMenor30+n30_40+n40_50+n50_60+nMaiorIgual60;
        if(numTotal == 0){
            //Não tem candidatos
            pMenor30 = 0;
            p30_40 = 0;
            p40_50 = 0;
            p50_60 = 0;
            pMaiorIgual60 = 0;
        } else {
            //Encontrar a proporção de cada faixa etária
            pMenor30 = 100*(float)nMenor30 / numTotal;
            p30_40 = 100*(float)n30_40 / numTotal;
            p40_50 = 100*(float)n40_50 / numTotal;
            p50_60 = 100*(float)n50_60 / numTotal;
            pMaiorIgual60 = 100*(float)nMaiorIgual60 / numTotal;
        }

        //Imprimir o resultado:
        printf("      Idade < 30: %d (%.2f%%)\n", nMenor30, pMenor30);
        printf("30 <= Idade < 40: %d (%.2f%%)\n", n30_40, p30_40);
        printf("40 <= Idade < 50: %d (%.2f%%)\n", n40_50, p40_50);
        printf("50 <= Idade < 60: %d (%.2f%%)\n", n50_60, p50_60);
        printf("60 <= Idade     : %d (%.2f%%)\n", nMaiorIgual60, pMaiorIgual60);
}

/*Função que imprime a distribuição dos candidatos eleitos por sexo*/
/*Entradas: lista dos candidatos (LinkedList<Candidato>)*/
/*Saída: Nada*/
void ImprimeDistribuicaoSexo(const vector<Candidato>& ListaCandidatos){
    cout << endl << "Eleitos, por sexo:" << endl;
    int nMasculino=0, nFeminino=0;
    float pMasculino;

    int i;
    for (i=0; i < (int) ListaCandidatos.size(); i++){
        Candidato candidato = ListaCandidatos[i];
        if(candidato.foiEleito()){
            //Verifica qual o sexo do candidato e incrementa o contador dele
            if(candidato.getSexo().compare("M")) nMasculino++;
            else nFeminino++;
        }
    }
    //Encontrar a proporção de cada sexo
    if(nMasculino+nFeminino > 0){
        pMasculino = (float)nMasculino/(nMasculino+nFeminino);
    } else {
        //Caso não existam candidatos, colocar uma proporção de 50%
        pMasculino = 0.5f;
    }

    //Imprimir o resultado:
    printf("Feminino:  %d (%.2f%%)\n", nFeminino, (1-pMasculino)*100);
    printf("Masculino: %d (%.2f%%)\n", nMasculino, pMasculino*100);
}

/*Função estática que imprime os votos totais válidos, nominais e de legenda e imprime sua distribuição*/
/*Entradas: lista dos partidos (LinkedList<Partido>)*/
/*Saída: Nada*/
void ImprimeVotosTotais(const vector<Partido> ListaPartidos){
    int i, votosTotais = 0, totaisNominais = 0, totaisLegenda = 0;
    float porcentoNominal, porcentoLegenda;
    for (i=0; i < (int)ListaPartidos.size(); i++){
        totaisNominais += ListaPartidos[i].getVotosNominais();
        totaisLegenda += ListaPartidos[i].getVotos();
    }
    votosTotais = totaisNominais + totaisLegenda;
    if(votosTotais == 0){
        porcentoLegenda = 0.0;
        porcentoNominal = 0.0;
    } else {
        porcentoNominal = 100*((float)totaisNominais / (float)votosTotais);
        porcentoLegenda = 100*((float)totaisLegenda / (float)votosTotais);
    }


    printf("\nTotal de votos válidos:    %d\nTotal de votos nominais:   %d (%.2f%%)\nTotal de votos de Legenda: %d (%.2f%%)", 
    votosTotais, totaisNominais, porcentoNominal, totaisLegenda, porcentoLegenda);
}


int main(int argc, char** argv){

    //Define o formato da impressão
    setlocale(LC_ALL, ""); //pt_BR.UTF-8

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
    ImprimeDistribuicaoIdade(ListaCandidatos);
    ImprimeDistribuicaoSexo(ListaCandidatos);
    ImprimeVotosTotais(ListaPartidos);
}