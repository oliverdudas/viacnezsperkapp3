package sk.olo.sperk.exception;

import com.google.appengine.api.oauth.OAuthRequestException;

public class SperkOAuthRequestException extends OAuthRequestException {

    private String key;

    public SperkOAuthRequestException(String key) {
        super(key);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
