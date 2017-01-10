package by.silverscreen.controllers;

import by.silverscreen.Entities.PushEntity;
import by.silverscreen.Entities.TokenEntity;
import by.silverscreen.Utils.Ajax;
import by.silverscreen.Utils.RestException;
import by.silverscreen.service.DataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * Created by sbaranau on 11/25/2016.
 */
@RequestMapping("api")
@Controller
public class PhoneRegistrator {

    @Autowired
    @Qualifier("dataService")
    private DataService dataService;


    private static final Logger LOG = Logger.getLogger(PhoneRegistrator.class);
    @CrossOrigin
    @RequestMapping(value = "/token", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Object> getToken (@RequestBody TokenEntity data) throws RestException {
        try {
            if (data.getToken() == null) {
                return Ajax.emptyResponse();
            } else {
                dataService.persist(data);
                //TODO working code pusher.send(data.getToken());
            }
            return Ajax.emptyResponse();
        } catch (Exception e) {
            throw new RestException(e);
        }
    }
    @Secured({"ADMIN","USER"})
    @CrossOrigin
    @RequestMapping(value = "/tokens", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> getAllToken () throws RestException {
        try {

            Set<TokenEntity> tokens = dataService.getAllTokens();
            LOG.trace("get all tokens");

            return Ajax.successResponse(tokens);
        } catch (Exception e) {
            throw new RestException(e);
        }
    }
    @Secured("ADMIN")
    @CrossOrigin
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> sendPush (@RequestBody PushEntity data) throws RestException {
        try {
            LOG.trace("push");
            if (data == null) {
                return Ajax.emptyResponse();
            } else {
                dataService.sendPush(data);
            }
            return Ajax.emptyResponse();
        } catch (Exception e) {
            throw new RestException(e);
        }
    }
}
