package Objetos;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ListaCandidatos extends LinkedList<Candidato> {
	
	private static final long serialVersionUID = 1L;

	public void getDados(File path) {
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
				this.add(candidato);
				
				linha = br.readLine();
			}	
			
		}
		catch (IOException erro) {
			System.out.println("Erro: " + erro.getMessage());
		}
		
	}
	
	public void imprimeNumEleitos() {
		int i, n = 0;
		for(i=0; i < this.size(); i++) {
			if (this.get(i).foiEleito()) {
				n++;
			}
		}
		System.out.println("Número de vagas: " + n);
	}
	
	public void imprimeEleitos(ListaPartidos listapartidos) {
		int i, n=1;
		System.out.println("Vereadores eleitos:");
		for(i=0; i < this.size(); i++) {
			Candidato candidato = this.get(i);
			if (candidato.foiEleito()) {
				String partido = listapartidos.getPartidoByNum(candidato.getNumeroPartido()).getNome();
				System.out.print(n + " - ");
				candidato.imprimeCandidato(partido);
			}
		}
	}
}
