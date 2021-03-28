package model;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable, Comparable<Product> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private ProductType productType;
    private ProductSize productSize;
    private List<Ingredients> ingredients;
    private int code;
    private double price;
    private User creator;
    private User lastEditor;
    private boolean state;

    public Product(String name, ProductType productType, List<Ingredients> ingredients, int code,
            ProductSize productSize, double price, User creator) {
        this.name = name;
        this.productType = productType;
        this.ingredients = ingredients;
        this.code = code;
        this.productSize = productSize;
        this.price = price;
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

    public ProductType getProductType() {
        return this.productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public List<Ingredients> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ProductSize getProductSize() {
        return this.productSize;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
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
    public int compareTo(Product p) {
        if (price < p.getPrice()) {
            return -1;
        }
        if (price > p.getPrice()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {

        return getName();
    }

}
