package Objetos;

public class Data {
    private int dia;
    private int mes;
    private int ano;

    
    public Data (String data) throws Exception{
        String[] dividido = data.split("/");
        if(dividido.length == 3){
            this.dia = Integer.parseInt(dividido[0]);
            this.mes = Integer.parseInt(dividido[1]);
            this.ano = Integer.parseInt(dividido[2]);
        } else {
            throw new Exception();
        }
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

    public int compareTo(Data outraData){
        if(this.ano > outraData.ano){ //Vem depois
            return 1;
        } else if (this.ano < outraData.ano){ //Vem antes da outra data
            return -1;
        } else if (this.mes > outraData.mes){ //Vem depois da outra data
            return 1;
        } else if (this.mes < outraData.mes){ //Vem antes da outra data
            return -1;
        } else if (this.dia > outraData.dia){ //Vem depois da outra data
            return 1;
        } else if (this.dia < outraData.dia){ //Vem antes da outra data
            return -1;
        } else { //Mesma data
            return 0;
        }
    }
}
