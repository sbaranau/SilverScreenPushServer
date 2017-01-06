package by.silverscreen.DAO;

import by.silverscreen.Entities.TokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Types;
import java.util.Date;
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
        SqlRowSet rowSet = jdbcOperations.queryForRowSet("SELECT data_id,token,login,password,name,isman,birth,system FROM phone p WHERE login LIKE '" + name +"';");
        return getPhoneDAO(rowSet);
    }

    private PhoneDAO getPhoneDAO(SqlRowSet rowSet) {
        PhoneDAO result = null;
        while (rowSet.next()) {
            result = new PhoneDAO();
            result.setId(UUID.fromString(rowSet.getString("data_id")));
            result.setLogin(rowSet.getString("login"));
            result.setPassword(rowSet.getString("password"));
            result.setToken(rowSet.getString("token"));
            result.setUser(rowSet.getString("name"));
            result.setIsman(rowSet.getInt("isman"));
            result.setDateOfBirth(rowSet.getLong("birth"));
            result.setSystem(rowSet.getString("system"));
        }
        return result;
    }

    @Override
    public PhoneDAO checkToken(String token) {
        SqlRowSet rowSet = jdbcOperations.queryForRowSet("SELECT data_id,token,login,password,name,isman,birth,system FROM phone p WHERE token LIKE '" + token +"';");
        return getPhoneDAO(rowSet);
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

    @Override
    public Set<PhoneDAO> getAllTokens() {
        Set<PhoneDAO> results = new HashSet<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet("SELECT data_id,token,login,password,date,birth,isman,name,system FROM phone p;");
        while (rowSet.next()) {
            PhoneDAO result = new PhoneDAO();
            result.setId(UUID.fromString(rowSet.getString("data_id")));
            result.setLogin(rowSet.getString("login"));
            result.setPassword(rowSet.getString("password"));
            result.setToken(rowSet.getString("token"));
            result.setDate(rowSet.getLong("date"));
            result.setDateOfBirth(rowSet.getLong("birth"));
            result.setIsman(rowSet.getInt("isman"));
            result.setUser(rowSet.getString("name"));
            result.setSystem(rowSet.getString("system"));
            results.add(result);
        }
        return results;
    }

    @Override
    public void updateUser(TokenEntity tokenEntity) {

    }

}
