package model.Dao;

import javafx.collections.ObservableList;
import model.DaoInterface.SkillDaoInterface;
import model.Skill;

import java.sql.Connection;

public class SkillDao implements SkillDaoInterface
{
    Connection connection;

    public SkillDao(Connection connection)
    {
        this.connection = connection;
    }
    @Override
    public ObservableList<Skill> skillList()
    {
        return null;
    }
}
