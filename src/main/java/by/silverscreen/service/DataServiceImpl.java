package by.silverscreen.service;

import by.silverscreen.DAO.DataRepository;
import by.silverscreen.DAO.PhoneDAO;
import by.silverscreen.Entities.TokenEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by sbaranau on 11/25/2016.
 */
@Service("dataService")
public class DataServiceImpl implements DataService {

    private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);

    @Autowired
    @Qualifier("dataRespitory")
    private DataRepository dataRepository;

    /**
     *  call only if change token on mobile called
     *  If user install app and use it with auth - update token(password)
     *  if user install app and use it without auth - save token if it not exist
     */
    @Override
    public boolean persist(TokenEntity tokenEntity) {
        try {
            if (tokenEntity.getLogin() != null && tokenEntity.getLogin().length() > 0) {
                TokenEntity existingTokenEntity = checkName(tokenEntity.getLogin());
                if (existingTokenEntity != null && (!existingTokenEntity.getToken().equals(tokenEntity.getToken()) ||
                    !existingTokenEntity.getPassword().equals(tokenEntity.getPassword()))) {
                    dataRepository.updatePhoneByLogin(new PhoneDAO(tokenEntity));
                } else {
                    dataRepository.persist(new PhoneDAO(tokenEntity));
                }
            } else {
                if (checkToken(tokenEntity.getToken()) == null) {
                    dataRepository.persist(new PhoneDAO(tokenEntity));
                } else {
                    dataRepository.updatePhoneByToken(new PhoneDAO(tokenEntity));
                }
            }
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

    private String replaceNull(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}
