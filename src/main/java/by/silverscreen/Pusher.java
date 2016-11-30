package by.silverscreen;

import by.silverscreen.Entities.PushEntity;
import by.silverscreen.pushraven.FcmResponse;
import by.silverscreen.pushraven.Notification;
import by.silverscreen.pushraven.Pushraven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by sbaranau on 11/25/2016.
 */
@Component
public class Pusher {

    private String serverkey;

    @Autowired
    public Pusher(@Value("${fcm.serverkey}") String serverkey) {
        this.serverkey = serverkey;
        System.out.println("================== " + serverkey + "================== ");
    }
    public void send(PushEntity pushEntity) {

        Pushraven.setKey(serverkey);


        // create Notification object
        Notification raven = new Notification();

        if (pushEntity.getTokens().size() == 1) {
            raven.to(pushEntity.getTokens().get(0));
        } else {
            raven.addAllMulticasts(pushEntity.getTokens());
        }

      /*  HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("Hello", "World!");
        data.put("Marco", "Polo");
        data.put("Foo", "Bar");*/

        // build raven message using the builder pattern
        raven
                .time_to_live(100)
                .restricted_package_name("com.simpity.silverscreen")
               /* .data(data)*/
                .dry_run(false)
                .title(pushEntity.getTitle())
                .body(pushEntity.getMessage())
                .color("#ff0000");




        // push the raven message
      //  FcmResponse response = Pushraven.push(raven);

        // alternatively set static notification first.
        Pushraven.setNotification(raven);
        FcmResponse response = Pushraven.push();
        raven.clear();
        // prints response code and message
        System.out.println(response);
    }

    public Pusher() {
    }
}
