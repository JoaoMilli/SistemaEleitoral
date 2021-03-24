package Objetos;

import java.util.Comparator;

// segundo comparador para ordenar os mais votados de cada partido

public class Comparador implements Comparator<Partido> {

    public int compare(Partido a, Partido b){
        //Colocar os partidos sem candidatos válidos em posições menores
        if(a.getmaisVotado() == null && b.getmaisVotado() == null) return 0;
        if(a.getmaisVotado() == null) return -1;
        if(b.getmaisVotado() == null) return 1;

        //Compara qual partido tem um candidato com mais votos
        if(a.getmaisVotado().getVotosNominais() > b.getmaisVotado().getVotosNominais()) return -1;
        if(a.getmaisVotado().getVotosNominais() < b.getmaisVotado().getVotosNominais()) return 1;
        //Se a quantidade de votos nominais no mais votado forem iguais nos dois partidos verifica o menos votado
        if(a.getmenosVotado().getVotosNominais() > b.getmenosVotado().getVotosNominais()) return -1;
        if(a.getmenosVotado().getVotosNominais() < b.getmenosVotado().getVotosNominais()) return 1;
        else return 0;
    }
}