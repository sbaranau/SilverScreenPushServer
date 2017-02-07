package by.silverscreen.DAO;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by sbaranau on 2/7/2017.
 */
@Entity
@Table(name = "notifications")
public class NotificationDAO {

    @Id
    @Column(name = "data_id")
    String id;
    @Column(name = "login")
    String login = "";
    @Column(name = "films")
    String film;
    @Column(name = "startnotif")
    Long startNotification = 0L;
    @Column(name = "endnotif")
    Long endNotification = 0L;
    @Column(name = "tikets")
    String tickets = "";
    @Column(name = "time")
    long time = 0L;
    @Column(name = "morningsend")
    boolean morningSend = false;
    @Column(name = "wantrecieve")
    boolean wantReceive = true;
    @Column(name = "filmreminder")
    boolean reminder = true;

    @ManyToOne/*(cascade = CascadeType.ALL)*/
    @JoinColumn(name = "login", nullable = false)
    PhoneDAO phoneDAO;

    public NotificationDAO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public Long getStartNotification() {
        return startNotification;
    }

    public void setStartNotification(Long startNotification) {
        this.startNotification = startNotification;
    }

    public Long getEndNotification() {
        return endNotification;
    }

    public void setEndNotification(Long endNotification) {
        this.endNotification = endNotification;
    }

    public String getTickets() {
        return tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isMorningSend() {
        return morningSend;
    }

    public void setMorningSend(boolean morningSend) {
        this.morningSend = morningSend;
    }

    public boolean isWantReceive() {
        return wantReceive;
    }

    public void setWantReceive(boolean wantReceive) {
        this.wantReceive = wantReceive;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public PhoneDAO getPhoneDAO() {
        return phoneDAO;
    }

    public void setPhoneDAO(PhoneDAO phoneDAO) {
        this.phoneDAO = phoneDAO;
    }
}
