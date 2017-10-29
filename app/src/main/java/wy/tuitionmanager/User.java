package wy.tuitionmanager;

/**
 * Created by weiyang on 8/10/2017.
 */

public class User {
    private String name;
    private String phonenumber;
    private String email;
    private boolean Admin;


    public User(){

    }

    public User(String name,String phonenumber,String email){
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.Admin = false;
    }

    public boolean isAdmin() {
        return Admin;
    }

    public void setAdmin(boolean admin) {
        Admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
