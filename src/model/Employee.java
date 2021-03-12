package model;

public class Employee extends Person{
    private int id;

    public Employee(String name, String lastName, int id) {
        super(name, lastName);
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
