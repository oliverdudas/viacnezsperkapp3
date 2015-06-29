package sk.olo.sperk.util;

/**
 * Contains the client IDs and scopes for allowed clients consuming the viacnezsperkAPI API.
 */
public class Constants {
  //  viacnezsperkapp1
    public static final String WEB_CLIENT_ID = "539843283811-7c9egdebuur3uc27qrpsacijml19264j.apps.googleusercontent.com";
  //  viacnezsperkapp3
//  public static final String WEB_CLIENT_ID = "638906425222-mi0677muktg56on3b679cj726ni46a3f.apps.googleusercontent.com";
  public static final String ANDROID_CLIENT_ID = "replace this with your Android client ID";
  public static final String IOS_CLIENT_ID = "replace this with your iOS client ID";
  public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;

//  public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
  public static final String EMAIL_SCOPE = "email";

  public static final String API_EXPLORER_CLIENT_ID = com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID;
}
