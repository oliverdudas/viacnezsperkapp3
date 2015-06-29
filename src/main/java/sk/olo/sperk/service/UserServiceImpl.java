package sk.olo.sperk.service;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import sk.olo.sperk.model.*;
import sk.olo.sperk.persinstence.ToolsDatastore;
import sk.olo.sperk.persinstence.ToolsPersistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by olo on 17. 6. 2015.
 */
public class UserServiceImpl implements UserService {

    @Override
    public UserModel getUserByUsername(String username) {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.getUserByUsername(username);
    }

    @Override
    public String putUser(UserModel userModel) {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.putUser(userModel);
    }

    @Override
    public UserModel getFullUser(String key) {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.getFullUser(key);
    }

    @Override
    public List<UserModel> getUsers() {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.getUsers();
    }

    public void createTestUsers() {
        String[] usernames = new String[]{
                "test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9", "test10", "test11", "test12", "test13", "test14", "test15",
                "te1", "te2", "te3", "te4", "te5", "te6", "te7", "te8", "te9", "te10", "te11", "te12", "te13", "te14", "te15",
                "ter1", "ter2", "ter3", "ter4", "ter5", "ter6", "ter7", "ter8", "ter9", "ter10", "ter11", "ter12", "ter13", "ter14", "ter15"};
        for (String username : usernames) {
            UserModel testUser = createTestUser(username, username);
            putUser(testUser);
        }
    }

    @Override
    public String createTestUser() {
        createTestUsers();

        UserModel testUser = createTestUser("test", "test");
        return putUser(testUser);
    }

    private UserModel createTestUser(String username, String password) {
        UserModel testUser = new UserModel();
        testUser.setUsername(username);
        testUser.setPassword(password);
        testUser.setContent(new Text("BLaaaaaaaaaaaaaaaaaaaaa laaaaaaaaaaa laaaaaaaaaaaa"));
        testUser.setAge(12L);
        testUser.setBornYear(2003L);
        testUser.setCreated(new Date());
        testUser.setCreatedBy("Admin");
        testUser.setFirstname("Lola");
        testUser.setLastname("Boba");
        testUser.setMainURL("https://lh4.googleusercontent.com/-tAdw_fwOze8/Utf1nVevFwI/AAAAAAAAGXs/-ksQSqXpKh0/amina%25252C%2525207y.JPG");
        testUser.setModified(new Date());
        testUser.setModifiedBy("Admin");
        testUser.setResidence("Mtopa");
        testUser.setSocialInfo("social info");

        ArrayList<GalleryItemModel> galleryItems = new ArrayList<GalleryItemModel>();

        GalleryItemModel item1 = new GalleryItemModel();
        item1.setImageUrl("https://lh3.googleusercontent.com/-vf-SoeYz_e0/Ut5B9oDy_pI/AAAAAAAAGe8/SqoUY0REeho/amina.JPG");
        item1.setThumbUrl("https://lh3.googleusercontent.com/-vf-SoeYz_e0/Ut5B9oDy_pI/AAAAAAAAGe8/SqoUY0REeho/s144/amina.JPG");
        item1.setGphotoId("aaaaaaaaaaaaa");
        item1.setIndex(5L);
        item1.setCrudAction(GalleryItemModel.ACTION_PUT);
        galleryItems.add(item1);

        GalleryItemModel item2 = new GalleryItemModel();
        item2.setImageUrl("https://lh5.googleusercontent.com/-2XBq5Kun_gg/UzUtW46AucI/AAAAAAAAHrs/eLSDpdVp8pM/our%252520kids%252520022.JPG");
        item2.setThumbUrl("https://lh5.googleusercontent.com/-2XBq5Kun_gg/UzUtW46AucI/AAAAAAAAHrs/eLSDpdVp8pM/s144/our%252520kids%252520022.JPG");
        item2.setGphotoId("aaaaaaaaaaaaa");
        item2.setIndex(5L);
        item2.setCrudAction(GalleryItemModel.ACTION_PUT);
        galleryItems.add(item2);

        testUser.setGalleryItems(galleryItems);
        return testUser;
    }

//    @Override
//    public UserModel getUser(String key) {
//        ToolsPersistence persistence = new ToolsDatastore();
//        return persistence.getUser(key);
//    }

//    @Override
//    public UserListHolder getUserList(String cursor) {
//        ToolsPersistence persistence = new ToolsDatastore();
//        return persistence.getUserList(cursor);
//    }

//    @Override
//    public String putFullUser(UserModel userModel) {
//        ToolsPersistence persistence = new ToolsDatastore();
//        return persistence.putFullUser(userModel);
//    }

//    @Override
//    public String putGalleryItem(GalleryItemModel galleryItemModel) {
//        ToolsPersistence persistence = new ToolsDatastore();
//        return persistence.putGalleryItem(galleryItemModel);
//    }

//    @Override
//    public String putRole(RoleModel roleModel) {
//        ToolsPersistence persistence = new ToolsDatastore();
//        return persistence.putRole(roleModel);
//    }
//
//    @Override
//    public String storePerson() {
//        ToolsPersistence persistence = new ToolsDatastore();
//        return persistence.storePersonWithImages();
//    }
//
//    @Override
//    public List<Entity> loadPersonByAncestor(String key) {
//        ToolsPersistence persistence = new ToolsDatastore();
//        return persistence.loadPersonGroupByAncestor(key);
//    }
}
