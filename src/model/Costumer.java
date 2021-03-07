package model;

public class Costumer {
    private String name;
    private String lastName;
    private int id;
    private String address;
    private int telephone;
    private String suggestions;
    private User creator;
    private User lastEditor;
    private boolean state;

    public Costumer(String name, String lastName, int id, String address, int telephone, String suggestions,
            User creator) {
        this.name = name;
        this.lastName = lastName;
        this.id = id;
        this.address = address;
        this.telephone = telephone;
        this.suggestions = suggestions;
        this.creator = creator;
        this.state = true;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTelephone() {
        return this.telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getSuggestions() {
        return this.suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getLastEditor() {
        return this.lastEditor;
    }

    public void setLastEditor(User lastEditor) {
        this.lastEditor = lastEditor;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
