package Objetos;

import java.util.Comparator;

// segundo comparador para ordenar os mais votados de cada partido

public class Comparador implements Comparator<Partido> {

    public int compare(Partido a, Partido b){
        if(a.getmaisVotado().getVotosNominais() > b.getmaisVotado().getVotosNominais()) return -1;
        else return 1;
    }
}