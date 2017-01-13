package by.silverscreen.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by siarheybaranau on 1/24/15.
 */
public class Cookie {

    private Map<String, String> cookies;
    private static Cookie cookie = null;

    private Cookie() {
        cookies = new HashMap<String, String>();
        cookies.put("SelectedTheatreAreaID","1006");
        cookies.put("_gat","1");
    }

    public static Cookie getInstance() {
        if (cookie == null) {
            cookie = new Cookie();
        }
        return cookie;
    }

    public void updateCookie(Map<String, String> map) {
        for (String key : map.keySet()) {
            cookies.put(key, map.get(key));
        }
    }


    public void updateCookie(String name, String value) {
        cookies.put(name, value);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public void clearCookie() {
        cookies = new HashMap<String, String>();
    }

    public void setCookie(String s, String s1) {
        cookies.put(s, s1);
    }

    public void delete(String userAuthentication) {
        cookies.remove(userAuthentication);
    }

}
