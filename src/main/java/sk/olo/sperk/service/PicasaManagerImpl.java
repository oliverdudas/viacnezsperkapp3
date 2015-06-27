package sk.olo.sperk.service;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.MediaStreamSource;
import com.google.gdata.data.photos.*;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import org.apache.commons.fileupload.FileItemStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by olo on 26. 6. 2015.
 */
public class PicasaManagerImpl implements PicasaManager {

    private static final String KIND_ALBUM = "?kind=album&v=2.0";
    private static final String VIACNEZSPERK = "viacnezsperk";
    private static final String GOOGLE_USER_ID = "104004273393078620402";
//    private static final String GOOGLE_USER_EMAIL = "oliver.dudas@viacnezsperk.sk";
//    private static final String GOOGLE_USER_EMAIL_PASSWORD = "sperk123";

    @Override
    public PhotoEntry uploadPhotoToPicasa(FileItemStream file, String accessToken) throws IOException, ServiceException {
        InputStream stream = file.openStream();
        URL albumPostUrl = getAlbumUrl(getAlbumIdWithFreeSpaceLeft(accessToken));

        PhotoEntry myPhoto = new PhotoEntry();
        myPhoto.setTitle(new PlainTextConstruct(file.getName()));
        myPhoto.setDescription(new PlainTextConstruct(file.getName()));

        MediaStreamSource myMedia = new MediaStreamSource(stream, "image/jpeg");
        myPhoto.setMediaSource(myMedia);

        PicasawebService myService = createPicasawebService(accessToken);
        return myService.insert(albumPostUrl, myPhoto);
    }

    private PicasawebService createPicasawebService(final String accessToken) throws AuthenticationException {
        PicasawebService myService = new PicasawebService(VIACNEZSPERK);
        myService.setReadTimeout(1000000);
        myService.setConnectTimeout(1000000);
        myService.setAuthSubToken(accessToken);
        return myService;
    }

    private URL getAlbumUrl(String albumId) throws MalformedURLException {
        return new URL(getUserFeedLink() + "/albumid/" + albumId);
    }

    private String getUserFeedLink() {
        return "https://picasaweb.google.com/data/feed/api/user/" + GOOGLE_USER_ID;
    }

    public String getAlbumIdWithFreeSpaceLeft(String accessToken) throws IOException, ServiceException {
        PicasawebService myService = createPicasawebService(accessToken);
        UserFeed userAlbumFeed = getUserAlbumFeed(myService);
        List<GphotoEntry> entries = userAlbumFeed.getEntries();
        for (GphotoEntry entry : entries) {
            String titleText = entry.getTitle().getPlainText();
            if (titleText.startsWith(VIACNEZSPERK) && entry.hasExtension(GphotoPhotosLeft.class)) {
                Integer photosLeftCount = entry.getExtension(GphotoPhotosLeft.class).getValue();
                if (photosLeftCount > 0) {
                    return entry.getGphotoId();
                }
            }
        }
        int postfix = entries.size() + 1;
        return addNewAlbum(myService, VIACNEZSPERK + postfix);
    }

    private UserFeed getUserAlbumFeed(PicasawebService myService) throws IOException, ServiceException {
        return myService.getFeed(new URL(getUserFeedLink() + KIND_ALBUM), UserFeed.class);
    }

    private String addNewAlbum(PicasawebService myService, String newTitle) throws IOException, ServiceException {
        AlbumEntry albumEntry = new AlbumEntry();

        albumEntry.setTitle(new PlainTextConstruct(newTitle));
        albumEntry.setDescription(new PlainTextConstruct("Viacnezsperk"));
        GphotoAccess accessExt = new GphotoAccess(GphotoAccess.Value.PUBLIC);
        albumEntry.setAccessExt(accessExt);

        AlbumEntry insertedEntry = myService.insert(new URL(getUserFeedLink()), albumEntry);
        return insertedEntry.getTitle().getPlainText();
    }

//    private static final JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//    private Credential createCredential(final String accessToken) {
//        final HttpTransport httpTransport;
//        try {
//            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        } catch (GeneralSecurityException | IOException e) {
//            throw new RuntimeException(e);
//        }
//        String clientId = "638906425222-mi0677muktg56on3b679cj726ni46a3f.apps.googleusercontent.com";
//        String clientSecret = "WPL3URB2BD3TXdmr-B0MKSeU";
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow(
//                httpTransport, jsonFactory, clientId, clientSecret, Collections.singleton("https://picasaweb.google.com/data/"));
//
//        String authorizationUrl = flow.newAuthorizationUrl()
//                .setRedirectUri("http://localhost:8080")
//                .setAccessType("offline")
//                .setApprovalPrompt("auto") // force, or auto (force - prompt every time, auto - if needed)
//                .build();
//
//        GoogleTokenResponse response = null;
//        try {
//            response = flow.newTokenRequest(accessToken)
////                    .setRedirectUri("urn:ietf:wg:oauth:2.0:oob")
//                    .setRedirectUri("http://localhost:8080")
//                    .execute();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Retrieve the credential from the request response
//        return new com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder()
//                .setTransport(httpTransport)
//                .setJsonFactory(jsonFactory)
//                .setClientSecrets(clientId, clientSecret)
//                .build()
//                .setFromTokenResponse(response);
//    }
}
