package sk.olo.sperk.model;

import java.util.List;

/**
 * Created by olo on 27. 6. 2015.
 */
public class UserListHolder {

    private List<UserModel> userModelList;
    private String cursor;

    public UserListHolder(List<UserModel> userModelList, String cursor) {
        this.userModelList = userModelList;
        this.cursor = cursor;
    }

    public List<UserModel> getUserModelList() {
        return userModelList;
    }

    public void setUserModelList(List<UserModel> userModelList) {
        this.userModelList = userModelList;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }
}
