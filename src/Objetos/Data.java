package Objetos;

public class Data {
    private int dia;
    private int mes;
    private int ano;

    
    public Data (String data){
        String[] dividido = data.split("/");
        this.dia = Integer.parseInt(dividido[0]);
        this.mes = Integer.parseInt(dividido[1]);
        this.ano = Integer.parseInt(dividido[2]);
    }


    public int getAno() {
        return ano;
    }


    public void setAno(int ano) {
        this.ano = ano;
    }


    public int getMes() {
        return mes;
    }


    public void setMes(int mes) {
        this.mes = mes;
    }


    public int getDia() {
        return dia;
    }


    public void setDia(int dia) {
        this.dia = dia;
    }

    public int anosPassados(Data dataDepois){
        int anos = dataDepois.ano - this.ano - 1;
        if(dataDepois.mes > this.mes){
            anos++;
        } else if(dataDepois.mes == this.mes && dataDepois.dia > dataDepois.dia){
            anos++;
        }
        return anos;
    }
}
