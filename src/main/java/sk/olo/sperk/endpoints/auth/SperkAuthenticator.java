package sk.olo.sperk.endpoints.auth;

import com.google.api.server.spi.auth.GoogleAuth;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Authenticator;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.repackaged.org.codehaus.jackson.map.DeserializationConfig;
import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;
import sk.olo.sperk.model.GoogleUser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * Created by olo on 18. 6. 2015.
 */
public class SperkAuthenticator implements Authenticator {
    @Override
    public User authenticate(HttpServletRequest httpServletRequest) {

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        String userInfoJson;
        try {
            userInfoJson = fetchUserInfo(accessToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        GoogleUser user;
        try {
            user = objectMapper.readValue(userInfoJson, GoogleUser.class);

            com.google.appengine.api.users.User appEngineUser = new com.google.appengine.api.users.User(user.getEmail(), user.getHd(), user.getId());
            httpServletRequest.setAttribute("endpoints:Authenticated-AppEngine-User", appEngineUser);

            return new User(user.getId(), user.getEmail());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String fetchUserInfo(String accessToken) throws IOException {
        URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();
        HTTPRequest httpRequest = new HTTPRequest(new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken));
        HTTPResponse httpResponse = urlFetchService.fetch(httpRequest);
        return new String(httpResponse.getContent(), StandardCharsets.UTF_8);
    }
}
