package model;

public class Ingredients implements Comparable<Ingredients> {
    private String name;
    private User creator;
    private User lastEditor;
    private boolean state;

    public Ingredients(String name, User creator) {
        this.name = name;
        this.creator = creator;
        this.state = true;
    }

    public String getName() {
        return name;
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
    public int compareTo(Ingredients i) {
        return getName().compareTo(i.getName());
    }

    @Override
    public String toString() {
        return "\"" + getName() + "\"" + "," + " ";
    }

}
