package by.silverscreen.controllers;

import by.silverscreen.Entities.TokenEntity;
import by.silverscreen.Pusher;
import by.silverscreen.Utils.Ajax;
import by.silverscreen.Utils.RestException;
import by.silverscreen.service.DataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by sbaranau on 11/25/2016.
 */
@Controller
public class PhoneRegistrator {

    @Autowired
    @Qualifier("dataService")
    private DataService dataService;

    @Autowired
    private Pusher pusher;

    private static final Logger LOG = Logger.getLogger(PhoneRegistrator.class);
    @RequestMapping(value = "/token", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> getToken (@RequestBody TokenEntity data) throws RestException {
        try {
            LOG.trace("test");
            if (data.getToken() == null) {
                return Ajax.emptyResponse();
            } else {
                pusher.sendToOne(data.getToken());
            }
            //dataService.persist(data);
            return Ajax.emptyResponse();
        } catch (Exception e) {
            throw new RestException(e);
        }
        //return "Hello World!";
    }
}
