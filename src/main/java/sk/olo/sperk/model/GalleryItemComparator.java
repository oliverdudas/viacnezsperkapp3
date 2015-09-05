package sk.olo.sperk.model;

import java.util.Comparator;

/**
 * Created by olo on 5. 9. 2015.
 */
public class GalleryItemComparator implements Comparator<GalleryItemModel> {
    @Override
    public int compare(GalleryItemModel first, GalleryItemModel second) {
        Long firstTimestamp = first.getTimestamp();
        Long secondTimestamp = second.getTimestamp();
        if (firstTimestamp == null && secondTimestamp == null) {
            return 0;
        } else if (firstTimestamp == null) {
            return -1;
        } else if (secondTimestamp == null) {
            return 1;
        } else {
            return firstTimestamp.compareTo(secondTimestamp);
        }
    }
}
