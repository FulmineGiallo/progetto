package model;

public class Salario
{
    int salario;
    int nProgetti;
    Impiegato impiegato;

    public Salario(Impiegato impiegato)
    {
        this.impiegato = impiegato;
        this.salario = calcoloSalario(impiegato);

    }

    private int calcoloSalario(Impiegato impiegato)
    {


        return 0;
    }
    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public int getnProgetti() {
        return nProgetti;
    }

    public void setnProgetti(int nProgetti) {
        this.nProgetti = nProgetti;
    }

    public Impiegato getImpiegato() {
        return impiegato;
    }

    public void setImpiegato(Impiegato impiegato) {
        this.impiegato = impiegato;
    }
}
