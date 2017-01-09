package by.silverscreen.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.Types;
import java.util.Date;

/**
 * Created by sbaranau on 11/25/2016.
 */
@org.springframework.stereotype.Repository("dataRespitory")
public class DataRepositoryImpl implements DataRepository<PhoneDAO> {

    @Autowired
    protected JdbcOperations jdbcOperations;

    @Override
    public void persist(PhoneDAO object) {

        Object[] params = new Object[] { object.getId(), object.getToken(), object.getLogin(), object.getPassword(), new Date().getTime(), object.getDateOfBirth(), object.getUser(), object.getIsman() , object.getSystem()};
        int[] types = new int[] {        Types.VARCHAR,    Types.VARCHAR,      Types.VARCHAR,      Types.VARCHAR,      Types.BIGINT,          Types.BIGINT ,       Types.VARCHAR,      Types.BIGINT,      Types.VARCHAR, };

        jdbcOperations.update("INSERT INTO phone(\n" +
                "            data_id, token, login, password, date, birth,name, isman, system )\n" +
                "    VALUES (cast(? as UUID), ?, ?, ?, ?,?,?,?,?);", params, types);
    }

    @Override
    public void delete(PhoneDAO object) {
        jdbcOperations.update("DELETE FROM phone\n" +
                " WHERE data_id = '" + object.getId().toString() + "';");
    }

    @Override
    public void updatePhoneByLogin(PhoneDAO phoneDAO) {
        Object[] params = new Object[] {  phoneDAO.getToken(), phoneDAO.getPassword(),  new Date().getTime(), phoneDAO.getDateOfBirth(), phoneDAO.getUser(), phoneDAO.getIsman(), phoneDAO.getSystem(), phoneDAO.getLogin()};
        int[] types = new int[] {          Types.VARCHAR,      Types.VARCHAR,             Types.BIGINT,           Types.BIGINT ,           Types.VARCHAR,      Types.BIGINT,         Types.VARCHAR,    Types.VARCHAR   };

        jdbcOperations.update("UPDATE phone \n" +
                "            SET token=?, password=?, date=?, birth=?, name=?, isman=?, system=? \n" +
                "            WHERE login=? ;",
                                params, types);
    }

    @Override
    public void updatePhoneByToken(PhoneDAO phoneDAO) {
        Object[] params = new Object[] {   phoneDAO.getLogin(), phoneDAO.getPassword(), new Date().getTime(), phoneDAO.getDateOfBirth(), phoneDAO.getUser(), phoneDAO.getIsman(), phoneDAO.getSystem(), phoneDAO.getToken() };
        int[] types = new int[] {          Types.VARCHAR,       Types.VARCHAR,       Types.BIGINT,           Types.BIGINT ,           Types.VARCHAR,      Types.BIGINT,            Types.VARCHAR,        Types.VARCHAR  };

        jdbcOperations.update("UPDATE phone \n" +
                        "            SET login=?, password=?, date=?, birth=?, name=?, isman=?, system=? \n" +
                        "            WHERE token=? ;",
                params, types);
    }

}
