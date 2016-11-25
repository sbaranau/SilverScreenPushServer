package by.silverscreen.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Types;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sbaranau on 11/25/2016.
 */
@org.springframework.stereotype.Repository("dataRespitory")
public class DataRepositoryImpl implements DataRepository<PhoneDAO> {

    @Autowired
    protected JdbcOperations jdbcOperations;

    @Override
    public void persist(PhoneDAO object) {

        Object[] params = new Object[] { object.getId(), object.getToken(), object.getLogin(), object.getPassword() };
        int[] types = new int[] {        Types.VARCHAR,    Types.VARCHAR,      Types.VARCHAR,      Types.VARCHAR };

        jdbcOperations.update("INSERT INTO phone(\n" +
                "            data_id, token, login, password )\n" +
                "    VALUES (cast(? as UUID), ?, ?, ?);", params, types);
    }

    @Override
    public void delete(PhoneDAO object) {
        jdbcOperations.update("DELETE FROM phone\n" +
                " WHERE data_id = '" + object.getId().toString() + "';");
    }

    @Override
    public Set<String> getRandomData() {
        Set<String> result = new HashSet<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet("SELECT token FROM phone p ORDER BY RANDOM() LIMIT 50;");
        while (rowSet.next()) {
            result.add(rowSet.getString("data_description"));
        }
        return result;
    }

    @Override
    public PhoneDAO checkName(String name) {
        PhoneDAO result = null;
        SqlRowSet rowSet = jdbcOperations.queryForRowSet("SELECT data_id,token,login,password FROM phone p WHERE login LIKE '" + name +"';");
        while (rowSet.next()) {
            result = new PhoneDAO();
            result.setId(UUID.fromString(rowSet.getString("data_id")));
            result.setLogin(rowSet.getString("login"));
            result.setPassword(rowSet.getString("password"));
            result.setToken(rowSet.getString("token"));
        }
        return result;
    }

    @Override
    public PhoneDAO checkToken(String token) {
        PhoneDAO result = null;
        SqlRowSet rowSet = jdbcOperations.queryForRowSet("SELECT data_id,token,login,password FROM phone p WHERE token LIKE '" + token +"';");
        while (rowSet.next()) {
            result = new PhoneDAO();
            result.setId(UUID.fromString(rowSet.getString("data_id")));
            result.setLogin(rowSet.getString("login"));
            result.setPassword(rowSet.getString("password"));
            result.setToken(rowSet.getString("token"));
        }
        return result;
    }

    @Override
    public void updatePhoneByLogin(PhoneDAO phoneDAO) {
        Object[] params = new Object[] {  phoneDAO.getToken(), phoneDAO.getPassword(), phoneDAO.getLogin() };
        int[] types = new int[] {          Types.VARCHAR,      Types.VARCHAR,      Types.VARCHAR };

        jdbcOperations.update("UPDATE phone \n" +
                "            SET token=?, password=? \n" +
                "            WHERE login=? ;",
                                params, types);
    }

    @Override
    public void updatePhoneByToken(PhoneDAO phoneDAO) {
        Object[] params = new Object[] {  phoneDAO.getPassword(), phoneDAO.getLogin(), phoneDAO.getToken() };
        int[] types = new int[] {          Types.VARCHAR,      Types.VARCHAR,      Types.VARCHAR };

        jdbcOperations.update("UPDATE phone \n" +
                        "            SET login=?, password=? \n" +
                        "            WHERE token=? ;",
                params, types);
    }

}
