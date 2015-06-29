package sk.olo.sperk.persinstence;

import sk.olo.sperk.model.UserModel;

import java.util.List;

/**
 * Created by olo on 17. 6. 2015.
 */
public interface ToolsPersistence {
    UserModel getUserByUsername(String username);

    String putUser(UserModel userModel);

//    UserModel getUser(String key);

    UserModel getFullUser(String key);

    List<UserModel> getUsers();

//    UserListHolder getUserList(String cursor);

//    String putGalleryItem(GalleryItemModel galleryItemModel);

//    String putRole(RoleModel roleModel);

//    String storePersonWithImages();

//    List<Entity> loadPersonGroupByAncestor(String id);

//    String putFullUser(UserModel userModel);
}
