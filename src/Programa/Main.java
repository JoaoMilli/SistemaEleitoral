package Programa;
import Objetos.ListaPartidos;
import Objetos.ListaCandidatos;
import java.io.File;


public class Main {

	public static void main(String[] args) {
		
		File path = new File(args[0]);
		
		ListaCandidatos listaCandidato = new ListaCandidatos();
		listaCandidato.getDados(path);	
		
		path = new File(args[1]);
		
		ListaPartidos listaPartido = new ListaPartidos();
		listaPartido.getDados(path);
		listaCandidato.imprimeNumEleitos();	
		listaCandidato.imprimeEleitos(listaPartido);
	}

}

