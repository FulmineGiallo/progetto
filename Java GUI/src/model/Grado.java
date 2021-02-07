package model;

public class Grado
{
    private String tipoGrado;

    public Grado(String tipoGrado)
    {
        this.tipoGrado = tipoGrado;
    }

    public String getTipoGrado()
    {
        return tipoGrado;
    }

    public void setTipoGrado(String tipoGrado)
    {
        this.tipoGrado = tipoGrado;
    }

    @Override
    public String toString()
    {
        return tipoGrado;
    }
}
