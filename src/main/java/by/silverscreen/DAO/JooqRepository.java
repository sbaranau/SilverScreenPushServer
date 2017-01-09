package by.silverscreen.DAO;

import static by.silverscreen.jooq.tables.Phone.PHONE;
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




}
