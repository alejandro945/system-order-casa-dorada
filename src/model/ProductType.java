package model;

import java.io.Serializable;

public class ProductType implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private String creator;
    private String lastEditor;
    private boolean state;
    private int code;

    public ProductType(String name, String creator, int code) {
        this.name = name;
        this.creator = creator;
        this.code = code;
        this.lastEditor = creator;
        this.state = true;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isState() {
        return this.state;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }
   

}
