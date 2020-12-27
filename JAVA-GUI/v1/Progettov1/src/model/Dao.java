package model;

import java.util.List;

public interface Dao<O>
{
    void save(O o);
    void update(O o, String parametri);
    void delete(O o);

    List<O> getAll();


}
