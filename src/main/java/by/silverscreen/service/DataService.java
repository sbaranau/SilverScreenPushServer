package by.silverscreen.service;

import by.silverscreen.Entities.NotificationEntity;
import by.silverscreen.Entities.PushEntity;
import by.silverscreen.Entities.TicketEntity;
import by.silverscreen.Entities.TokenEntity;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by sbaranau on 11/25/2016.
 */
public interface DataService {

    boolean persist(TokenEntity tokenEntity);

    TokenEntity checkName(String name);

    TokenEntity checkToken(String token);

    Set<TokenEntity> getAllTokens();

    boolean sendPush(PushEntity pushEntity);

    Set<NotificationEntity> getNotificationForLogin(TokenEntity token);

    String checkFilms(TokenEntity tokenEntity, Set<NotificationEntity> notificationEntities);

    void addTicketByLogin(TicketEntity notification, String login);

    void clearNotifications();

    Set<NotificationEntity> getAllNotifications();

    Set<NotificationEntity> getAllNotificationsByTime(LocalDateTime start, LocalDateTime finish);
}