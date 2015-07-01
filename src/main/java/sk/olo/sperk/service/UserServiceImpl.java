package sk.olo.sperk.service;

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
                "test1", "test2", "test3", "test4", "test5", "test6", "test7",
                "te1", "te2", "te3", "te4", "te5", "te6", "te7", "te8",
                "ter1", "ter2", "ter3", "ter4", "ter5", "ter6", "ter7", };
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

        createTestGalleryItem(galleryItems, "https://lh5.googleusercontent.com/-2XBq5Kun_gg/UzUtW46AucI/AAAAAAAAHrs/eLSDpdVp8pM/s144/our%252520kids%252520022.JPG");
        createTestGalleryItem(galleryItems, "https://lh5.googleusercontent.com/-FfAXG5WJD6A/UVrhakNwBfI/AAAAAAAABzU/nsaLfFS73_s/s144/P1060056.JPG");
        createTestGalleryItem(galleryItems, "https://lh6.googleusercontent.com/-Ky8fsngq0X0/UVriTybItfI/AAAAAAAAB0c/5QSuqF6Jf00/s144/P1090227.jpg");
        createTestGalleryItem(galleryItems, "https://lh4.googleusercontent.com/-EzX9dpX_isc/UVrhh38qFNI/AAAAAAAABzc/6AH_M4cW748/s144/P1120024.jpg");
        createTestGalleryItem(galleryItems, "https://lh4.googleusercontent.com/-5xaDuJD2CiY/UVriZL71cxI/AAAAAAAAB0k/aeFm8qxgXdw/s144/help%252520016.JPG");
        createTestGalleryItem(galleryItems, "https://lh4.googleusercontent.com/-7RT5I3Qo0Sg/UVrhmHHRpWI/AAAAAAAABzk/g4KqVe5Il_M/s144/kibibi%2525202.JPG");
        createTestGalleryItem(galleryItems, "https://lh5.googleusercontent.com/-6mvtH8ECe4I/UVridf05NTI/AAAAAAAAB0s/rSV0oSlQayk/s144/help%252520022.JPG");
        createTestGalleryItem(galleryItems, "https://lh3.googleusercontent.com/-6rE7TGsZNGw/UVrhtppmO3I/AAAAAAAABzs/pPlMLfPaCfI/s144/P1110243.JPG");
        createTestGalleryItem(galleryItems, "https://lh6.googleusercontent.com/-TJ2O3y4CR68/UVrhzBHrtVI/AAAAAAAABz0/esrk43Z5C30/s144/kibibi.JPG");
        createTestGalleryItem(galleryItems, "https://lh4.googleusercontent.com/-RS_YZx_oPaE/UVrh34L6vKI/AAAAAAAABz8/H3IVFBcz9Gs/s144/lucky%252520a%252520kib.JPG");
        createTestGalleryItem(galleryItems, "https://lh3.googleusercontent.com/--1j6Zi0eWx8/UVriCaSTwnI/AAAAAAAAB0E/3auaosKPJXQ/s144/P1060314.JPG");
        createTestGalleryItem(galleryItems, "https://lh5.googleusercontent.com/-EdZtJYGojig/UVriG8xbe6I/AAAAAAAAB0M/mI2-LrjrCcc/s144/kibibi%2525203.JPG");
        createTestGalleryItem(galleryItems, "https://lh6.googleusercontent.com/-x5b-Pn0bdaU/UVriNmaZbAI/AAAAAAAAB0U/2a992pRCIyA/s144/P1110247.JPG");
        createTestGalleryItem(galleryItems, "https://lh6.googleusercontent.com/-x5b-Pn0bdaU/UVriNmaZbAI/AAAAAAAAB0U/2a992pRCIyA/s144/P1110247.JPG");
        createTestGalleryItem(galleryItems, "https://lh4.googleusercontent.com/-uOPzqqIgDnU/UVrimGRJ0II/AAAAAAAAB00/U8UWDJ54yUg/s144/P1120003.jpg");
        createTestGalleryItem(galleryItems, "https://lh6.googleusercontent.com/-ydYRTOLLj8A/UYtm3MMu1QI/AAAAAAAADGA/_rqHJw4IJU4/s144/P1120521.JPG");
        createTestGalleryItem(galleryItems, "https://lh4.googleusercontent.com/-2AfvN3H2xCI/UYtm-rWaARI/AAAAAAAADGI/adiSMf2AmvI/s144/P1120511.JPG");
        createTestGalleryItem(galleryItems, "https://lh5.googleusercontent.com/-qwhn3W0uaKw/UYtnCBhldUI/AAAAAAAADGQ/wXO93UHsvFE/s144/P1120515.JPG");
        createTestGalleryItem(galleryItems, "https://lh3.googleusercontent.com/-o7dA2VZRzfE/UZHVV_GKGMI/AAAAAAAADNM/nocIlX9gXEo/s144/P1010375.JPG");
        createTestGalleryItem(galleryItems, "https://lh6.googleusercontent.com/-exVicZu1cBo/UZHVbYS_z4I/AAAAAAAADNU/Seova7VIEUc/s144/P1010405.JPG");
        createTestGalleryItem(galleryItems, "https://lh4.googleusercontent.com/-NC9tkTdHxtU/UZMuvNLoQ0I/AAAAAAAADSg/bJ9N1gtABHE/s144/P1010453.jpg");
        createTestGalleryItem(galleryItems, "https://lh5.googleusercontent.com/-TNEjojm-nWA/UeU8dhg5RgI/AAAAAAAAEng/_rfG9NCV5mA/s144/shanzu%252520kids%252520037.JPG");
        createTestGalleryItem(galleryItems, "https://lh4.googleusercontent.com/-UHTpXCNOJ_8/UiLJSTnYDyI/AAAAAAAAFbA/5RvEe_e_ntg/s144/P1020925.JPG");
        createTestGalleryItem(galleryItems, "https://lh3.googleusercontent.com/-6kBUuLQ87Fw/UoOxOygwVdI/AAAAAAAAFz0/H3lNwpQVnug/s144/kibibi.JPG");
        createTestGalleryItem(galleryItems, "https://lh3.googleusercontent.com/-vS8R6j4xn4s/UtwdtLVuitI/AAAAAAAAGdo/DnsKy0PWjzI/s144/KIBIBI%252526LUCKY%252520013.JPG");
        createTestGalleryItem(galleryItems, "https://lh6.googleusercontent.com/-8Sc5Z4_ILls/Utwdv0HSRZI/AAAAAAAAGdw/h7Sd4kg11-M/s144/KIBIBI%252526LUCKY%252520015.JPG");
        createTestGalleryItem(galleryItems, "https://lh4.googleusercontent.com/-uKoOBCF9iyI/Utwd0Pr355I/AAAAAAAAGd4/Wpk6jpDmLcU/s144/KIBIBI%252526LUCKY%252520022.JPG");
        createTestGalleryItem(galleryItems, "https://lh4.googleusercontent.com/-ac7YZwuXKuE/Utwd4gdF6UI/AAAAAAAAGeA/QmtzJCEURWQ/s144/KIBIBI%252526LUCKY%252520017.JPG");
        createTestGalleryItem(galleryItems, "https://lh3.googleusercontent.com/-mXtvJdbMPy4/UuKzJjWuMiI/AAAAAAAAGgE/YNo060ba5Vo/s144/kibibi.JPG");
        createTestGalleryItem(galleryItems, "https://lh5.googleusercontent.com/-dDjKwqS-zAs/UuyiYPBYPJI/AAAAAAAAGts/cs470ryBDlM/s144/100_7590.JPG");
        createTestGalleryItem(galleryItems, "https://lh3.googleusercontent.com/-AdhKNluS_N0/UwZCWa8Xi-I/AAAAAAAAHBo/UagtvIY6zVI/s144/P1030061.jpg");
        createTestGalleryItem(galleryItems, "https://lh6.googleusercontent.com/-d4paNpHi4Uk/UwZCZXMWd1I/AAAAAAAAHBw/5kljEOb0dKk/s144/P1030059.jpg");
        createTestGalleryItem(galleryItems, "https://lh6.googleusercontent.com/-d4paNpHi4Uk/UwZCZXMWd1I/AAAAAAAAHBw/5kljEOb0dKk/s144/P1030059.jpg");
        createTestGalleryItem(galleryItems, "https://lh6.googleusercontent.com/-EKtM-FPCP8I/UwZCcLP7SLI/AAAAAAAAHB4/RE-4MANVMxk/s144/kibib%252520001.JPG");
        createTestGalleryItem(galleryItems, "https://lh3.googleusercontent.com/-K9pMrokTUso/Uxj1DyEv28I/AAAAAAAAHYs/ykqoVuPtYhU/s144/P1030004.JPG");

        testUser.setGalleryItems(galleryItems);
        return testUser;
    }

    private void createTestGalleryItem(ArrayList<GalleryItemModel> galleryItems, String imageUrl) {
        GalleryItemModel item2 = new GalleryItemModel();
        item2.setImageUrl(imageUrl);
        item2.setThumbUrl(imageUrl);
        item2.setGphotoId("aaaaaaaaaaaaa");
        item2.setIndex(5L);
        item2.setCrudAction(GalleryItemModel.ACTION_PUT);
        galleryItems.add(item2);
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
