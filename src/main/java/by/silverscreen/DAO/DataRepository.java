package by.silverscreen.DAO;

import by.silverscreen.Entities.TokenEntity;

import java.util.Set;

/**
 * Created by sbaranau on 11/25/2016.
 */
public interface DataRepository<V extends DomainObject> {

    void persist(V object);

    void delete(V object);

    void updatePhoneByLogin(PhoneDAO phoneDAO);

    void updatePhoneByToken(PhoneDAO phoneDAO);

}
