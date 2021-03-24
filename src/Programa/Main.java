package Programa;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Formatter;
import java.util.Locale;

import Objetos.Candidato;
import Objetos.Comparador;
import Objetos.ListaCandidatos;
import Objetos.ListaPartidos;
import Objetos.Partido;


public class Main {

    private static Locale locale;
	public static void main(String[] args) {    
        //Definindo o locale para os printf's
        locale = new Locale("pt", "BR");

        //Verifica se todos os argumentos foram passados
        if(args.length < 3){
            System.out.println("Erro! É necessário passar 3 argumentos: arquivocandidatos.csv arquivopartidos.csv dataEleição (em formato dd/mm/aaaa)");
            return;
        }

        //Leitura dos Candidatos
		File path = new File(args[0]);
		
		ListaCandidatos listaCandidatos = new ListaCandidatos();
		listaCandidatos.getDados(path, args[2]);	
		
        //Leitura dos partidos
		path = new File(args[1]);
		
		ListaPartidos listaPartidos = new ListaPartidos();
		listaPartidos.getDados(path);

        //Varrer lista de candidatos e definir o nome do partido de cada
        listaCandidatos.defineNomesPartidos(listaPartidos);
        listaPartidos.setPartidosEleitos(listaCandidatos);

		int n = ListaCandidatos.getVagas();
        System.out.println("Número de vagas: " + n);
        System.out.println();
		imprimeEleitos(listaCandidatos);
        ImprimeCandidatosMaisVotados(listaCandidatos);
        ImprimeCandidatosPrejudicados(listaCandidatos);
        ImprimeCandidatosBeneficiados(listaCandidatos);
        ImprimePartidos(listaPartidos);
        ImprimePrimeiroeUltimo(listaPartidos);
        ImprimeDistribuicaoIdade(listaCandidatos);
        ImprimeVotosTotais(listaPartidos);
	}
   

    

    private static void ImprimeCandidatosMaisVotados(ListaCandidatos listaCandidatos) {
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        //Ordena a lista de candidatos com os mais votados primeiro
        listaCandidatos.sort(null);
        
        int i;
        for (i=0; i<ListaCandidatos.getVagas(); i++){
            Candidato candidato = listaCandidatos.get(i);
            System.out.println((i+1) + " - " + candidato);
        }
        System.out.println();
    }

    private static void ImprimeCandidatosPrejudicados(ListaCandidatos listaCandidatos) {
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)");

        //A lista já está ordenada
        
        int i;
        for (i=0; i<ListaCandidatos.getVagas(); i++){
            Candidato candidato = listaCandidatos.get(i);
            if(!candidato.foiEleito()) 
                System.out.println((i+1) + " - " + candidato);
        }
        System.out.println();
    }

    private static void ImprimeCandidatosBeneficiados(ListaCandidatos listaCandidatos) {
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)");
        
        //A lista já está ordenada
        
        int i;
        for (i=ListaCandidatos.getVagas(); i < listaCandidatos.size(); i++){
            Candidato candidato = listaCandidatos.get(i);
            if(candidato.foiEleito()) 
                System.out.println((i+1) + " - " + candidato);
        }
        System.out.println();
    }


    private static void ImprimePartidos(ListaPartidos listaPartidos){
        listaPartidos.sort(null);
        System.out.println("Votação dos partidos e número de candidatos eleitos:");
        int i, n = 1;
        for (i=0; i < listaPartidos.size(); i++){
            System.out.println(n + " - " + listaPartidos.get(i).toString());
            n++;
        }
        System.out.println();

    }

    private static void ImprimePrimeiroeUltimo(ListaPartidos listaPartidos){
        Collections.sort(listaPartidos, new Comparador());
        System.out.println("Primeiro e último colocados de cada partido:");
        int i, n = 1;
        for (i=0; i < listaPartidos.size(); i++){
            Partido partido = listaPartidos.get(i);
            Candidato maisVotado = partido.getmaisVotado();
            Candidato menosVotado = partido.getmenosVotado();
            if (maisVotado != null && menosVotado != null) {
                if (maisVotado.getVotosNominais() == 1 && menosVotado.getVotosNominais() == 1){
                    System.out.println(n + " - " + partido.getNome() + " - " + partido.getNumero() + ", " +  maisVotado.getNomeUrna() + " (" + maisVotado.getNumero() + ", " + maisVotado.getVotosNominais() + " voto) / " + menosVotado.getNomeUrna() + " (" + menosVotado.getNumero() + ", " + menosVotado.getVotosNominais() + " voto)");
                    n++;
                }
                else if (maisVotado.getVotosNominais() == 1){
                    System.out.println(n + " - " + partido.getNome() + " - " + partido.getNumero() + ", " +  maisVotado.getNomeUrna() + " (" + maisVotado.getNumero() + ", " + maisVotado.getVotosNominais() + " voto) / " + menosVotado.getNomeUrna() + " (" + menosVotado.getNumero() + ", " + menosVotado.getVotosNominais() + " votos)");
                    n++;
                }
                else if (menosVotado.getVotosNominais() == 1){
                    System.out.println(n + " - " + partido.getNome() + " - " + partido.getNumero() + ", " +  maisVotado.getNomeUrna() + " (" + maisVotado.getNumero() + ", " + maisVotado.getVotosNominais() + " votos) / " + menosVotado.getNomeUrna() + " (" + menosVotado.getNumero() + ", " + menosVotado.getVotosNominais() + " voto)");
                    n++;
                }
                else {
                    System.out.println(n + " - " + partido.getNome() + " - " + partido.getNumero() + ", " +  maisVotado.getNomeUrna() + " (" + maisVotado.getNumero() + ", " + maisVotado.getVotosNominais() + " votos) / " + menosVotado.getNomeUrna() + " (" + menosVotado.getNumero() + ", " + menosVotado.getVotosNominais() + " votos)");
                    n++;
                }
            }
    
        }
        System.out.println();
    }

    private static void ImprimeDistribuicaoIdade(ListaCandidatos listaCandidatos) {
        System.out.println("Eleitos, por faixa etária (na data da eleição):");
        int nMenor30=0, n30_40=0, n40_50=0, n50_60=0, nMaiorIgual60=0;
        float pMenor30, p30_40, p40_50, p50_60, pMaiorIgual60;

        int i;
        for (i=0; i < listaCandidatos.size(); i++){
            Candidato candidato = listaCandidatos.get(i);
            if(candidato.foiEleito()){
                //Verifica em qual faixa etária o candidato está incluído e incrementa o contador dela
                if(candidato.getIdade() < 30) nMenor30++;
                else if(candidato.getIdade() < 40) n30_40++;
                else if(candidato.getIdade() < 50) n40_50++;
                else if(candidato.getIdade() < 60) n50_60++;
                else nMaiorIgual60++;            
            }
        }
        //Encontrar a proporção de cada faixa etária
        pMenor30 = 100*(float)nMenor30 / (nMenor30+n30_40+n40_50+n50_60+nMaiorIgual60);
        p30_40 = 100*(float)n30_40 / (nMenor30+n30_40+n40_50+n50_60+nMaiorIgual60);
        p40_50 = 100*(float)n40_50 / (nMenor30+n30_40+n40_50+n50_60+nMaiorIgual60);
        p50_60 = 100*(float)n50_60 / (nMenor30+n30_40+n40_50+n50_60+nMaiorIgual60);
        pMaiorIgual60 = 100*(float)nMaiorIgual60 / (nMenor30+n30_40+n40_50+n50_60+nMaiorIgual60);

        //Imprimir o resultado:
        System.out.printf(locale, "      Idade < 30: %d (%.2f%%)\n", nMenor30, pMenor30);
        System.out.printf(locale, "30 <= Idade < 40: %d (%.2f%%)\n", n30_40, p30_40);
        System.out.printf(locale, "40 <= Idade < 50: %d (%.2f%%)\n", n40_50, p40_50);
        System.out.printf(locale, "50 <= Idade < 60: %d (%.2f%%)\n", n50_60, p50_60);
        System.out.printf(locale, "60 <= Idade     : %d (%.2f%%)\n", nMaiorIgual60, pMaiorIgual60);
        

        System.out.println();

    }
    
    private static void ImprimeVotosTotais(ListaPartidos listaPartidos){
        int i, votosTotais = 0, totaisNominais = 0, totaisLegenda = 0;
        float porcentoNominal, porcentoLegenda;
        for (i=0; i < listaPartidos.size(); i++){
            totaisNominais += listaPartidos.get(i).getVotosNominais();
            totaisLegenda += listaPartidos.get(i).getVotos();
        }
        votosTotais = totaisNominais + totaisLegenda;

        porcentoNominal = 100*((float)totaisNominais / (float)votosTotais);
        porcentoLegenda = 100*((float)totaisLegenda / (float)votosTotais);

        Formatter fmt = new Formatter();
        fmt.format(locale, "Total de votos válidos: %d\nTotal de votos nominais: %d (%.2f%%)\nTotal de votos de Legenda: %d (%.2f%%)", votosTotais, totaisNominais, porcentoNominal, totaisLegenda, porcentoLegenda);
        String string = fmt.out().toString();
        fmt.close();

        System.out.println(string);

    }

    private static void imprimeEleitos( ListaCandidatos listaCandidatos) {
		int i, n=1;
		System.out.println("Vereadores eleitos:");
		for(i=0; i < listaCandidatos.size(); i++) {
			Candidato candidato = listaCandidatos.get(i);
			if (candidato.foiEleito()) {
				System.out.println(n + " - " + candidato);
                n++;
			}
		}
        System.out.println();
	}
}

