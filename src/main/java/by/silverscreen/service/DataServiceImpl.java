package by.silverscreen.service;

import by.silverscreen.DAO.DataRepository;
import by.silverscreen.DAO.PhoneDAO;
import by.silverscreen.Entities.PushEntity;
import by.silverscreen.Entities.TokenEntity;
import by.silverscreen.Utils.Pusher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by sbaranau on 11/25/2016.
 */
@Service("dataService")
public class DataServiceImpl implements DataService {

    private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);

    @Autowired
    @Qualifier("dataRespitory")
    private DataRepository dataRepository;

    @Autowired
    private Pusher pusher;

    /**
     *  call only if change token on mobile called
     *  If user install push and use it with auth - update token(password)
     *  if user install push and use it without auth - save token if it not exist
     */
    @Override
    public boolean persist(TokenEntity tokenEntity) {
        try {
            if (tokenEntity.getLogin() != null && tokenEntity.getLogin().length() > 0) {
                TokenEntity existingTokenEntity = checkName(tokenEntity.getLogin());
                PhoneDAO phoneDAO = new PhoneDAO(tokenEntity);
                if (existingTokenEntity != null && (!existingTokenEntity.getToken().equals(tokenEntity.getToken()) ||
                        !existingTokenEntity.getPassword().equals(tokenEntity.getPassword()))) {
                    dataRepository.updatePhoneByLogin(phoneDAO);
                } else if (existingTokenEntity == null && checkToken(tokenEntity.getToken()) != null){
                    dataRepository.updatePhoneByToken(phoneDAO);
                } else if (existingTokenEntity == null)  {
                    dataRepository.persist(phoneDAO);
                }
            } else {
                tokenEntity.setUser("Anonymous");
                if (checkToken(tokenEntity.getToken()) == null) {
                    PhoneDAO phoneDAO = new PhoneDAO(tokenEntity);
                    dataRepository.persist(phoneDAO);
                } else {
                    PhoneDAO phoneDAO = new PhoneDAO(tokenEntity);
                    dataRepository.updatePhoneByToken(phoneDAO);
                }
            }
           /* if (tokenEntity.getLogin() != null && tokenEntity.getLogin().length() > 0 &&
                    tokenEntity.getPassword() != null && tokenEntity.getPassword().length() > 0) {
                dataRepository.updateUser(tokenEntity);
            }*/
            return true;
        } catch (Exception e) {
            LOG.error("ERROR SAVING DATA: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Set<String> getRandomData() {
        return dataRepository.getRandomData();
    }

    @Override
    public TokenEntity checkName(String name) {
        PhoneDAO phoneDAO = dataRepository.checkName(name);
        if (phoneDAO == null) {
            return null;
        }
        return new TokenEntity(phoneDAO);
    }

    @Override
    public TokenEntity checkToken(String token) {
        PhoneDAO phoneDAO = dataRepository.checkToken(token);
        if (phoneDAO == null) {
            return null;
        }
        return new TokenEntity(phoneDAO);
    }

    @Override
    public Set<TokenEntity> getAllTokens() {
        Set<TokenEntity> result = new HashSet<>();
        for (Object dao: dataRepository.getAllTokens() )  {
            result.add(new TokenEntity((PhoneDAO) dao));
        }
        return result;
    }

    @Override
    public boolean sendPush(PushEntity pushEntity) {
        //https://github.com/Raudius/Pushraven
        if (pushEntity.getTokens().size() == 0) {
            return false;
        }
        PushEntity andPush = new PushEntity();
        andPush.setMessage(pushEntity.getMessage());
        andPush.setTitle(pushEntity.getTitle());
        PushEntity iosPush = new PushEntity();
        iosPush.setMessage(pushEntity.getMessage());
        iosPush.setTitle(pushEntity.getTitle());

        Predicate<PhoneDAO> isNotNull = (s2) -> s2 != null;

        BiFunction<List<String>,String, List<String>> getPhoneDAO = (tokens, type) -> {
            Predicate<PhoneDAO> isIos = ((s) -> (s.getSystem().contains(type)));
            return  tokens.stream().filter(token -> isNotNull.and(isIos).test(dataRepository.checkToken(token))).collect(Collectors.toList());
        };

        List<String> iosTokens = getPhoneDAO.apply(pushEntity.getTokens(), "ios");
        List<String> andTokens = getPhoneDAO.apply(pushEntity.getTokens(), "android");
        if (andTokens.size() > 0) {
            andPush.setTokens(andTokens);
            pusher.sendAndr(andPush);
        }
        if (iosTokens.size() > 0) {
            iosPush.setTokens(iosTokens);
            pusher.sendIos(iosPush);
        }
        // pusher.send(pushEntity);
        return true;
    }

    private String replaceNull(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}
/*
 PushEntity andPush = new PushEntity();
        andPush.setMessage(pushEntity.getMessage());
        andPush.setTitle(pushEntity.getTitle());
        PushEntity iosPush = new PushEntity();
        iosPush.setMessage(pushEntity.getMessage());
        iosPush.setTitle(pushEntity.getTitle());

        Predicate<PhoneDAO> isNotNull = (s2) -> s2 != null;

        BiFunction<List<String>,String, List<String>> getPhoneDAO = (tokens, type) -> {
            Predicate<PhoneDAO> isIos = ((s) -> (s.getSystem().contains(type)));
            return  tokens.stream().filter(token -> isNotNull.and(isIos).test(dataRepository.checkToken(token))).collect(Collectors.toList());
        };

        List<String> iosTokens = getPhoneDAO.apply(pushEntity.getTokens(), "ios");
        List<String> andTokens = getPhoneDAO.apply(pushEntity.getTokens(), "android");
        if (andTokens.size() > 0) {
            andPush.setTokens(andTokens);
            pusher.sendAndr(andPush);
        }
        if (iosTokens.size() > 0) {
            iosPush.setTokens(iosTokens);
            pusher.sendIos(iosPush);
        }
       // pusher.send(pushEntity);
*/
