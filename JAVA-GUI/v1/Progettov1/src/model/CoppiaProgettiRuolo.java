package model;

public class CoppiaProgettiRuolo <Progetto, Ruolo>
{
    private Ruolo r;
    private Progetto p;

    public CoppiaProgettiRuolo(Ruolo r, Progetto p)
    {
        this.r = r;
        this.p = p;
    }

    public Ruolo getR()
    {
        return r;
    }

    public void setR(Ruolo r)
    {
        this.r = r;
    }

    public Progetto getP()
    {
        return p;
    }

    public void setP(Progetto p)
    {
        this.p = p;
    }
}
