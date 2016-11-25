package by.silverscreen.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Types;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sbaranau on 11/25/2016.
 */
@org.springframework.stereotype.Repository("dataRespitory")
public class DataRepositoryImpl implements DataRepository<PhoneEntity> {

    @Autowired
    protected JdbcOperations jdbcOperations;

    @Override
    public void persist(PhoneEntity object) {

        Object[] params = new Object[] { object.getId(), object.getToken(), object.getLogin(), object.getPassword() };
        int[] types = new int[] {        Types.VARCHAR,    Types.VARCHAR,      Types.VARCHAR,      Types.VARCHAR };

        jdbcOperations.update("INSERT INTO phone(\n" +
                "            data_id, token, login, password )\n" +
                "    VALUES (cast(? as UUID), ?, ?, ?);", params, types);
    }

    @Override
    public void delete(PhoneEntity object) {
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
    public boolean checkName(String name) {
        return false;
    }

    @Override
    public boolean checkToken(String token) {
        return false;
    }


}
