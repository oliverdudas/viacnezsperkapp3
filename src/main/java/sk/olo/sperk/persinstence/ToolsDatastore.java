package sk.olo.sperk.persinstence;

import com.google.appengine.api.datastore.*;
import sk.olo.sperk.model.GalleryItemModel;
import sk.olo.sperk.model.RoleModel;
import sk.olo.sperk.model.UserModel;

import java.util.ArrayList;
import java.util.Date;
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
        return entity != null ? UserModel.createModel(entity, true) : null;
    }

    @Override
    public String putUser(UserModel userModel) {

        if (userModel.getIdentifier() == null) {
            checkDuplicateUsername(userModel.getUsername());
        }

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Transaction transaction = datastore.beginTransaction();

        updateUserDates(userModel);

        Key userKey = resolveUserKey(userModel);

        Entity entity = userModel.toEntity(userKey);
        datastore.put(entity);

        List<GalleryItemModel> galleryItems = userModel.getGalleryItems();
        if (galleryItems != null) {
            List<Key> deleteItemKeys = new ArrayList<>();
            List<Entity> putItemEntities = new ArrayList<>();

            for (GalleryItemModel galleryItem : galleryItems) {
                String galleryItemKey = galleryItem.getIdentifier();
                String crudAction = galleryItem.getCrudAction();
                if (galleryItemKey != null && GalleryItemModel.ACTION_DELETE.equals(crudAction)) {
                    deleteItemKeys.add(KeyFactory.stringToKey(galleryItemKey)); // preparing items for deletion
                } else if (galleryItemKey == null && GalleryItemModel.ACTION_PUT.equals(crudAction)) { // preparing items for adding (updating scenario will never happen)
                    galleryItem.setParentKey(KeyFactory.keyToString(userKey));
                    Entity putGalleryItem = galleryItem.toEntity();
                    putItemEntities.add(putGalleryItem);
                }
            }

            datastore.delete(deleteItemKeys);
            datastore.put(putItemEntities);
        }

        transaction.commit();

        return KeyFactory.keyToString(userKey);
    }

    private void checkDuplicateUsername(String username) {
        UserModel userByUsername = getUserByUsername(username);
        if (userByUsername != null) {
            throw new RuntimeException("USERNAME_ALREADY_EXISTS");
        }
    }

    private Key resolveUserKey(UserModel userModel) {
        if (userModel.getIdentifier() != null) {
            return KeyFactory.stringToKey(userModel.getIdentifier());
        } else {
            return generateKey(UserModel.KIND, 1);
        }
    }

    private void updateUserDates(UserModel userModel) {
        Date actualDate = new Date();
        if (userModel.getIdentifier() == null) {
            userModel.setCreated(actualDate);
        }
        userModel.setModified(actualDate);
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
        for (Entity entity : iterable) {
            if (UserModel.KIND.equals(entity.getKind())) {
                userModel = UserModel.createModel(entity, true);
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
        query.addSort("modified", Query.SortDirection.DESCENDING);

        PreparedQuery pq = datastore.prepare(query);

        List<UserModel> users = new ArrayList<UserModel>();
        for (Entity entity : pq.asIterable()) {
            users.add(UserModel.createModel(entity));
        }
        return users;
    }

    private Key generateKey(String kind, long count) {
        return DatastoreServiceFactory.getDatastoreService().allocateIds(kind, count).getStart();
    }

//    public String updateUser(UserModel userModel) {
//        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//
//        Transaction transaction = datastore.beginTransaction();
//
//        Key newKey = generateKey(UserModel.KIND, 1);
//        Entity entity = userModel.toEntity(newKey);
//        datastore.put(entity);
//
//        transaction.commit();
//    }

//    @Override
//    public UserModel getUser(String key) {
//        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//        try {
//            return UserModel.createModel(datastore.get(KeyFactory.stringToKey(key)));
//        } catch (EntityNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Override
//    public String putFullUser(UserModel userModel) {
//        Key newParentKey = generateKey(UserModel.KIND, 1);
//        Entity userModelEntity = userModel.toEntity(newParentKey);
//
//        List<Entity> galleryItemsList = new ArrayList<>();
//        for (GalleryItemModel galleryItemModel : userModel.getGalleryItems()) {
//            galleryItemModel.setParentKey(KeyFactory.keyToString(newParentKey));
//            Entity galleryItemEntity = galleryItemModel.toEntity();
//            galleryItemsList.add(galleryItemEntity);
//        }
//
//        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//
//        datastore.put(userModelEntity);
//        datastore.put(galleryItemsList);
//
//        return KeyFactory.keyToString(newParentKey);
//    }

//    @Override
//    public UserListHolder getUserList(String cursor) {
//        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//        List<UserModel> userModelList = new ArrayList<>();
//
////        int pageSize = 10;
////        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);
////
////        if (cursor != null && !"-1".equals(cursor)) {
////            fetchOptions.startCursor(Cursor.fromWebSafeString(cursor));
////        }
//
//        Query query = new Query(UserModel.KIND);
//        PreparedQuery pq = datastore.prepare(query);
//
////        QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
//        for (Entity entity : pq.asIterable()) {
//            userModelList.add(UserModel.createModel(entity));
//        }
//
////        String newCursor = results.getCursor().toWebSafeString();
//
//        return new UserListHolder(userModelList, "-1");
//    }

//    private Key generateKey(Key parent, String kind, long count) {
//        return DatastoreServiceFactory.getDatastoreService().allocateIds(parent, kind, count).getStart();
//    }

/*    private String putGalleryItem(GalleryItemModel galleryItemModel, DatastoreService datastore) {
        Entity entity = galleryItemModel.toEntity();
        datastore.put(entity);
        return KeyFactory.keyToString(entity.getIdentifier());
    }*/

//    @Override
//    public String putRole(RoleModel roleModel) {
//        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//        Entity entity = roleModel.toEntity();
//        datastore.put(entity);
//        return KeyFactory.keyToString(entity.getIdentifier());
//    }

//    @Override
//    public String storePersonWithImages() {
//        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//        Entity tom = new Entity("Person", "Tom");
//        Key tomKey = tom.getIdentifier();
//        datastore.put(tom);
//
//
//        Entity weddingPhoto = new Entity("Photo", tomKey);
//        weddingPhoto.setProperty("imageURL", "http://domain.com/some/path/to/wedding_photo.jpg");
//
//        Entity babyPhoto = new Entity("Photo", tomKey);
//        babyPhoto.setProperty("imageURL", "http://domain.com/some/path/to/baby_photo.jpg");
//
//        Entity dancePhoto = new Entity("Photo", tomKey);
//        dancePhoto.setProperty("imageURL", "http://domain.com/some/path/to/dance_photo.jpg");
//
//        Entity campingPhoto = new Entity("Photo");
//        campingPhoto.setProperty("imageURL", "http://domain.com/some/path/to/camping_photo.jpg");
//
//        List<Entity> photoList = Arrays.asList(weddingPhoto, babyPhoto, dancePhoto, campingPhoto);
//        datastore.put(photoList);
//
//        return KeyFactory.keyToString(tomKey);
//    }
//
//    @Override
//    public List<Entity> loadPersonGroupByAncestor(String key) {
//        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//        Key tomKey = KeyFactory.stringToKey(key);
//        Query photoQuery = new Query("Photo").setAncestor(tomKey);
//
//        // This returns weddingPhoto, babyPhoto, and dancePhoto,
//        // but not campingPhoto, because tom is not an ancestor
//        return datastore.prepare(photoQuery).asList(FetchOptions.Builder.withDefaults());
//    }

}
