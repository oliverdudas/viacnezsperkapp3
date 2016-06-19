package sk.olo.sperk.servlet;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.org.codehaus.jackson.map.DeserializationConfig;
import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.common.base.Preconditions;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import sk.olo.sperk.model.ImageUploadResponse;
import sk.olo.sperk.service.PicasaManager;
import sk.olo.sperk.service.PicasaManagerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by olo on 26. 6. 2015.
 */
public class FileUploadServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(FileUploadServlet.class.getName());

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            ServletFileUpload upload = new ServletFileUpload();
            res.setContentType("application/json");
            PrintWriter out = res.getWriter();

            FileItemIterator iterator = upload.getItemIterator(req);
            String accessToken = null;
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();

                PicasaManager picasaManager = new PicasaManagerImpl();

                if ("accessToken".equals(item.getFieldName())) {
                    accessToken = IOUtils.toString(item.openStream(), "UTF-8");
                    continue;
                }

                log.info("Uploading photo to picasa web album[accessToken: " + accessToken + "]");
                PhotoEntry photoEntry = picasaManager.uploadPhotoToPicasa(item, accessToken);

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                String imageUrl = ((MediaContent) photoEntry.getContent()).getUri();
                objectMapper.writeValue(out, new ImageUploadResponse(
                        imageUrl,
                        photoEntry.getMediaThumbnails().get(1).getUrl(),
                        new Date().getTime(),
                        photoEntry.getGphotoId()
                ));

                out.flush();
                log.info("Photo uploaded[imageURL: " + imageUrl + "]");

            }
        } catch (Exception ex) {
            throw new ServletException("Photo not uploaded!", ex);
        }

    }

    private String getUserId() {
        UserService userService = UserServiceFactory.getUserService();
        User loggedIn = userService.getCurrentUser();
        Preconditions.checkState(loggedIn != null, "This servlet requires the user to be logged in.");
        return loggedIn.getUserId();
    }
}
