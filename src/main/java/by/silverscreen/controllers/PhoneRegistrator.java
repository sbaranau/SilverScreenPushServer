package by.silverscreen.controllers;

import by.silverscreen.Entities.NotificationEntity;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            }
            return Ajax.emptyResponse();
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @CrossOrigin
    @RequestMapping(value = "/tokens", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> getAllToken (Authentication authentication) throws RestException {
        try {
            LOG.info(authentication == null? null : (UserDetails) authentication.getPrincipal());
            Set<TokenEntity> tokens = dataService.getAllTokens();
            LOG.trace("get all tokens");
            return Ajax.successResponse(tokens);
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> sendPush (Authentication authentication, @RequestBody PushEntity data) throws RestException {
        try {
            LOG.trace("push");
            LOG.info(authentication == null? null : (UserDetails) authentication.getPrincipal());
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

    @Secured({"ROLE_ADMIN"})
    @CrossOrigin
    @RequestMapping(value = "/notifications", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> getAllNotifications (Authentication authentication) throws RestException {
        try {
            LOG.info(authentication == null? null : (UserDetails) authentication.getPrincipal());
            Set<NotificationEntity> tokens = dataService.getAllNotifications();
            LOG.trace("get all notifications");
            return Ajax.successResponse(tokens);
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> logout (Authentication authentication) throws RestException {
        try {
            LOG.info(authentication == null? null : (UserDetails) authentication.getPrincipal());
          //  authentication.
            LOG.trace("get all notifications");
            return Ajax.successResponse("");
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public Map<String, Object> logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return Ajax.successResponse("");//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
}
