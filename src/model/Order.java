package model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int code;
    private State state;
    private List<Product> products;
    private List<Integer> amount;
    private Costumer costumer;
    private Employee employee;
    private String date;
    private String suggestion;
    private User creator;
    private User lastEditor;

    public Order(int code, State state, List<Product> products, List<Integer> amount, Costumer costumer,
            Employee employee, String date, String suggestion, User creator) {
        this.code = code;
        this.state = state;
        this.products = products;
        this.amount = amount;
        this.costumer = costumer;
        this.employee = employee;
        this.date = date;
        this.suggestion = suggestion;
        this.creator = creator;
        this.lastEditor = creator;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Integer> getAmount() {
        return this.amount;
    }

    public void setAmount(List<Integer> amount) {
        this.amount = amount;
    }

    public Costumer getCostumer() {
        return this.costumer;
    }

    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSuggestion() {
        return this.suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
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

    @Override
    public String toString() {
        return String.valueOf(getCode());
    }

}
