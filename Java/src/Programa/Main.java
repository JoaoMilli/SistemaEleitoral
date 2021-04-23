package Programa;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;

import Objetos.Candidato;
import Objetos.Comparador;
import Objetos.Data;
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

        LinkedList<Candidato> listaCandidatos = new LinkedList<Candidato>();
		
        int nvagas = getDadosCandidatos(listaCandidatos, path, args[2]);
		
        //Leitura dos partidos
		path = new File(args[1]);
		
		LinkedList<Partido> listaPartidos = new LinkedList<Partido>();
        getDadosPartidos(path, listaPartidos);

        //Varrer lista de candidatos e definir o nome do partido de cada
        defineNomesPartidos(listaCandidatos , listaPartidos);
        setPartidosEleitos(listaPartidos, listaCandidatos);

        System.out.println("Número de vagas: " + nvagas);
        System.out.println();

        //Ordena a lista de candidatos com os mais votados primeiro
        listaCandidatos.sort(null);

		imprimeEleitos(listaCandidatos);
        ImprimeCandidatosMaisVotados(listaCandidatos, nvagas);
        ImprimeCandidatosPrejudicados(listaCandidatos, nvagas);
        ImprimeCandidatosBeneficiados(listaCandidatos, nvagas);
        ImprimePartidos(listaPartidos);
        ImprimePrimeiroeUltimo(listaPartidos);
        ImprimeDistribuicaoIdade(listaCandidatos);
        ImprimeDistribuicaoSexo(listaCandidatos);
        ImprimeVotosTotais(listaPartidos);
	}
   
    /*Função estática que Obtém dados dos candidatos, instancia e os insere na lista de candidatos*/
	/*Entradas: lista dos candidatos (LinkedList<Candidato>), caminho do arquivo CSV contendo os dados dos Candidatos(Path) e data da eleição (String)*/
	/*Saída: Número de vagas totais(int) e lista de candidatos contendo todos os candidatos válidos instanciados*/

    public static int getDadosCandidatos(LinkedList<Candidato> listaCandidatos, File path, String dataEleicao) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
			
            int nvagas = 0;

			String linha = br.readLine();
			linha = br.readLine();
			while (linha != null) {
				
                try{
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
                        listaCandidatos.add(candidato);
                        
                        //Incrementa o número de vagas contando os candidatos eleitos
                        if(candidato.foiEleito()){
                            nvagas++;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Erro: Formato da data da eleição não reconhecido, favor usar dd/mm/aaaa");
                    System.exit(4);
                }
                    
				linha = br.readLine();
			}	
			br.close();
            return nvagas;
		}
		catch (IOException erro) {
			System.out.println("Erro: " + erro.getMessage());
            System.exit(1);
            return 0;
		}
		
	}

    /*Função estática que define o nome do partido de cada candidato através do número do partido*/
	/*Entradas: lista de candidatos (LinkedList<Candidato>), lista de partidos (LinkedList<Partido>)*/
	/*Saída: Candidatos com o nome do própio partido definidos*/

    public static void defineNomesPartidos(LinkedList<Candidato> listaCandidatos , LinkedList<Partido> listaPartidos){
        int i;
		for(i=0; i < listaCandidatos.size(); i++) {
            //Pega o candidato i e salva o nome do partido ao qual ele pertence
			listaCandidatos.get(i).setNomePartido(getPartidoByNum(listaPartidos, listaCandidatos.get(i).getNumeroPartido()).getSigla());
		}
    }


    /*Função estática que Obtém dados dos Partidos, instancia e os insere na lista de partidos*/
	/*Entradas: lista dos partidos (LinkedList<Partido>) e caminho do arquivo CSV contendo os dados dos Partidos(Path) */
	/*Saída: lista de partidos contendo todos partidos do arquivo instanciados*/

    public static void getDadosPartidos(File path, LinkedList<Partido> listaPartidos) {
		
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
				listaPartidos.add(partido);
				
				linha = br.readLine();
			}	

            br.close();
			
		}
		catch (IOException erro) {
			System.out.println("Erro: " + erro.getMessage());
            System.exit(2);
		}
	}

    
    /*Função estática que retorna o partido referente ao seu número*/
	/*Entradas: lista dos partidos (LinkedList<Partido>) e o número do partido desejado(int)*/
	/*Saída: Objeto da classe Partido referente ao número inserido*/

	public static Partido getPartidoByNum(LinkedList<Partido> listaPartidos, int num) {
		int i;
		for(i=0; i < listaPartidos.size(); i++) {
			if (Objects.equals(listaPartidos.get(i).getNumero(), num)) {
				return listaPartidos.get(i);
			}
		}
		return null;
	}


    /*Função estática que adiciona o mais e menos votado de cada partido, assim como o número de votos nominais e o total de candidatos eleitos*/
	/*Entradas: lista dos partidos (LinkedList<Partido>) e lista dos candidatos (LinkedList<Candidato>)*/
	/*Saída: Objetos Partido da lista de partidos com os atributos adicionados*/

    public static void setPartidosEleitos(LinkedList<Partido> listaPartidos, LinkedList<Candidato> lista){
		int i;
		for(i=0; i < listaPartidos.size(); i++){			
			listaPartidos.get(i).setEleitos(lista);
		}
	}

    /*Função estática que imprime todos os candidatos que foram eleitos*/
	/*Entradas: lista de candidatos(LinkedList<Partido>)*/
	/*Saída: Nada*/

    private static void imprimeEleitos(LinkedList<Candidato> listaCandidatos) {
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

    /*Função estática que imprime os candidatos mais votados em ordem decrescente de votos*/
	/*Entradas: lista dos candidatos (LinkedList<Partido>) e número total de vagas (int)*/
	/*Saída: Nada*/

    private static void ImprimeCandidatosMaisVotados( LinkedList<Candidato> listaCandidatos, int nvagas) {
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        
        int i;
        for (i=0; i<nvagas; i++){
            Candidato candidato = listaCandidatos.get(i);
            System.out.println((i+1) + " - " + candidato);
        }
        System.out.println();
    }

    
    /*Função estática que imprime os candidatos não eleitos que teriam sido eleitos se a votação fosse majoritária*/
	/*Entradas: lista dos candidatos (LinkedList<Candidato>) e número total de vagas (int)*/
	/*Saída: Nada*/

    private static void ImprimeCandidatosPrejudicados(LinkedList<Candidato> listaCandidatos, int nvagas) {
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)");

        //A lista já está ordenada
        
        int i;
        for (i=0; i<nvagas; i++){
            Candidato candidato = listaCandidatos.get(i);
            if(!candidato.foiEleito()) 
                System.out.println((i+1) + " - " + candidato);
        }
        System.out.println();
    }


    /*Função estática que imprime os candidatos que se beneficiaram do sistema proporcional*/
	/*Entradas: lista dos candidatos (LinkedList<Candidato>) e número total de vagas (int)*/
	/*Saída: Nada*/

    private static void ImprimeCandidatosBeneficiados(LinkedList<Candidato> listaCandidatos, int nvagas) {
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)");
        
        //A lista já está ordenada
        
        int i;
        for (i=nvagas; i < listaCandidatos.size(); i++){
            Candidato candidato = listaCandidatos.get(i);
            if(candidato.foiEleito()) 
                System.out.println((i+1) + " - " + candidato);
        }
        System.out.println();
    }

    /*Função estática que imprime informações dos partidos*/
	/*Entradas: lista dos partidos (LinkedList<Partido>)*/
	/*Saída: Nada*/

    private static void ImprimePartidos(LinkedList<Partido> listaPartidos){
        listaPartidos.sort(null);
        System.out.println("Votação dos partidos e número de candidatos eleitos:");
        int i, n = 1;
        for (i=0; i < listaPartidos.size(); i++){
            System.out.println(n + " - " + listaPartidos.get(i).toString());
            n++;
        }
        System.out.println();

    }

    /*Função estática que imprime o primeiro e o último candidato mais votado de cada partido*/
	/*Entradas: lista dos partidos (LinkedList<Partido>)*/
	/*Saída: Nada*/

    private static void ImprimePrimeiroeUltimo(LinkedList<Partido> listaPartidos){
        Collections.sort(listaPartidos, new Comparador());
        System.out.println("Primeiro e último colocados de cada partido:");
        int i, n = 1;
        for (i=0; i < listaPartidos.size(); i++){
            Partido partido = listaPartidos.get(i);
            Candidato maisVotado = partido.getmaisVotado();
            Candidato menosVotado = partido.getmenosVotado();
            if (maisVotado != null && menosVotado != null) {
                if (maisVotado.getVotosNominais() <= 1 && menosVotado.getVotosNominais() <= 1){
                    System.out.println(n + " - " + partido.getSigla() + " - " + partido.getNumero() + ", " +  maisVotado.getNomeUrna() + " (" + maisVotado.getNumero() + ", " + maisVotado.getVotosNominais() + " voto) / " + menosVotado.getNomeUrna() + " (" + menosVotado.getNumero() + ", " + menosVotado.getVotosNominais() + " voto)");
                    n++;
                }
                else if (maisVotado.getVotosNominais() <= 1){
                    System.out.println(n + " - " + partido.getSigla() + " - " + partido.getNumero() + ", " +  maisVotado.getNomeUrna() + " (" + maisVotado.getNumero() + ", " + maisVotado.getVotosNominais() + " voto) / " + menosVotado.getNomeUrna() + " (" + menosVotado.getNumero() + ", " + menosVotado.getVotosNominais() + " votos)");
                    n++;
                }
                else if (menosVotado.getVotosNominais() <= 1){
                    System.out.println(n + " - " + partido.getSigla() + " - " + partido.getNumero() + ", " +  maisVotado.getNomeUrna() + " (" + maisVotado.getNumero() + ", " + maisVotado.getVotosNominais() + " votos) / " + menosVotado.getNomeUrna() + " (" + menosVotado.getNumero() + ", " + menosVotado.getVotosNominais() + " voto)");
                    n++;
                }
                else {
                    System.out.println(n + " - " + partido.getSigla() + " - " + partido.getNumero() + ", " +  maisVotado.getNomeUrna() + " (" + maisVotado.getNumero() + ", " + maisVotado.getVotosNominais() + " votos) / " + menosVotado.getNomeUrna() + " (" + menosVotado.getNumero() + ", " + menosVotado.getVotosNominais() + " votos)");
                    n++;
                }
            }
    
        }
        System.out.println();
    }

    /*Função estática que imprime a distribuição de idade dos candidatos eleitos por faixa etária*/
	/*Entradas: lista dos candidatos (LinkedList<Candidato>)*/
	/*Saída: Nada*/

    private static void ImprimeDistribuicaoIdade(LinkedList<Candidato> listaCandidatos) {
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
        System.out.printf(locale, "      Idade < 30: %d (%.2f%%)\n", nMenor30, pMenor30);
        System.out.printf(locale, "30 <= Idade < 40: %d (%.2f%%)\n", n30_40, p30_40);
        System.out.printf(locale, "40 <= Idade < 50: %d (%.2f%%)\n", n40_50, p40_50);
        System.out.printf(locale, "50 <= Idade < 60: %d (%.2f%%)\n", n50_60, p50_60);
        System.out.printf(locale, "60 <= Idade     : %d (%.2f%%)\n", nMaiorIgual60, pMaiorIgual60);
        

        System.out.println();
    }

    /*Função estática que imprime a distribuição dos candidatos eleitos por sexo*/
	/*Entradas: lista dos candidatos (LinkedList<Candidato>)*/
	/*Saída: Nada*/

    private static void ImprimeDistribuicaoSexo(LinkedList<Candidato> listaCandidatos) {
        System.out.println("Eleitos, por sexo:");
        int nMasculino=0, nFeminino=0;
        float pMasculino;

        int i;
        for (i=0; i < listaCandidatos.size(); i++){
            Candidato candidato = listaCandidatos.get(i);
            if(candidato.foiEleito()){
                //Verifica qual o sexo do candidato e incrementa o contador dele
                if(candidato.getSexo().equals("M")) nMasculino++;
                else nFeminino++;
            }
        }
        //Encontrar a proporção de cada sexo
        if(nMasculino+nFeminino > 0){
            pMasculino = (float)nMasculino/(nMasculino+nFeminino);
        } else {
            pMasculino = 0.5f;
        }

        //Imprimir o resultado:
        System.out.printf(locale, "Feminino:  %d (%.2f%%)\n", nFeminino, (1-pMasculino)*100f);
        System.out.printf(locale, "Masculino: %d (%.2f%%)\n", nMasculino, pMasculino*100f);
        

        System.out.println();
    }

    /*Função estática que imprime os votos totais válidos, nominais e de legenda e imprime sua distribuição*/
	/*Entradas: lista dos partidos (LinkedList<Partido>)*/
	/*Saída: Nada*/
    
    private static void ImprimeVotosTotais(LinkedList<Partido> listaPartidos){
        int i, votosTotais = 0, totaisNominais = 0, totaisLegenda = 0;
        float porcentoNominal, porcentoLegenda;
        for (i=0; i < listaPartidos.size(); i++){
            totaisNominais += listaPartidos.get(i).getVotosNominais();
            totaisLegenda += listaPartidos.get(i).getVotos();
        }
        votosTotais = totaisNominais + totaisLegenda;
        if(votosTotais == 0){
            porcentoLegenda = 0f;
            porcentoNominal = 0f;
        } else {
            porcentoNominal = 100*((float)totaisNominais / (float)votosTotais);
            porcentoLegenda = 100*((float)totaisLegenda / (float)votosTotais);
        }


        Formatter fmt = new Formatter();
        fmt.format(locale, "Total de votos válidos:    %d\nTotal de votos nominais:   %d (%.2f%%)\nTotal de votos de Legenda: %d (%.2f%%)", votosTotais, totaisNominais, porcentoNominal, totaisLegenda, porcentoLegenda);
        String string = fmt.out().toString();
        fmt.close();

        System.out.print(string);

    }

    

    
}

