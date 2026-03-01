package org.softwareeng.group37.model;

public class User  extends Entity{
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String toWrite() {
//        return getId() + "," + username + "," + password + ","+ getStatus() +"\n";
//    }



}
