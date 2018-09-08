package sk.olo.sperk.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import endpoints.repackaged.com.fasterxml.jackson.databind.DeserializationFeature;
import endpoints.repackaged.com.fasterxml.jackson.databind.ObjectMapper;
import sk.olo.sperk.exception.SperkOAuthRequestException;
import sk.olo.sperk.model.GoogleUser;
import sk.olo.sperk.model.KeyModel;
import sk.olo.sperk.model.UserModel;
import sk.olo.sperk.service.UserService;
import sk.olo.sperk.service.UserServiceImpl;
import sk.olo.sperk.util.Constants;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by olo on 17. 6. 2015.
 */
@Api(
        name = "viacnezsperkAPI",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {
                Constants.WEB_CLIENT_ID,
                Constants.API_EXPLORER_CLIENT_ID,
                Constants.ANDROID_CLIENT_ID,
                Constants.IOS_CLIENT_ID
        },
        audiences = {
                Constants.ANDROID_AUDIENCE
        }
//        ,
//        namespace = @ApiNamespace(ownerDomain = "deti.sperk.sk",
//                ownerName = "deti.sperk.sk",
//                packagePath = "")
//        ,
//        authenticators = {
//                SperkAuthenticator.class
//        }
)
public class Sperk {

    @ApiMethod(
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public UserModel login(@Named("username") String username, @Named("password") String password) {
        UserService service = new UserServiceImpl();
        UserModel userByUsername = service.getUserByUsername(username);
        if (userByUsername != null && password.equals(userByUsername.getPassword())) {
            return getFullUser(userByUsername.getIdentifier());
        } else if (userByUsername == null && "test".equals(username) && "test".equals(password)) {
            KeyModel admin = new KeyModel(service.createTestUser());
            return getFullUser(admin.getIdentifier());
        } else {
            throw new RuntimeException("Invalid username, or password!");
        }
    }

    public GoogleUser getAuthorizedUser(HttpServletRequest request) throws SperkOAuthRequestException {
        return authorizeUser(request);
    }

    public KeyModel getDummyRequest() {
        return new KeyModel("Dummy request!");
    }

    @ApiMethod(
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public KeyModel putUser(UserModel userModel, HttpServletRequest request) throws SperkOAuthRequestException {
        authorizeUser(request);
        UserService service = new UserServiceImpl();
        return new KeyModel(service.putUser(userModel));
    }

    @ApiMethod(
            name = "sperk.fulluser",
            path = "usermodel/fulluser"
    )
    public UserModel getFullUser(@Named("identifier") String identifier, HttpServletRequest request) throws SperkOAuthRequestException {
        authorizeUser(request);
        return getFullUser(identifier);
    }

    public List<UserModel> getUsers(HttpServletRequest request) throws SperkOAuthRequestException {
        authorizeUser(request);
        UserService service = new UserServiceImpl();
        return service.getUsers();
    }

    @ApiMethod(
            name = "sperk.deleteuser",
            path = "usermodel/deleteuser"
    )
    public void deleteUser(@Named("identifier") String identifier, HttpServletRequest request) throws SperkOAuthRequestException {
        authorizeUser(request);
        UserService service = new UserServiceImpl();
        service.deleteUser(identifier);
    }

    private UserModel getFullUser(String identifier) {
        UserService service = new UserServiceImpl();
        return service.getFullUser(identifier);
    }

    private GoogleUser authorizeUser(HttpServletRequest request) throws SperkOAuthRequestException {
        GoogleUser user;
        try {
            user = accessUser(request);
        } catch (IOException e) {
            throw new SperkOAuthRequestException("AUTHORIZATION_FAILED");
        }

        if (user != null && user.getEmail() != null && user.getEmail().endsWith("viacnezsperk.sk")) {
            return user;
        } else {
            throw new SperkOAuthRequestException("NOT_AUTHORIZED");
        }

    }

    private GoogleUser accessUser(HttpServletRequest request) throws IOException, SperkOAuthRequestException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            String accessToken = authorization.split(" ")[1];
            String userInfoJson = fetchUserInfo(accessToken);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(userInfoJson, GoogleUser.class);
        } else {
            throw new SperkOAuthRequestException("NOT_AUTHORIZED");
        }
    }

    private String fetchUserInfo(String accessToken) throws IOException {
        URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();
        HTTPRequest httpRequest = new HTTPRequest(new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken));
        HTTPResponse httpResponse = urlFetchService.fetch(httpRequest);
        return new String(httpResponse.getContent(), StandardCharsets.UTF_8);
    }

}
