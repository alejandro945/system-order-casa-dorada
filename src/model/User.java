package model;

public class User extends Employee {
    private String userName;
    private String password;
    private String image;
    private boolean state;

    public User(String name, String lastName, int id, String userName, String password, String image) {
        super(name, lastName, id);
        this.userName = userName;
        this.password = password;
        this.image = image;
        this.state = true;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
