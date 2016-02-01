package sk.olo.sperk.servlet;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.MediaStreamSource;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.common.base.Preconditions;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import sk.olo.sperk.service.PicasaManager;
import sk.olo.sperk.service.PicasaManagerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by olo on 26. 6. 2015.
 */
public class FileUploadServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(FileUpload.class.getName());

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            ServletFileUpload upload = new ServletFileUpload();
//            res.setContentType("text/plain");
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

                PhotoEntry photoEntry = picasaManager.uploadPhotoToPicasa(item, accessToken);

                String imageUrl = ((MediaContent) photoEntry.getContent()).getUri();
                String thumbUrl = photoEntry.getMediaThumbnails().get(1).getUrl();
                String gphotoId = photoEntry.getGphotoId();
                Long timestamp = new Date().getTime();

                out.print("{" +
                        "\"imageUrl\": \"" + imageUrl + "\"," +
                        "\"thumbUrl\": \"" + thumbUrl + "\"," +
                        "\"timestamp\": \"" + timestamp + "\"," +
                        "\"gphotoId\": \"" + gphotoId + "\"}");
                out.flush();

//                if (item.isFormField()) {
//                    log.warning("Got a form field: " + item.getFieldName());
//                } else {
//                    log.warning("Got an uploaded file: " + item.getFieldName() +
//                            ", name = " + item.getName());
//
//                    // You now have the filename (item.getName() and the
//                    // contents (which you can read from stream). Here we just
//                    // print them back out to the servlet output stream, but you
//                    // will probably want to do something more interesting (for
//                    // example, wrap them in a Blob and commit them to the
//                    // datastore).
//                    int len;
//                    byte[] buffer = new byte[8192];
//                    while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
//                        res.getOutputStream().write(buffer, 0, len);
//                    }
//                }
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }

    }

    private String getUserId() {
        UserService userService = UserServiceFactory.getUserService();
        User loggedIn = userService.getCurrentUser();
        Preconditions.checkState(loggedIn != null, "This servlet requires the user to be logged in.");
        return loggedIn.getUserId();
    }
}
