package com.rupp.yonchando.sayhello;

/**
 * Created by PC User on 15-Jan-18.
 */

public class Users {

    private String username;
    private String email;
    private String photo;

    public Users() {

    }

    public Users(String username, String email, String photo) {
        this.username = username;
        this.email = email;
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
