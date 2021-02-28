package Programa;
import Objetos.Partido;
import Objetos.Candidato;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {

	public static void main(String[] args) {
		
		File path = new File(args[0]);
		
		List<Candidato> listaCandidato = new ArrayList<Candidato>();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
			
			String linha = br.readLine();
			linha = br.readLine();
			while (linha != null) {
				
				String[] vect = linha.split(",");
				Integer num = Integer.parseInt(vect[0]);
				Integer votos = Integer.parseInt(vect[1]);
				String situacao = vect[2];
				String nome = vect[3];
				String nomeUrna = vect[4];
				String sexo = vect[5];
				String dataNasc = vect[6];
				String destino = vect[7];
				Integer numPart = Integer.parseInt(vect[8]);
				
				
				Candidato candidato = new Candidato(num, votos, situacao, nome, nomeUrna, sexo, dataNasc, destino, numPart);
				listaCandidato.add(candidato);
				
				linha = br.readLine();
			}	
			
		}
		catch (IOException erro) {
			System.out.println("Erro: " + erro.getMessage());
		}
		
		path = new File(args[1]);
		
		List<Partido> listaPartido = new ArrayList<Partido>();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
			
			String linha = br.readLine();
			linha = br.readLine();
			while (linha != null) {
				
				String[] vect = linha.split(",");
				Integer num = Integer.parseInt(vect[0]);
				Integer votos = Integer.parseInt(vect[1]);
				String nome = vect[2];
				String sigla = vect[3];
				
				Partido partido = new Partido(num, nome, sigla, votos);
				listaPartido.add(partido);
				
				linha = br.readLine();
			}	
			
		}
		catch (IOException erro) {
			System.out.println("Erro: " + erro.getMessage());
		}
	}

}

