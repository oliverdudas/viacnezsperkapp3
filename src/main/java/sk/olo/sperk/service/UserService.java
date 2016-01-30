package sk.olo.sperk.service;

import sk.olo.sperk.model.UserModel;

import java.util.List;

/**
 * Created by olo on 17. 6. 2015.
 */
public interface UserService {
    UserModel getUserByUsername(String username);

    String putUser(UserModel userModel);

//    UserModel getUser(String key);

//    UserListHolder getUserList(String cursor);

    UserModel getFullUser(String key);

    List<UserModel> getUsers();

    void deleteUser(String key);

//    String putGalleryItem(GalleryItemModel galleryItemModel);

//    String putRole(RoleModel roleModel);

//    String storePerson();

//    List<Entity> loadPersonByAncestor(String id);

    String createTestUser();

//    String putFullUser(UserModel userModel);
}
