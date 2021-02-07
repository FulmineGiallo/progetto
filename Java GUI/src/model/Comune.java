package model;

public class Comune
{
    private String codiceComune; /* Codice Catastale di un comune */
    private String nomeComune;

    public Comune(String nomeComune, String codiceComune)
    {
        this.nomeComune = nomeComune;
        this.codiceComune = codiceComune;
    }

    public String getCodiceComune() {
        return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {
        this.codiceComune = codiceComune;
    }

    public String getNomeComune() {
        return nomeComune;
    }

    public void setNomeComune(String nomeComune) {
        this.nomeComune = nomeComune;
    }

    @Override
    public String toString() {
        return codiceComune + " - " + nomeComune;
    }
}
