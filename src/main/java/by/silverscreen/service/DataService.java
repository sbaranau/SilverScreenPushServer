package by.silverscreen.service;

import by.silverscreen.Entities.TokenEntity;

import java.util.Set;

/**
 * Created by sbaranau on 11/25/2016.
 */
public interface DataService {

    boolean persist(TokenEntity tokenEntity);

    Set<String> getRandomData();

    TokenEntity checkName(String name);

    TokenEntity checkToken(String token);
}