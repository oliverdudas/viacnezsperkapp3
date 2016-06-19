package sk.olo.sperk.model;

/**
 * Created by olo on 18. 6. 2016.
 */
public class ImageUploadResponse {

    private String imageUrl;
    private String thumbUrl;
    private Long timestamp;
    private String gphotoId;

    public ImageUploadResponse(String imageUrl, String thumbUrl, Long timestamp, String gphotoId) {
        this.imageUrl = imageUrl;
        this.thumbUrl = thumbUrl;
        this.timestamp = timestamp;
        this.gphotoId = gphotoId;
    }

    public ImageUploadResponse() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getGphotoId() {
        return gphotoId;
    }

    public void setGphotoId(String gphotoId) {
        this.gphotoId = gphotoId;
    }
}
