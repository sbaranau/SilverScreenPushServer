package by.silverscreen.Utils;

import by.silverscreen.Entities.PushEntity;
import by.silverscreen.pushraven.FcmResponse;
import by.silverscreen.pushraven.Notification;
import by.silverscreen.pushraven.Pushraven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by sbaranau on 11/25/2016.
 */
@Component
public class Pusher {

    private String serverkey;
    private String serverkeyIos;
    @Autowired
    public Pusher(@Value("${fcm.serverkey}") String serverkey, @Value("${fcm.serverkeyIos}") String serverkeyIos) {
        this.serverkey = serverkey;
        System.out.println("================== " + serverkey + "================== ");
        this.serverkeyIos = serverkeyIos;
        System.out.println("================== " + serverkeyIos + "================== ");
    }
    public void sendAndr(PushEntity pushEntity) {

        Pushraven.setKey(serverkey);

        // create Notification object
        Notification raven = new Notification();
        raven.restricted_package_name("com.simpity.silverscreen");
        // build raven message using the builder pattern
        send(pushEntity, raven);
    }

    public void sendIos(PushEntity pushEntity) {
        Pushraven.setKey(serverkeyIos);
        Notification raven = new Notification();
        raven.restricted_package_name("com.simpity.SilverScreenCinema");
        send(pushEntity, raven);


    }

    private void send(PushEntity pushEntity, Notification raven) {
        // build raven message using the builder pattern
        if (pushEntity.getTokens().size() == 1) {
            raven.to(pushEntity.getTokens().get(0));
        } else {
            raven.addAllMulticasts(pushEntity.getTokens());
        }
        raven
                .time_to_live(100)
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
