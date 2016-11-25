package by.silverscreen.Entities;

import by.silverscreen.DAO.PhoneDAO;

import java.io.Serializable;

/**
 * Created by sbaranau on 11/25/2016.
 */
public class TokenEntity implements Serializable {
    private String token = "";
    private String user = "";
    private String login = "";
    private String password = "";


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

    public TokenEntity(String token, String user, String login, String password) {
        this.token = token;
        this.user = user;
        this.login = login;
        this.password = password;
    }

    public TokenEntity() {
    }

    public TokenEntity(PhoneDAO phoneDAO) {
        TokenEntity tokenEntity = new TokenEntity();
        if (phoneDAO.getToken() != null) {
            tokenEntity.setToken(phoneDAO.getToken());
        }
        if (phoneDAO.getLogin() != null) {
            tokenEntity.setLogin(phoneDAO.getLogin());
        }
        if (phoneDAO.getPassword() != null) {
            tokenEntity.setPassword(phoneDAO.getPassword());
        }
    }
}
