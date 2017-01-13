package by.silverscreen.Entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sbaranau on 1/11/2017.
 */
public class NotificationEntity implements Serializable{

    String login = "";
    Set<String> film;
    Long startNotification = 0L;
    Long endNotification = 0L;
    String tickets = "";
    boolean wantReceive = true;

    public NotificationEntity(String login, Set<String> film, Long startNotification, Long endNotification, String tickets) {
        this.login = login;
        this.film = film;
        this.startNotification = startNotification;
        this.endNotification = endNotification;
        this.tickets = tickets;
    }

    public NotificationEntity(String login, Set<String> film, Long startNotification, Long endNotification, String tickets, boolean wantReceive) {
        this.login = login;
        this.film = film;
        this.startNotification = startNotification;
        this.endNotification = endNotification;
        this.tickets = tickets;
        this.wantReceive = wantReceive;
    }

    public NotificationEntity() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<String> getFilm() {
        if (film != null) {
            film = new HashSet<>();
        }
        return film;
    }

    public void setFilm(Set<String> film) {
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

    public String getTikets() {
        return tickets;
    }

    public void setTikets(String tikets) {
        this.tickets = tikets;
    }

    public String getTickets() {
        return tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
    }

    public boolean isWantReceive() {
        return wantReceive;
    }

    public void setWantReceive(boolean wantReceive) {
        this.wantReceive = wantReceive;
    }
}
