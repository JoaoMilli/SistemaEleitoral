package Programa;
import Objetos.ListaPartidos;
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
		listaCandidatos.imprimeEleitos(listaPartidos);
	}

}

