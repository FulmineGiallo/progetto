package model;

public class Salario
{
    private int salario;
    private Impiegato impiegato;

    public Salario(Impiegato impiegato)
    {
        this.impiegato = impiegato;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public Impiegato getImpiegato() {
        return impiegato;
    }

    public void setImpiegato(Impiegato impiegato) {
        this.impiegato = impiegato;
    }
}