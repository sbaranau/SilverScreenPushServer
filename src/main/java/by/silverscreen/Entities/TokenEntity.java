package by.silverscreen.Entities;

import by.silverscreen.DAO.PhoneDAO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by sbaranau on 11/25/2016.
 */
public class TokenEntity implements Serializable {
    private String token = "";
    private String user = "";
    private String login = "";
    private String password = "";
    private long date = 0;
    private long dateOfBirth = 0;
    private int isman = 0;
    private String system = "";

    public TokenEntity() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TokenEntity(PhoneDAO phoneDAO) {
        if (phoneDAO.getToken() != null) {
            token = phoneDAO.getToken();
        }
        if (phoneDAO.getLogin() != null) {
            login = (phoneDAO.getLogin());
        }
        if (phoneDAO.getPassword() != null) {
            password = (phoneDAO.getPassword());
        }
        if (phoneDAO.getUser() != null) {
            user = (phoneDAO.getUser());
        }
        date = phoneDAO.getDate();
        dateOfBirth = phoneDAO.getDateOfBirth();
        isman = phoneDAO.getIsman();
        system = phoneDAO.getSystem();
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getIsman() {
        return isman;
    }

    public void setIsman(int isman) {
        this.isman = isman;
    }

    @JsonCreator
    public TokenEntity(@JsonProperty("token") String token, @JsonProperty("user") String user, @JsonProperty("login") String login, @JsonProperty("password") String password,
                       @JsonProperty("date") long date, @JsonProperty("dateOfBirth") long dateOfBirth, @JsonProperty("isman") int isman, @JsonProperty("system") String system) {
        this.token = token;
        this.user = user;
        this.login = login;
        this.password = password;
        this.date = date;
        this.dateOfBirth = dateOfBirth;
        this.isman = isman;
        this.system = system;
    }

}
