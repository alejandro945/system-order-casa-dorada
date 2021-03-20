package model;

public class ProductType {
    private String name;
    private User creator;
    private User lastEditor;
    private boolean state;
    private String code;


    public ProductType(String name, User creator, User lastEditor, boolean state, String code) {
        this.name = name;
        this.creator = creator;
        this.lastEditor = lastEditor;
        this.state = state;
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isState() {
        return this.state;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
   

}
