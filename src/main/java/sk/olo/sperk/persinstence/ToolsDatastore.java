package sk.olo.sperk.persinstence;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.search.query.QueryParserFactory;
import sk.olo.sperk.model.GalleryItemModel;
import sk.olo.sperk.model.RoleModel;
import sk.olo.sperk.model.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by olo on 17. 6. 2015.
 */
public class ToolsDatastore implements ToolsPersistence {

    @Override
    public UserModel getUserByUsername(String username) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter propertyFilter = new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, username);
        Query query = new Query(UserModel.KIND).setFilter(propertyFilter);

        Entity entity = datastore.prepare(query).asSingleEntity();
        return entity != null ? UserModel.createModel(entity): null;
    }

    @Override
    public String putUser(UserModel userModel) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Transaction transaction = datastore.beginTransaction();

        Key newKey = generateKey(UserModel.KIND, 1);
        Entity entity = userModel.toEntity(newKey);
        datastore.put(entity);

        transaction.commit();

        return KeyFactory.keyToString(newKey);
    }

    @Override
    public UserModel getUser(String key) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        try {
            return UserModel.createModel(datastore.get(KeyFactory.stringToKey(key)));
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserModel getFullUser(String key) {
//        SELECT * WHERE ANCESTOR IS KEY('Person', 'amym')
//        SELECT * WHERE ANCESTOR IS KEY('sd5f4g65sdf2g1s3dfg')
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query(KeyFactory.stringToKey(key));
        PreparedQuery preparedQuery = datastore.prepare(query);

        Iterable<Entity> iterable = preparedQuery.asIterable();
        return buildModel(iterable);
    }

    private UserModel buildModel(Iterable<Entity> iterable) {
        UserModel userModel = null;
        List<RoleModel> roles = new ArrayList<>();
        List<GalleryItemModel> galleryItems = new ArrayList<>();
        for(Entity entity : iterable) {
            if (UserModel.KIND.equals(entity.getKind())) {
                userModel = UserModel.createModel(entity);
            } else if (GalleryItemModel.KIND.equals(entity.getKind())) {
                galleryItems.add(GalleryItemModel.createModel(entity));
            } else if (RoleModel.KIND.equals(entity.getKind())) {
                roles.add(RoleModel.createModel(entity));
            }
        }

        if (userModel != null) {
            userModel.setGalleryItems(galleryItems);
            userModel.setRoles(roles);
        }

        return userModel;
    }

    @Override
    public List<UserModel> getUsers() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query(UserModel.KIND);

        PreparedQuery pq = datastore.prepare(query);

        List<UserModel> users = new ArrayList<UserModel>();
        for(Entity entity : pq.asIterable()) {
            users.add(UserModel.createModel(entity));
        }
        return users;
    }

    private Key generateKey(String kind, long count) {
        return DatastoreServiceFactory.getDatastoreService().allocateIds(kind, count).getStart();
    }

    private Key generateKey(Key parent, String kind, long count) {
        return DatastoreServiceFactory.getDatastoreService().allocateIds(parent, kind, count).getStart();
    }

    @Override
    public String putGalleryItem(GalleryItemModel galleryItemModel) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity entity = galleryItemModel.toEntity();
        datastore.put(entity);
        return KeyFactory.keyToString(entity.getKey());
    }

    @Override
    public String putRole(RoleModel roleModel) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity entity = roleModel.toEntity();
        datastore.put(entity);
        return KeyFactory.keyToString(entity.getKey());
    }

    @Override
    public String storePersonWithImages() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity tom = new Entity("Person", "Tom");
        Key tomKey = tom.getKey();
        datastore.put(tom);


        Entity weddingPhoto = new Entity("Photo", tomKey);
        weddingPhoto.setProperty("imageURL", "http://domain.com/some/path/to/wedding_photo.jpg");

        Entity babyPhoto = new Entity("Photo", tomKey);
        babyPhoto.setProperty("imageURL", "http://domain.com/some/path/to/baby_photo.jpg");

        Entity dancePhoto = new Entity("Photo", tomKey);
        dancePhoto.setProperty("imageURL", "http://domain.com/some/path/to/dance_photo.jpg");

        Entity campingPhoto = new Entity("Photo");
        campingPhoto.setProperty("imageURL", "http://domain.com/some/path/to/camping_photo.jpg");

        List<Entity> photoList = Arrays.asList(weddingPhoto, babyPhoto, dancePhoto, campingPhoto);
        datastore.put(photoList);

        return KeyFactory.keyToString(tomKey);
    }

    @Override
    public List<Entity> loadPersonGroupByAncestor(String key) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key tomKey = KeyFactory.stringToKey(key);
        Query photoQuery = new Query("Photo").setAncestor(tomKey);

        // This returns weddingPhoto, babyPhoto, and dancePhoto,
        // but not campingPhoto, because tom is not an ancestor
        return datastore.prepare(photoQuery).asList(FetchOptions.Builder.withDefaults());
    }

}
