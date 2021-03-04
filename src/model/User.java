package model;

public class User extends Employee {
    private String userName;
    private String password;

    public User(String name, String lastName, int id, String userName, String password) {
        super(name, lastName, id);
        this.userName = userName;
        this.password = password;
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

}
