package by.silverscreen.service;

import by.silverscreen.Entities.TokenEntity;

import java.util.Set;

/**
 * Created by sbaranau on 11/25/2016.
 */
public interface DataService {

    public boolean persist(TokenEntity tokenEntity);

    public Set<String> getRandomData();

    public boolean checkName(String name);

    public boolean checkToken(String token);
}