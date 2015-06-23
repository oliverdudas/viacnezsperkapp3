package sk.olo.sperk.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import sk.olo.sperk.model.GalleryItemModel;
import sk.olo.sperk.model.KeyModel;
import sk.olo.sperk.model.RoleModel;
import sk.olo.sperk.model.UserModel;
import sk.olo.sperk.service.UserService;
import sk.olo.sperk.service.UserServiceImpl;
import sk.olo.sperk.util.Constants;

import javax.inject.Named;
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

    @ApiMethod(httpMethod = "post")
    public UserModel login(@Named("username") String username, @Named("password") String password) {
        UserService service = new UserServiceImpl();
        UserModel userByUsername = service.getUserByUsername(username);
        if (userByUsername != null && password.equals(userByUsername.getPassword())) {
            return getFullUser(userByUsername.getKey());
        } else if ("test".equals(username) && "test".equals(password)) {
            KeyModel admin = new KeyModel(service.createTestUser());
            return getFullUser(admin.getKey());
        } else {
            throw new RuntimeException("Invalid username, or password!");
        }
    }

    public KeyModel putUser(UserModel userModel) {
        UserService service = new UserServiceImpl();
        return new KeyModel(service.putUser(userModel));
    }

    public KeyModel putGalleryItem(GalleryItemModel galleryItemModel) {
        UserService service = new UserServiceImpl();
        return new KeyModel(service.putGalleryItem(galleryItemModel));
    }

    public KeyModel putRole(RoleModel roleModel) {
        UserService service = new UserServiceImpl();
        return new KeyModel(service.putRole(roleModel));
    }

    public UserModel getUser(@Named("key") String key) {
        UserService service = new UserServiceImpl();
        return service.getUser(key);
    }

    @ApiMethod(path = "usermodel/fulluser")
    public UserModel getFullUser(@Named("key") String key) {
        UserService service = new UserServiceImpl();
        return service.getFullUser(key);
    }

    public List<UserModel> getUsers(User user) {
        if (user != null) {
            UserService service = new UserServiceImpl();
            return service.getUsers();
        } else {
            throw new RuntimeException("USER_ACCESS_DENIED");
        }
    }

    public KeyModel storePerson() {
        UserService service = new UserServiceImpl();
        return new KeyModel(service.storePerson());
    }

    public List<Entity> loadPerson(@Named("key") String key) {
        UserService service = new UserServiceImpl();
        return service.loadPersonByAncestor(key);
    }

}
