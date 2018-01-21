package com.rupp.yonchando.sayhello;

/**
 * Created by PC User on 15-Jan-18.
 */

public class Users {

    private String username;
    private String email;
    private String photo;
    private String statusOnline;

    public Users() {

    }

    public Users(String username, String email, String photo, String statusOnline) {
        this.username = username;
        this.email = email;
        this.photo = photo;
        this.statusOnline = statusOnline;
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

    public String getStatusOnline() {
        return statusOnline;
    }

    public void setStatusOnline(String statusOnline) {
        this.statusOnline = statusOnline;
    }
}
