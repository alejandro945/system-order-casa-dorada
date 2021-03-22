package model;

public class Employee extends Person {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private String creator;
    private String lastEditor;
    private boolean state;

    public Employee(String name, String lastName, int id, String creator) {
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

    @Override
    public Employee getPerson() {
        return this;
    }

}
