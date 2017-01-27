package by.silverscreen.DAO;

import static by.silverscreen.jooq.tables.Phone.PHONE;
import static by.silverscreen.jooq.tables.Notifications.NOTIFICATIONS;

import by.silverscreen.Entities.NotificationEntity;
import by.silverscreen.Entities.TicketEntity;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jooq.Record;
import org.jooq.Result;

import java.util.Date;
import java.util.UUID;


/**
 * Created by sergey on 1/7/17.
 */
@Service
public class JooqRepository {
    @Autowired
    private DSLContext dsl;


    public Result<Record> getAllUsers(){
        return  dsl.select().from(PHONE).orderBy(PHONE.DATE.desc()).fetch();
    }

    public Record checkName(String name) {
        return dsl.select().from(PHONE).where(PHONE.LOGIN.like(name)).fetchOne();
    }

    public Record checkToken(String token) {
        return dsl.select().from(PHONE).where(PHONE.TOKEN.like(token)).fetchOne();
    }
    @Transactional
    public void deleteByLogin(String name) {
        dsl.deleteFrom(PHONE).where(PHONE.LOGIN.like(name)).execute();
    }

    @Transactional
    public void deleteByToken(String token) {
        dsl.deleteFrom(PHONE).where(PHONE.TOKEN.like(token)).execute();
    }

    @Transactional
    public void persist(PhoneDAO object) {
        dsl.insertInto(PHONE)
                .set(PHONE.DATA_ID, object.getId())
                .set(PHONE.TOKEN, object.getToken())
                .set(PHONE.LOGIN, object.getLogin())
                .set(PHONE.PASSWORD, object.getPassword())
                .set(PHONE.DATE, new Date().getTime())
                .set(PHONE.BIRTH, object.getDateOfBirth())
                .set(PHONE.NAME, object.getUser())
                .set(PHONE.ISMAN, (long) object.getIsman())
                .set(PHONE.SYSTEM, object.getSystem())
        .execute();
    }

    @Transactional
    public void updatePhoneByLogin(PhoneDAO phoneDAO) {
        dsl.update(PHONE)
                .set(PHONE.DATA_ID, phoneDAO.getId())
                .set(PHONE.TOKEN, phoneDAO.getToken())
               // .set(PHONE.LOGIN, phoneDAO.getLogin())
                .set(PHONE.PASSWORD, phoneDAO.getPassword())
                .set(PHONE.DATE, new Date().getTime())
                .set(PHONE.BIRTH, phoneDAO.getDateOfBirth())
                .set(PHONE.NAME, phoneDAO.getUser())
                .set(PHONE.ISMAN, (long) phoneDAO.getIsman())
                .set(PHONE.SYSTEM, phoneDAO.getSystem()).where(PHONE.LOGIN.like(phoneDAO.getLogin()))
                .execute();
    }
    @Transactional
    public void updatePhoneByToken(PhoneDAO phoneDAO) {
        dsl.update(PHONE)
                .set(PHONE.DATA_ID, phoneDAO.getId())
              //  .set(PHONE.TOKEN, phoneDAO.getToken())
                .set(PHONE.LOGIN, phoneDAO.getLogin())
                .set(PHONE.PASSWORD, phoneDAO.getPassword())
                .set(PHONE.DATE, new Date().getTime())
                .set(PHONE.BIRTH, phoneDAO.getDateOfBirth())
                .set(PHONE.NAME, phoneDAO.getUser())
                .set(PHONE.ISMAN, (long) phoneDAO.getIsman())
                .set(PHONE.SYSTEM, phoneDAO.getSystem()).where(PHONE.TOKEN.like(phoneDAO.getToken()))
                .execute();
    }

    @Transactional
    public Result<Record> getNotificationsForUser(String login) {
        return dsl.select().from(NOTIFICATIONS).where(NOTIFICATIONS.LOGIN.like(login)).fetch();
    }

    @Transactional
    public void persistTicketNotification(String login, TicketEntity ticket) {
        dsl.insertInto(NOTIFICATIONS)
                .set(NOTIFICATIONS.ID, UUID.randomUUID().toString())
                .set(NOTIFICATIONS.LOGIN, login)
                .set(NOTIFICATIONS.TIKETS, ticket.getName())
                .set(NOTIFICATIONS.TIME, ticket.getTime())
                .set(NOTIFICATIONS.STARTNOTIF, 0L)
                .set(NOTIFICATIONS.ENDNOTIF, 0L)
                .set(NOTIFICATIONS.MORNINGSEND, false)
                .set(NOTIFICATIONS.FILMREMINDER, false)
                .set(NOTIFICATIONS.WANTRECIEVE, true)
                .execute();
    }

    @Transactional
    public void clearNotifications() {
        dsl.deleteFrom(NOTIFICATIONS).execute();
    }

    @Transactional
    public Result<Record> getAllNotification() {
        return  dsl.select().from(NOTIFICATIONS).fetch();
    }

    @Transactional
    public Result<Record> getAllNotificationByTime(long start, long finish) {
        return  dsl.select().from(NOTIFICATIONS).
                where(NOTIFICATIONS.TIME.between(start, finish)).fetch();
    }

    @Transactional
    public int  updateMorningInNotification(PhoneDAO phoneDAO) {
        return dsl.update(NOTIFICATIONS)
                .set(NOTIFICATIONS.MORNINGSEND, true)
                .where(NOTIFICATIONS.LOGIN.like(phoneDAO.getLogin()))
                .execute();
    }
}
