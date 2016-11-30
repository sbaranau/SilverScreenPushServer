package by.silverscreen.Entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbaranau on 11/28/2016.
 */
public class PushEntity {
    List<String> tokens = new ArrayList<>();
    String title = "";
    String message = "";

    public PushEntity() {
    }

    public PushEntity(List<String> tokens, String title, String message) {
        this.tokens = tokens;
        this.title = title;
        this.message = message;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
