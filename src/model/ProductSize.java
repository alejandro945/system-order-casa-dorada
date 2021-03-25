package model;

import java.io.Serializable;

public class ProductSize implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String name;
    int code;
    String creator;
    String lastEditor;
    boolean state;

    public ProductSize(String name, int code, String creator) {
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

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastEditor() {
        return this.lastEditor;
    }

    public void setLastEditor(String lastEditor) {
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
