package by.silverscreen.DAO;

import by.silverscreen.Entities.TokenEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sbaranau on 11/25/2016.
 */
@Entity
@Table(name = "phone")
public class PhoneDAO implements Serializable {

    @Id
    @Column(name = "data_id", nullable = false)
    private UUID id;
    @Column(name = "token")
    private String token;
    @Column(name = "login", unique = true)
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "date")
    private long date;
    @Column(name = "birth")
    private long dateOfBirth;
    @Column(name = "isman")
    private int isman;
    @Column(name = "name")
    private String user;
    @Column(name = "system")
    private String system;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "login")
    Set<NotificationDAO> notificationsDAO;


    public PhoneDAO(TokenEntity tokenEntity) {
        id=UUID.randomUUID();
        login = "";
        if (tokenEntity.getLogin() != null) {
           login = (tokenEntity.getLogin());
        }
        token = "";
        if (tokenEntity.getToken() != null) {
            token = (tokenEntity.getToken());
        }
        password = "";
        if (tokenEntity.getPassword() != null) {
           password = (tokenEntity.getPassword());
        }
        user = "";
        if (tokenEntity.getUser() != null) {
            user = (tokenEntity.getUser());
        }
        dateOfBirth = 0;
        if (tokenEntity.getDateOfBirth() != 0) {
            try {
                dateOfBirth = tokenEntity.getDateOfBirth();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        if (tokenEntity.getIsman() != 0) {
            try {
                isman = tokenEntity.getIsman();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        system = "";
        if (tokenEntity.getSystem() != null) {
            try {
                system = tokenEntity.getSystem();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        date = 0;
    }

    public PhoneDAO() {
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

    public long getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = 0;
        try {
            this.date = Long.parseLong(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void setDate(long date) {
        this.date = date;
    }


    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = 0;
        try {
            this.dateOfBirth = Long.parseLong(dateOfBirth);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getIsman() {
        return isman;
    }

    public void setIsman(String isman) {
        this.isman = 0;
        try {
            this.isman = Integer.parseInt(isman);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void setIsman(int isman) {
        this.isman = isman;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Set<NotificationDAO> getNotificationsDAO() {
        return notificationsDAO;
    }

    public void setNotificationsDAO(Set<NotificationDAO> notificationsDAO) {
        this.notificationsDAO = notificationsDAO;
    }
}
