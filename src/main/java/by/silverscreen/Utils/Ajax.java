package by.silverscreen.Utils;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbaranau on 11/25/2016.
 */
public class Ajax {

    public static Map<String, Object> emptyResponse(UserDetails user) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("result", "success");
        response.put("user", user);
        return response;
    }

}
