package sk.olo.sperk.endpoints.auth;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Authenticator;
import com.google.api.server.spi.request.Attribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by olo on 18. 6. 2015.
 */
public class SperkAuthenticator implements Authenticator {
    @Override
    public User authenticate(HttpServletRequest httpServletRequest) {
        Attribute attr = Attribute.from(httpServletRequest);
        String mail = "testorius@gmail.com";
        attr.set("endpoints:Authenticated-AppEngine-User", new com.google.appengine.api.users.User(mail, ""));
        return new User(mail, "");
    }
}
