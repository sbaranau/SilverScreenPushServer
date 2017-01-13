package by.silverscreen.service;

/**
 * Created by sbaranau on 1/11/2017.
 */
import by.silverscreen.Entities.NotificationEntity;
import by.silverscreen.Entities.TicketEntity;
import by.silverscreen.Entities.TokenEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

@Component
public class Task {

    @Autowired
    DataServiceImpl dataService;

    private static final Logger LOG = LoggerFactory.getLogger(Task.class);

    @Scheduled(cron  = "${scheduled.delay}")
    public void runCheckTodayTikets() {
        try {
            LOG.info("check ticket started");
            checkTodayTickets();
            LOG.info("check ticket end");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


   @Scheduled(cron  = "${scheduledPush.delay}")
    public void runSendPushTodayTikets() {
        try {
            LOG.info("send push started");
            configurePush();
            LOG.info("send push end");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void checkTodayTickets() {
        Set<TokenEntity> tokens = dataService.getAllTokens();
        dataService.clearNotifications();
        Predicate<TokenEntity> isHaveLogin = s -> s.getLogin() != null && s.getLogin().length() > 0 && !s.getLogin().contains("Anonymous");
        tokens.stream().filter(isHaveLogin::test).forEach(token -> {
            Set<TicketEntity> tickets = dataService.getTickets(token);
            if (tickets.size() > 0) {
                tickets.stream().forEach(ticket -> {
                    dataService.addTicketByLogin(ticket, token.getLogin());
                });
            }

        });
    }

    private void configurePush() {
        LocalDateTime startDate = LocalDateTime.now();
        startDate = startDate.minusMinutes(startDate.getMinute()).minusHours(startDate.getHour());
        LOG.info(startDate.toString());
        Set<NotificationEntity> notificationEntities = dataService.getAllNotificationsByTime(startDate, startDate.plusDays(1));
        Map<String, String> tokenToSend = new HashMap<>();
        notificationEntities.stream().forEach(notificationEntity -> {
            tokenToSend.merge(dataService.checkName(notificationEntity.getLogin()).getToken(), notificationEntity.getTickets(), (value, newValue) -> value.concat("; " + newValue));
        });
        LOG.info(tokenToSend.toString());

    }
}
