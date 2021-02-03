package model;

public class RiunioneFisica extends Riunione
{
    String Sede;
    String Piano;
    String nomeStanza;
    Riunione r;

    public String getSede() {
        return Sede;
    }

    public void setSede(String sede) {
        Sede = sede;
    }

    public String getPiano() {
        return Piano;
    }

    public void setPiano(String piano) {
        Piano = piano;
    }

    public String getNomeStanza() {
        return nomeStanza;
    }

    public void setNomeStanza(String nomeStanza) {
        this.nomeStanza = nomeStanza;
    }

    public Riunione getR() {
        return r;
    }

    public void setR(Riunione r) {
        this.r = r;
    }

    public RiunioneFisica(String titolo)
    {
        super(titolo);
    }
}
