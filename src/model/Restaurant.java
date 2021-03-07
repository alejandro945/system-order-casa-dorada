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

    public Restaurant() {
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

    public String addProduct(String name, ProductType type, List<Ingredients> ingredients, ProductSize size, int price,
            User creator) {
        String msg = "";
        Product newProduct = null;
        if (products.isEmpty()) {
            newProduct = new Product(name, type, ingredients, size, price, creator);
            products.add(newProduct);
        } else {
            int i = 0;
            newProduct = new Product(name, type, ingredients, size, price, creator);
            while (i < products.size() && newProduct.compareTo(products.get(i)) > 0) {
                i++;
            }
            for (int j = 0; j < products.size(); j++) {
                if (newProduct.getName().equalsIgnoreCase(products.get(j).getName())) {
                    msg = "You can not added a product with the same name";
                } else {
                    products.add(i, newProduct);
                    msg = "The product " + newProduct.getName() + " have been added succesfully";
                }
            }
        }
        return msg;
    }

    public String setProductInfo(Product product, String newName, ProductType newType, List<Ingredients> newIngredients,
            ProductSize newSize, int newPrice, User lastEditor) {
        product.setName(newName);
        product.setType(newType);
        product.setIngredients(newIngredients);
        product.setSize(newSize);
        product.setPrice(newPrice);
        product.setLastEditor(lastEditor);
        return "The Product have been edited succesfully";
    }

    public String deleteProduct(int positionProduct) {
        products.remove(positionProduct);
        return "The product have been deleted succesfully";
    }

    public String setState(Product product, boolean newState) {
        product.setState(newState);
        return "The product state have been changed";
    }

    public String addIngrendient(String name, User creator) {
        String msg = "";
        Ingredients newIngredient = null;
        if (ingredients.isEmpty()) {
            newIngredient = new Ingredients(name, creator);
            ingredients.add(newIngredient);
        } else {
            int i = 0;
            newIngredient = new Ingredients(name, creator);
            while (i < ingredients.size() && newIngredient.compareTo(ingredients.get(i)) < 0) {
                i++;
            }
            for (int j = 0; j < products.size(); j++) {
                if (newIngredient.getName().equalsIgnoreCase(products.get(j).getName())) {
                    msg = "You can not added an ingredient with the same name";
                } else {
                    ingredients.add(i, newIngredient);
                    msg = "The ingredient " + newIngredient.getName() + " have been added succesfully";
                }
            }
        }
        return msg;
    }

    public String setInfoIngredient(Ingredients ingredient, String name, User lastEditor) {
        ingredient.setName(name);
        ingredient.setLastEditor(lastEditor);
        return "The Ingredient have been edited succesfully";
    }

    public String deleteIngredient(int positionIngredient) {
        products.remove(positionIngredient);
        return "The Ingredient have been deleted succesfully";
    }

    public String setState(Ingredients ingredient, boolean newState) {
        ingredient.setState(newState);
        return "The Ingredient state have been changed";
    }

}
