package model;

public class RiunioneTelematica extends Riunione
{
    String Piattaforma;
    String codiceAccesso;
    Riunione r;

    public String getPiattaforma() {
        return Piattaforma;
    }

    public void setPiattaforma(String piattaforma) {
        Piattaforma = piattaforma;
    }

    public String getCodiceAccesso() {
        return codiceAccesso;
    }

    public void setCodiceAccesso(String codiceAccesso) {
        this.codiceAccesso = codiceAccesso;
    }

    public RiunioneTelematica(String titolo)
    {
        super(titolo);
    }
}
