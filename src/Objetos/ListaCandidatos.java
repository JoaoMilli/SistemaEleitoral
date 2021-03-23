package Objetos;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ListaCandidatos extends LinkedList<Candidato> {
	
	private static final long serialVersionUID = 1L;

    private static int vagas = 0;

	public void getDados(File path, String dataEleicao) {
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
				Data dataNasc = new Data(vect[6]);
                int idade = dataNasc.anosPassados(new Data(dataEleicao));
				String destino = vect[7];
				Integer numPart = Integer.parseInt(vect[8]);
				
				if(destino.equals("Válido")) {
					Candidato candidato = new Candidato(num, votos, situacao, nome, nomeUrna, sexo, dataNasc, idade, destino, numPart);
					this.add(candidato);

					//Incrementa o número de vagas contando os candidatos eleitos
					if(candidato.foiEleito()){
						setVagas(getVagas() + 1);
					}
				}

				linha = br.readLine();
			}	
			br.close();
		}
		catch (IOException erro) {
			System.out.println("Erro: " + erro.getMessage());
		}
		
	}

    public static int getVagas() {
        return vagas;
    }

    public static void setVagas(int vagas) {
        ListaCandidatos.vagas = vagas;
    }

    public void defineNomesPartidos(ListaPartidos listaPartidos){
        int i;
		for(i=0; i < this.size(); i++) {
            //Pega o candidato i e salva o nome do partido ao qual ele pertence
			this.get(i).setNomePartido(listaPartidos.getPartidoByNum(this.get(i).getNumeroPartido()).getNome());
		}
    }
}