package model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private List<Product> products;
    private List<Costumer> costumers;
    private List<Employee> employees;
    private List<Order> orders;
    private List<Ingredients> ingredients;

    public Restaurant(List<Product> products, List<Costumer> costumers, List<Employee> employees, List<Order> orders,
            List<Ingredients> ingredients) {
        products = new ArrayList<Product>();
        costumers = new ArrayList<Costumer>();
        employees = new ArrayList<Employee>();
        orders = new ArrayList<Order>();
        ingredients = new ArrayList<Ingredients>();
    }
    public Restaurant(){
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Costumer> getCostumers() {
        return this.costumers;
    }

    public void setCostumers(List<Costumer> costumers) {
        this.costumers = costumers;
    }

    public List<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Ingredients> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public void addProduct(String name, ProductType type, List<Ingredients> ingredients, ProductSize size, int price) {
        Product newProduct = null;
        if (products.isEmpty()) {
            newProduct = new Product(name, type, ingredients, size, price);
            products.add(newProduct);
        } else {
            int i = 0;
            newProduct = new Product(name, type, ingredients, size, price);
            while (i < products.size() && newProduct.compareTo(products.get(i)) > 0) {
                i++;
            }
            products.add(i, newProduct);
        }
    }
}
