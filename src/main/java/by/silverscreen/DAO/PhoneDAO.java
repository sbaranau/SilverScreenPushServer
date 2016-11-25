package by.silverscreen.DAO;

import by.silverscreen.Entities.TokenEntity;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by sbaranau on 11/25/2016.
 */
public class PhoneDAO implements DomainObject{

    private UUID id;
    private String token = "";
    private String login = "";
    private String password = "";

    public PhoneDAO() {
    }

    public PhoneDAO(UUID id, String token, String login, String password) {
        this.id = id;
        this.token = token;
        this.login = login;
        this.password = password;
    }

    public PhoneDAO(String token, String login, String password) {
        this.token = token;
        this.login = login;
        this.password = password;
    }

    public PhoneDAO(TokenEntity tokenEntity) {
        PhoneDAO phoneDAO = new PhoneDAO();
        phoneDAO.setId(UUID.randomUUID());
        if (tokenEntity.getLogin() != null) {
           phoneDAO.setLogin(tokenEntity.getLogin());
        }
        if (tokenEntity.getToken() != null) {
            phoneDAO.setToken(tokenEntity.getToken());
        }
        if (tokenEntity.getPassword() != null) {
            phoneDAO.setPassword(tokenEntity.getPassword());
        }
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
