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
        ImprimeCandidatosPrejudicados(listaCandidatos);
        ImprimeCandidatosBeneficiados(listaCandidatos);
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
    

}

