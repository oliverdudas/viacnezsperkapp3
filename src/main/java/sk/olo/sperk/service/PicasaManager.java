package sk.olo.sperk.service;

import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;
import org.apache.commons.fileupload.FileItemStream;

import java.io.IOException;

/**
 * Created by olo on 26. 6. 2015.
 */
public interface PicasaManager {
    PhotoEntry uploadPhotoToPicasa(FileItemStream file, String accessToken) throws IOException, ServiceException;
}
