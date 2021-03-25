package model;

import java.io.Serializable;

public class Ingredients implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int code;
    private String name;
    private User creator;
    private User lastEditor;
    private boolean state;

    public Ingredients(int code, String name, User creator) {
        this.code = code;
        this.name = name;
        this.creator = creator;
        this.lastEditor = creator;
        this.state = true;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
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

    @Override
    public String toString() {
        return getName();
    }
}
