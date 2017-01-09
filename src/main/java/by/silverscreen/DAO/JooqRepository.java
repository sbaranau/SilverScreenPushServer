package by.silverscreen.DAO;

import static by.silverscreen.jooq.tables.Phone.PHONE;

import by.silverscreen.jooq.tables.records.PhoneRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jooq.Record;
import org.jooq.Result;


/**
 * Created by sergey on 1/7/17.
 */
@Service
@Transactional
public class JooqRepository {
    @Autowired
    private DSLContext dsl;


    public Result<Record> getAll(){
        return  dsl.select().from(PHONE).orderBy(PHONE.DATE.desc()).fetch();
    }

    public Record checkName(String name) {
        return dsl.select().from(PHONE).where(PHONE.LOGIN.like(name)).fetchOne();
    }

    public Record checkToken(String token) {
        return dsl.select().from(PHONE).where(PHONE.TOKEN.like(token)).fetchOne();
    }

    public void deleteByLogin(String name) {
        dsl.deleteFrom(PHONE).where(PHONE.LOGIN.like(name)).execute();
    }

    public void deleteByToken(String token) {
        dsl.deleteFrom(PHONE).where(PHONE.TOKEN.like(token)).execute();
    }

    public void persist(PhoneDAO object) {
        dsl.insertInto(PHONE)
                .set(PHONE.DATA_ID, object.getId())
                .set(PHONE.TOKEN, object.getToken())
                .set(PHONE.LOGIN, object.getLogin())
                .set(PHONE.PASSWORD, object.getPassword())
                .set(PHONE.DATE, object.getDate())
                .set(PHONE.BIRTH, object.getDateOfBirth())
                .set(PHONE.NAME, object.getUser())
                .set(PHONE.ISMAN, (long) object.getIsman())
                .set(PHONE.SYSTEM, object.getSystem())
        .execute();
    }

    public void updatePhoneByLogin(PhoneDAO phoneDAO) {
        dsl.update(PHONE)
                .set(PHONE.DATA_ID, phoneDAO.getId())
                .set(PHONE.TOKEN, phoneDAO.getToken())
               // .set(PHONE.LOGIN, phoneDAO.getLogin())
                .set(PHONE.PASSWORD, phoneDAO.getPassword())
                .set(PHONE.DATE, phoneDAO.getDate())
                .set(PHONE.BIRTH, phoneDAO.getDateOfBirth())
                .set(PHONE.NAME, phoneDAO.getUser())
                .set(PHONE.ISMAN, (long) phoneDAO.getIsman())
                .set(PHONE.SYSTEM, phoneDAO.getSystem()).where(PHONE.LOGIN.like(phoneDAO.getLogin()))
                .execute();
    }

    public void updatePhoneByToken(PhoneDAO phoneDAO) {
         dsl.update(PHONE)
                .set(PHONE.DATA_ID, phoneDAO.getId())
              //  .set(PHONE.TOKEN, phoneDAO.getToken())
                .set(PHONE.LOGIN, phoneDAO.getLogin())
                .set(PHONE.PASSWORD, phoneDAO.getPassword())
                .set(PHONE.DATE, phoneDAO.getDate())
                .set(PHONE.BIRTH, phoneDAO.getDateOfBirth())
                .set(PHONE.NAME, phoneDAO.getUser())
                .set(PHONE.ISMAN, (long) phoneDAO.getIsman())
                .set(PHONE.SYSTEM, phoneDAO.getSystem()).where(PHONE.TOKEN.like(phoneDAO.getToken()))
                .execute();
    }
}
