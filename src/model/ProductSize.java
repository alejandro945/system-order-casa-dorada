package model;

import java.io.Serializable;

public class ProductSize implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private int code;
    private User creator;
    private User lastEditor;
    private boolean state;

    public ProductSize(String name, int code, User creator) {
        this.name = name;
        this.code = code;
        this.creator = creator;
        this.lastEditor = creator;
        this.state = true;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
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

    @Override
    public String toString() {
        return getName();
    }

}
