package by.silverscreen.service;

import by.silverscreen.DAO.DataRepository;
import by.silverscreen.DAO.PhoneEntity;
import by.silverscreen.Entities.TokenEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

/**
 * Created by sbaranau on 11/25/2016.
 */
@Service("dataService")
public class DataServiceImpl implements DataService {

    private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);

    @Autowired
    @Qualifier("dataRespitory")
    private DataRepository dataRepository;

    @Override
    public boolean persist(TokenEntity tokenEntity) {
        try {
            dataRepository.persist(new PhoneEntity(UUID.randomUUID(), tokenEntity.getToken(), replaceNull(tokenEntity.getLogin()),
                    replaceNull(tokenEntity.getPassword())));
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
    public boolean checkName(String name) {
        return false;
    }

    @Override
    public boolean checkToken(String token) {
        return false;
    }

    private String replaceNull(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}
