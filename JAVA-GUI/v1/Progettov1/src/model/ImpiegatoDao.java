package model;

import java.util.ArrayList;
import java.util.List;

public class ImpiegatoDao implements Dao<ImpiegatoDao>
{
    List<ImpiegatoDao> impiegati = new ArrayList<>();

    public ImpiegatoDao()
    {

    }

    @Override
    public void save(ImpiegatoDao impiegato)
    {
            impiegati.add(impiegato);
    }

    @Override
    public void update(ImpiegatoDao o, String parametri[])
    {

    }

    @Override
    public void delete(ImpiegatoDao impiegati)
    {
        System.out.println("Ho eliminato: ");
        System.out.println("Prova 55");
    }


    @Override
    public List<ImpiegatoDao> getAll() {
        return impiegati;
    }
}
