package Programa;
import Objetos.ListaPartidos;
import Objetos.Candidato;
import Objetos.ListaCandidatos;
import java.io.File;


public class Main {

	public static void main(String[] args) {
        //Leitura dos Candidatos
		File path = new File(args[0]);
		
		ListaCandidatos listaCandidatos = new ListaCandidatos();
		listaCandidatos.getDados(path);	
		
        //Leitura dos partidos
		path = new File(args[1]);
		
		ListaPartidos listaPartidos = new ListaPartidos();
		listaPartidos.getDados(path);

        //Varrer lista de candidatos e definir o nome do partido de cada
        listaCandidatos.defineNomesPartidos(listaPartidos);


		listaCandidatos.imprimeNumEleitos();	
		listaCandidatos.imprimeEleitos();
        ImprimeCandidatosMaisVotados(listaCandidatos);
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

}

