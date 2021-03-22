package model;

public class Costumer extends Person implements Comparable<Costumer> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private String address;
    private int telephone;
    private String suggestions;
    private String creator;
    private String lastEditor;
    private boolean state;

    public Costumer(String name, String lastName, int id, String address, int telephone, String suggestions,
            String creator) {
        super(name, lastName);
        this.id = id;
        this.address = address;
        this.telephone = telephone;
        this.suggestions = suggestions;
        this.creator = creator;
        this.state = true;
        this.lastEditor = creator;
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
    public int compareTo(Costumer c) {

        return this.getName().compareTo(c.getName());
    }

    @Override
    public Costumer getPerson() {
        return this;
    }

}
