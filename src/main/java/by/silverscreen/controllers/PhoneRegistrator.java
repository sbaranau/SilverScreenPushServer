package by.silverscreen.controllers;

import by.silverscreen.Entities.NotificationEntity;
import by.silverscreen.Entities.PushEntity;
import by.silverscreen.Entities.TokenEntity;
import by.silverscreen.Utils.Ajax;
import by.silverscreen.Utils.RestException;
import by.silverscreen.service.DataService;
import com.sun.security.auth.UserPrincipal;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashSet;
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
    Map<String, Object> getToken (Authentication authentication, @RequestBody TokenEntity data) throws RestException {
        try {
            if (data.getToken() == null) {
                return Ajax.emptyResponse(authentication == null? null : (UserDetails) authentication.getPrincipal());
            } else {
                dataService.persist(data);
            }
            return Ajax.emptyResponse(authentication == null? null : (UserDetails) authentication.getPrincipal());
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    @RequestMapping(value = "/tokens", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity getAllToken (Authentication authentication) throws RestException {
        LOG.trace("get all tokens");
        try {
            if (authentication == null) {
                throw new RestException("403");
            }
            LOG.info(authentication.getPrincipal());
            Set<TokenEntity> tokens = dataService.getAllTokens();
            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity sendPush (Authentication authentication, @RequestBody PushEntity data) throws RestException {
        try {
            LOG.trace("push");
            LOG.info(authentication == null? null : (UserDetails) authentication.getPrincipal());
            if (data == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            } else {
                dataService.sendPush(data);
            }
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @CrossOrigin
    @RequestMapping(value = "/notifications", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity getAllNotifications (Authentication authentication) throws RestException {
        try {
            LOG.info(authentication == null? null : (UserDetails) authentication.getPrincipal());
            Set<NotificationEntity> tokens = dataService.getAllNotifications();
            LOG.trace("get all notifications");
            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

  /*  @RequestMapping(value="/logout", method = RequestMethod.GET)
    public Map<String, Object> logoutPage (Authentication authentication) throws RestException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = ((User) principal);
        if (authentication == null){
            throw new RestException("403");
        }
        return Ajax.successResponse((UserDetails) authentication.getPrincipal());
    }*/

    @CrossOrigin
    @RequestMapping(value="/username", method = RequestMethod.GET)
    public ResponseEntity getUserName (Authentication authentication) throws RestException {
        if (authentication == null){
            return ResponseEntity.ok("anonymous");
        } else {
            return ResponseEntity.ok((UserDetails) authentication.getPrincipal());
        }
    }

    @CrossOrigin
    @RequestMapping(value="/user", method = RequestMethod.GET)
    public ResponseEntity getUser (Authentication authentication) throws RestException {
        if (authentication == null){
            return ResponseEntity.ok("anonymous");
        } else {
            return ResponseEntity.ok((UserDetails) authentication.getPrincipal());
        }
    }
}
