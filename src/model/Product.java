package model;

import java.util.List;

public class Product implements Comparable<Product> {
    private String name;
    private ProductType type;
    private List<Ingredients> ingredients;
    private ProductSize size;
    private int price;

    public Product(String name, ProductType type, List<Ingredients> ingredients, ProductSize size, int price) {
        this.name = name;
        this.type = type;
        this.ingredients = ingredients;
        this.size = size;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return this.type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public List<Ingredients> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public ProductSize getSize() {
        return this.size;
    }

    public void setSize(ProductSize size) {
        this.size = size;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int compareTo(Product p) {
        return getPrice() - p.getPrice();
    }

}
