package model;

public class Employee extends Person {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private User creator;
    private User lastEditor;
    private boolean state;

    public Employee(String name, String lastName, int id, User creator) {
        super(name, lastName);
        this.id = id;
        this.creator = creator;
        this.lastEditor = creator;
        this.state = true;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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
