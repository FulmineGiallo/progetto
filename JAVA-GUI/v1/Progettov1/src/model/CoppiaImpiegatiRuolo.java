package model;

public class CoppiaImpiegatiRuolo <Impiegato, Ruolo>
{
    Impiegato i;
    Ruolo r;

    public CoppiaImpiegatiRuolo(Impiegato i, Ruolo r) {
        this.i = i;
        this.r = r;
    }

    public Impiegato getI() {
        return i;
    }

    public void setI(Impiegato i) {
        this.i = i;
    }

    public Ruolo getR() {
        return r;
    }

    public void setR(Ruolo r) {
        this.r = r;
    }
}
