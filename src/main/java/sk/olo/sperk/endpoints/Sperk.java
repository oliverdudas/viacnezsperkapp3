package sk.olo.sperk.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import sk.olo.sperk.model.*;
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
            return getFullUser(userByUsername.getIdentifier());
        } else if (userByUsername == null && "test".equals(username) && "test".equals(password)) {
            KeyModel admin = new KeyModel(service.createTestUser());
            return getFullUser(admin.getIdentifier());
        } else {
            throw new RuntimeException("Invalid username, or password!");
        }
    }

    public KeyModel getDummyRequest() {
        return new KeyModel("Dummy request!");
    }

    public KeyModel putUser(UserModel userModel) {
        UserService service = new UserServiceImpl();
        return new KeyModel(service.putUser(userModel));
    }

    @ApiMethod(name="sperk.fulluser", path = "usermodel/fulluser")
    public UserModel getFullUser(@Named("identifier") String identifier) {
        UserService service = new UserServiceImpl();
        return service.getFullUser(identifier);
    }

    public List<UserModel> getUsers() {
        UserService service = new UserServiceImpl();
        return service.getUsers();
    }

    @ApiMethod(name="sperk.deleteuser", path = "usermodel/deleteuser")
    public void deleteUser(@Named("identifier") String identifier) {
        UserService service = new UserServiceImpl();
        service.deleteUser(identifier);
    }

//    public KeyModel storePerson() {
//        UserService service = new UserServiceImpl();
//        return new KeyModel(service.storePerson());
//    }
//
//    public List<Entity> loadPerson(@Named("identifier") String identifier) {
//        UserService service = new UserServiceImpl();
//        return service.loadPersonByAncestor(identifier);
//    }

//    public KeyModel putGalleryItem(GalleryItemModel galleryItemModel) {
//        UserService service = new UserServiceImpl();
//        return new KeyModel(service.putGalleryItem(galleryItemModel));
//    }

//    public KeyModel putRole(RoleModel roleModel) {
//        UserService service = new UserServiceImpl();
//        return new KeyModel(service.putRole(roleModel));
//    }
//
//    public UserModel getUser(@Named("identifier") String identifier) {
//        UserService service = new UserServiceImpl();
//        return service.getUser(identifier);
//    }
//
//    public UserListHolder getUserList(@Named("cursor") String cursor) {
//        UserService service = new UserServiceImpl();
//        return service.getUserList(cursor);
//    }

}
